package com.example.testappqr.presentation.Beacon

import android.Manifest
import android.app.Application
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Handler
import android.os.Looper
import android.os.Parcelable
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import javax.inject.Inject
import kotlinx.parcelize.RawValue

data class BeaconDevice(
    val address: String,
    val name: String,
    val rssi: Int,
    val batteryLevel: Float? = null,
    val manufacturerData: String? = null,
    val lastSeen: Long = System.currentTimeMillis()
)

@HiltViewModel
class BeaconVM @Inject constructor(
    private val application: Application,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        private const val TAG = "BeaconVM"
        private const val SCAN_PERIOD = 30000L // 30 seconds
    }

    // Bluetooth components
    private val bluetoothManager =
        application.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter
    private var bluetoothLeScanner: BluetoothLeScanner? = null
    private val handler = Handler(Looper.getMainLooper())

    val beaconState = savedStateHandle.getStateFlow("beaconState", BeaconState())


    // BLE Scanner receiver
    private val bluetoothStateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == BluetoothAdapter.ACTION_STATE_CHANGED) {
                val btState =
                    intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)
                val isEnabled = btState == BluetoothAdapter.STATE_ON
                Log.d(TAG, "BT state changed: $isEnabled ($btState)")

                updateState { it.copy(isBluetoothEnabled = isEnabled) }

                if (!isEnabled) {
                    updateState {
                        it.copy(
                            errorMessage = "Bluetooth must be enabled"
                        )
                    }
                    stopScanning()
                } else {
                    updateState {
                        it.copy(
                            errorMessage = null
                        )
                    }
                    setupBluetoothScanner()
                }
            }
        }
    }

    init {
        // Register receiver for Bluetooth state changes
        val filter = IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)
        application.registerReceiver(bluetoothStateReceiver, filter)

        // Initialize Bluetooth scanner
        setupBluetoothScanner()
    }

    private fun setupBluetoothScanner() {
        try {
            if (bluetoothAdapter == null) {
                updateState {
                    it.copy(
                        errorMessage = "Device doesn't support Bluetooth"
                    )
                }
                return
            }

            if (!bluetoothAdapter.isEnabled) {
                updateState {
                    it.copy(
                        errorMessage = "Bluetooth is disabled"
                    )
                }
                return
            }

            bluetoothLeScanner = bluetoothAdapter.bluetoothLeScanner
            Log.d(TAG, "Bluetooth scanner initialized successfully")
        } catch (e: Exception) {
            updateState {
                it.copy(
                    errorMessage = "Error initializing Bluetooth: ${e.message}"
                )
            }
            Log.e(TAG, "Error initializing bluetooth", e)
        }
    }

    // Set permissions status from MainActivity
    fun setPermissionsGranted(granted: Boolean) {
        viewModelScope.launch {
            updateState {
                it.copy(
                    permissionsGranted = granted
                )
            }
            if (granted) {
                setupBluetoothScanner()
            }
        }
    }

    fun startScanning() {
        if (!checkPermissions()) {
            updateState {
                it.copy(
                    errorMessage = "Missing required permissions"
                )
            }

            return
        }

        if (!isLocationEnabled()) {
            updateState {
                it.copy(
                    errorMessage = "Location services must be enabled"
                )
            }
            return
        }

        if (!checkBluetoothState()) {
            updateState {
                it.copy(
                    errorMessage = "Bluetooth must be enabled"
                )
            }
            return
        }

        if (bluetoothLeScanner == null) {
            setupBluetoothScanner()
            if (bluetoothLeScanner == null) {
                updateState { it.copy(errorMessage = "Bluetooth scanner not available") }

                return
            }
        }

        updateState {
            it.copy(
                detectedBeacons = emptyList(),
                isScanning = true,
                errorMessage = null
            )
        }

        Log.d(TAG, "Starting scan for beacons")

        // Set scan settings to aggressive mode for best discovery
        val settings = ScanSettings.Builder()
            .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
            .setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES)
            .setMatchMode(ScanSettings.MATCH_MODE_AGGRESSIVE)
            .setNumOfMatches(ScanSettings.MATCH_NUM_MAX_ADVERTISEMENT)
            .setReportDelay(0)
            .build()

        // Don't use filters to get all devices including ELA beacons
        val filters = ArrayList<ScanFilter>()

        handler.postDelayed({ stopScanning() }, SCAN_PERIOD)

        try {
            if (ActivityCompat.checkSelfPermission(
                    application, Manifest.permission.BLUETOOTH_SCAN
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                bluetoothLeScanner?.startScan(filters, settings, leScanCallback)
                Log.d(TAG, "Started scanning with aggressive settings")
            } else {
                updateState { it.copy(isScanning = false, errorMessage ="Missing BLUETOOTH_SCAN permission" ) }
                Log.e(TAG, "Missing BLUETOOTH_SCAN permission")
            }
        } catch (e: Exception) {
            updateState { it.copy(isScanning = false, errorMessage = "Error: ${e.message}") }

            Log.e(TAG, "Error starting scan", e)
        }
    }

    fun stopScanning() {
        if (bluetoothLeScanner != null && beaconState.value.isScanning) {
            try {
                if (ActivityCompat.checkSelfPermission(
                        application, Manifest.permission.BLUETOOTH_SCAN
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    bluetoothLeScanner?.stopScan(leScanCallback)
                    updateState { it.copy(isScanning = false) }

                    Log.d(TAG, "Stopped scanning - found ${beaconState.value.detectedBeacons.size} devices")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error stopping scan", e)
            }
        }
    }

    private val leScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            val device = result.device
            val scanRecord = result.scanRecord
            var batteryLevel: Float? = null
            var manufacturerInfo: String? = null

            Log.d(TAG, "Device found: ${device.address}")

            scanRecord?.let { record ->
                val rawBytes = record.bytes
                if (rawBytes != null) {
                    // Full hex dump of all advertising data
                    Log.d(TAG, "Raw data: ${bytesToHex(rawBytes)}")

                    // Try to identify if this is an ELA beacon
                    if (isELABeacon(device.address, rawBytes)) {
                        // Extract battery level using ELA specific logic
                        batteryLevel = 0f
                        manufacturerInfo = "ELA Beacon"
                        Log.d(TAG, "ELA Beacon identified: ${device.address}")
                    } else {
                        // Generic manufacturer data extraction
                        val manufacturerData = record.manufacturerSpecificData
                        if (manufacturerData != null && manufacturerData.size() > 0) {
                            val id = manufacturerData.keyAt(0)
                            manufacturerInfo = "0x${id.toString(16)}"
                            Log.d(TAG, "Manufacturer ID: $manufacturerInfo")
                        }
                    }
                }

                // Try to find battery service data
                record.serviceData?.forEach { (uuid, data) ->
                    Log.d(TAG, "Service UUID: $uuid, data: ${bytesToHex(data)}")
                    if (uuid.toString().startsWith("0000180f")) {
                        // Standard Battery Service
                        if (data.isNotEmpty()) {
                            batteryLevel = (data[0].toInt() and 0xFF).toFloat()
                            Log.d(TAG, "Battery from standard service: $batteryLevel%")
                        }
                    }
                }
            }

            val deviceName = if (ActivityCompat.checkSelfPermission(
                    application, Manifest.permission.BLUETOOTH_CONNECT
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                device.name ?: "Unknown Device"
            } else {
                "Unknown Device (Permission Denied)"
            }

            val beacon = BeaconDevice(
                address = device.address,
                name = deviceName,
                rssi = result.rssi,
                batteryLevel = batteryLevel,
                manufacturerData = manufacturerInfo
            )

            // Update beacon list
            handler.post {
//                val currentList = savedStateHandle.get<BeaconState>("beaconState").detectedBeacons.toMutableList()
                val currentList = beaconState.value.detectedBeacons.toMutableList()
                val existingIndex = currentList.indexOfFirst { it.address == beacon.address }
                if (existingIndex >= 0) {
                    currentList[existingIndex] = beacon
                } else {
                    if (beacon.address.startsWith("CC:97:6E"))
                    currentList.add(beacon)
                    Log.d(TAG, "New device found: ${beacon.address}, Name: ${beacon.name}")
                }
                updateState {
                    it.copy(
                        detectedBeacons = currentList
                    )
                }
            }
        }

        override fun onBatchScanResults(results: List<ScanResult>) {
            for (result in results) {
                onScanResult(0, result)
            }
        }

        override fun onScanFailed(errorCode: Int) {
            updateState { it.copy(isScanning = false) }
            val errorMessage = when (errorCode) {
                ScanCallback.SCAN_FAILED_ALREADY_STARTED -> "Scan already started"
                ScanCallback.SCAN_FAILED_APPLICATION_REGISTRATION_FAILED -> "Application registration failed"
                ScanCallback.SCAN_FAILED_FEATURE_UNSUPPORTED -> "BLE not supported"
                ScanCallback.SCAN_FAILED_INTERNAL_ERROR -> "Internal error"
                else -> "Unknown error code: $errorCode"
            }
            updateState { it.copy(errorMessage = errorMessage) }

            Log.e(TAG, "Scan failed: $errorMessage")
        }
    }

    // Helper function to identify ELA beacons
    private fun isELABeacon(address: String, data: ByteArray): Boolean {
        // Check for ELA MAC address patterns (common prefixes)
        return address.startsWith("CC:97:6E:")
    }

    // Helper function to convert byte array to hex string
    private fun bytesToHex(bytes: ByteArray): String {
        val hexChars = "0123456789ABCDEF"
        val result = StringBuilder(bytes.size * 2)
        for (b in bytes) {
            val i = b.toInt() and 0xFF
            result.append(hexChars[i shr 4])
            result.append(hexChars[i and 0x0F])
        }
        return result.toString()
    }

    fun checkPermissions(): Boolean {
        val locationPermission = ContextCompat.checkSelfPermission(
            application, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val bluetoothPermission =
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                ContextCompat.checkSelfPermission(
                    application, Manifest.permission.BLUETOOTH_SCAN
                ) == PackageManager.PERMISSION_GRANTED
            } else {
                ContextCompat.checkSelfPermission(
                    application, Manifest.permission.BLUETOOTH
                ) == PackageManager.PERMISSION_GRANTED
            }

        return locationPermission && bluetoothPermission
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager =
            application.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun checkBluetoothState(): Boolean {
        val isEnabled = bluetoothAdapter?.isEnabled == true
        Log.d(TAG, "Checking BT state: $isEnabled")
        return isEnabled
    }

    override fun onCleared() {
        super.onCleared()
        try {
            application.unregisterReceiver(bluetoothStateReceiver)
        } catch (e: Exception) {
            Log.e(TAG, "Error unregistering receiver", e)
        }
        stopScanning()
    }

    private fun updateState(update: (BeaconState) -> BeaconState) {
        savedStateHandle["beaconState"] = update(beaconState.value)
    }
}

@Parcelize
data class BeaconState(
    val isScanning: Boolean = false,
    val detectedBeacons: @RawValue List<BeaconDevice> = emptyList(),
    val permissionsGranted: Boolean = false,
    val isBluetoothEnabled: Boolean = false,
    val errorMessage: String? = null
) : Parcelable
