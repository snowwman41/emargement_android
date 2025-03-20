package com.example.testappqr.presentation.beacon

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
import android.os.Parcelable
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import javax.inject.Inject

data class BeaconDevice(
    val address: String, val name: String, val rssi: Int
)

@HiltViewModel
class BeaconVM @Inject constructor(
    private val application: Application, private val savedStateHandle: SavedStateHandle
) : ViewModel() {


    val beaconState = savedStateHandle.getStateFlow("beaconState", BeaconState())

    companion object {
        private const val TAG = "BeaconVM"
        private const val SCAN_PERIOD = 10000L // 10 seconds scan time
    }

    // Bluetooth components
    private val bluetoothManager =
        application.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter
    private var bluetoothLeScanner: BluetoothLeScanner? = null

    private val bluetoothStateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
//            when (intent.action) {
//                BluetoothAdapter.ACTION_STATE_CHANGED -> {
//                    if (checkBluetoothState()) setupBluetoothScanner()
//                }
//                LocationManager.PROVIDERS_CHANGED_ACTION -> {
//                    checkLocationState()
//                }
//            }
            if (checkLocationAndBluetoothState())
                setupBluetoothScanner()

        }
    }


    init {
        // Register receiver for Bluetooth state changes
//        val filters = IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)
        val filters = IntentFilter().apply {
            addAction(BluetoothAdapter.ACTION_STATE_CHANGED)
            addAction(LocationManager.PROVIDERS_CHANGED_ACTION)
        }
        application.registerReceiver(bluetoothStateReceiver, filters)

        // Initialize Bluetooth scanner
        if (checkLocationAndBluetoothState())
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
            bluetoothLeScanner = bluetoothAdapter.bluetoothLeScanner
        } catch (e: Exception) {
            updateState {
                it.copy(
                    errorMessage = "Error initializing Bluetooth: ${e.message}"
                )
            }
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
        if (!checkPermissions() || !checkLocationAndBluetoothState()) return

        if (bluetoothLeScanner == null) {
            setupBluetoothScanner()
            if (bluetoothLeScanner == null) {
                updateState { it.copy(errorMessage = "Bluetooth scanner not available") }
                return
            }
        }

        updateState {
            it.copy(
                detectedBeacons = emptyList(), isScanning = true, errorMessage = null
            )
        }

        // Set scan settings to aggressive mode for best discovery
        val settings = ScanSettings.Builder().setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
            .setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES)
            .setMatchMode(ScanSettings.MATCH_MODE_AGGRESSIVE)
            .setNumOfMatches(ScanSettings.MATCH_NUM_MAX_ADVERTISEMENT).setReportDelay(0).build()

        // Scan for the user's beacon with device name
        val filters = ArrayList<ScanFilter>()
        filters.add(ScanFilter.Builder().setDeviceName("L ID 007435").build())
        viewModelScope.launch {
            delay(SCAN_PERIOD)
            stopScanning()
        }

        try {
            if (ActivityCompat.checkSelfPermission(
                    application, Manifest.permission.BLUETOOTH_SCAN
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                bluetoothLeScanner?.startScan(filters, settings, leScanCallback)
            } else {
                updateState {
                    it.copy(
                        isScanning = false, errorMessage = "Missing BLUETOOTH_SCAN permission"
                    )
                }
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
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error stopping scan", e)
            }
        }
    }

    private val leScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            val device = result.device

            val deviceName = if (ActivityCompat.checkSelfPermission(
                    application, Manifest.permission.BLUETOOTH_CONNECT
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                device.name ?: "Unknown Device"
            } else {
                "Unknown Device (Permission Denied)"
            }

            val beacon = BeaconDevice(
                address = device.address, name = deviceName, rssi = result.rssi
            )

            // Update beacon list
            viewModelScope.launch {

                val currentList = beaconState.value.detectedBeacons.toMutableList()
                val existingIndex = currentList.indexOfFirst { it.address == beacon.address }
                //refresh existing beacon data
                if (existingIndex >= 0) {
                    currentList[existingIndex] = beacon
                } else {
                    currentList.add(beacon)
                }
                updateState {
                    it.copy(
                        detectedBeacons = currentList
                    )
                }
                // stop scanning when the beacon is found
                if (currentList.size > 0) {
                    stopScanning()
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
                SCAN_FAILED_ALREADY_STARTED -> "Scan already started"
                SCAN_FAILED_APPLICATION_REGISTRATION_FAILED -> "Application registration failed"
                SCAN_FAILED_FEATURE_UNSUPPORTED -> "BLE not supported"
                SCAN_FAILED_INTERNAL_ERROR -> "Internal error"
                else -> "Unknown error code: $errorCode"
            }
            updateState { it.copy(errorMessage = errorMessage) }
        }
    }


    private fun checkPermissions(): Boolean {
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
        if (locationPermission || bluetoothPermission) {
            updateState {
                it.copy(
                    errorMessage = "Missing required permissions"
                )
            }
        }
        return locationPermission && bluetoothPermission
    }

    private fun checkLocationAndBluetoothState(): Boolean {
        val locationManager =
            application.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isLocEnabled =
            locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
            )
        val isBleEnabled = bluetoothAdapter?.isEnabled ?: false

        viewModelScope.launch {
            updateState {
                it.copy(
                    isLocationEnabled = isLocEnabled,
                    isBluetoothEnabled = isBleEnabled
                )
            }

            if (!isLocEnabled && !isBleEnabled) {
                updateState { it.copy(errorMessage = "Location and Bluetooth services must be enabled") }
                stopScanning()
            } else if (!isLocEnabled) {
                updateState { it.copy(errorMessage = "Location service must be enabled") }
                stopScanning()
            } else if (!isBleEnabled) {
                updateState { it.copy(errorMessage = "Bluetooth service must be enabled") }
                stopScanning()
            } else {
                updateState { it.copy(errorMessage = null) }
            }
        }
        return (isLocEnabled && isBleEnabled)
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

    fun getBeaconId(userId: String) {
        viewModelScope.launch { print("getBeaconid") }
        updateState { it.copy(beaconId = "L ID 007435") }
    }

    private fun updateState(update: (BeaconState) -> BeaconState) {
        savedStateHandle["beaconState"] = update(beaconState.value)
    }

}

@Parcelize
data class BeaconState(
    val beaconId: String? = null,
    val isScanning: Boolean = false,
    val detectedBeacons: @RawValue List<BeaconDevice> = emptyList(),
    val permissionsGranted: Boolean = false,
    val isBluetoothEnabled: Boolean = false,
    val isLocationEnabled: Boolean = false,
    val errorMessage: String? = null
) : Parcelable

