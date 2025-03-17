//package com.example.testappqr
//import com.example.testappqr.data.models.Attributes
//import com.example.testappqr.data.models.AuthenticationSuccess
//import com.example.testappqr.data.models.SSODTO
//import android.Manifest
//import android.bluetooth.BluetoothAdapter
//import android.bluetooth.BluetoothManager
//import android.bluetooth.le.BluetoothLeScanner
//import android.bluetooth.le.ScanCallback
//import android.bluetooth.le.ScanFilter
//import android.bluetooth.le.ScanResult
//import android.bluetooth.le.ScanSettings
//import android.content.Context
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.location.LocationManager
//import android.os.Build
//import android.os.Bundle
//import android.os.Handler
//import android.os.Looper
//import android.os.ParcelUuid
//import android.provider.Settings
//import android.util.Log
//import android.widget.Toast
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material3.Button
//import androidx.compose.material3.Card
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.Divider
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateListOf
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
//import com.example.testappqr.theme.EmargementTheme
//import dagger.hilt.android.AndroidEntryPoint
//import java.util.ArrayList
//import java.util.UUID
//
//
//data class BeaconDevice(
//    val address: String,
//    val name: String,
//    val rssi: Int,
//    val batteryLevel: Float? = null,
//    val manufacturerData: String? = null,
//    val lastSeen: Long = System.currentTimeMillis()
//)
//class Beacon(private val context: Context) {
//
//    companion object {
//        private const val TAG = "BeaconScanner"
//        private const val SCAN_PERIOD = 30000L // 30 seconds
//    }
//
//    // Callbacks for communication with Activity
//    interface ScanCallback {
//        fun onScanStarted()
//        fun onScanStopped(deviceCount: Int)
//        fun onDeviceFound(device: BeaconDevice)
//        fun onScanFailed(errorMessage: String)
//    }
//
//    var scanCallback: ScanCallback? = null
//
//    // Bluetooth components
//    private lateinit var bluetoothAdapter: BluetoothAdapter
//    private var bluetoothLeScanner: BluetoothLeScanner? = null
//
//    // State variables
//    private val detectedBeacons = mutableListOf<BeaconDevice>()
//    private var isScanning = false
//    private val handler = Handler(Looper.getMainLooper())
//
//    fun initialize() {
//        setupBluetoothScanner()
//    }
//
//    fun hasRequiredPermissions(): Boolean {
//        val locationPermission = ContextCompat.checkSelfPermission(
//            context, Manifest.permission.ACCESS_FINE_LOCATION
//        ) == PackageManager.PERMISSION_GRANTED
//
//        val bluetoothPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//            ContextCompat.checkSelfPermission(
//                context, Manifest.permission.BLUETOOTH_SCAN
//            ) == PackageManager.PERMISSION_GRANTED
//        } else {
//            ContextCompat.checkSelfPermission(
//                context, Manifest.permission.BLUETOOTH
//            ) == PackageManager.PERMISSION_GRANTED
//        }
//
//        return locationPermission && bluetoothPermission
//    }
//
//    private fun setupBluetoothScanner() {
//        try {
//            val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
//            bluetoothAdapter = bluetoothManager.adapter
//
//            if (bluetoothAdapter == null) {
//                scanCallback?.onScanFailed("Device doesn't support Bluetooth")
//                return
//            }
//
//            if (!bluetoothAdapter.isEnabled) {
//                scanCallback?.onScanFailed("Bluetooth is disabled")
//                return
//            }
//
//            bluetoothLeScanner = bluetoothAdapter.bluetoothLeScanner
//            Log.d(TAG, "Bluetooth scanner initialized successfully")
//        } catch (e: Exception) {
//            Log.e(TAG, "Error initializing bluetooth: ${e.message}")
//            scanCallback?.onScanFailed("Error initializing Bluetooth: ${e.message}")
//        }
//    }
//
//    fun startScanning() {
//        if (!hasRequiredPermissions()) {
//            scanCallback?.onScanFailed("Missing required permissions")
//            return
//        }
//
//        if (!isLocationEnabled()) {
//            promptEnableLocation()
//            return
//        }
//
//        if (!isBluetoothEnabled()) {
//            promptEnableBluetooth()
//            return
//        }
//
//        if (bluetoothLeScanner == null) {
//            setupBluetoothScanner()
//            if (bluetoothLeScanner == null) {
//                scanCallback?.onScanFailed("Bluetooth scanner not available")
//                return
//            }
//        }
//
//        detectedBeacons.clear()
//        isScanning = true
//        scanCallback?.onScanStarted()
//        Log.d(TAG, "Starting scan for beacons")
//
//        // Set scan settings to aggressive mode for best discovery
//        val settings = ScanSettings.Builder()
//            .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
//            .setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES)
//            .setMatchMode(ScanSettings.MATCH_MODE_AGGRESSIVE)
//            .setNumOfMatches(ScanSettings.MATCH_NUM_MAX_ADVERTISEMENT)
//            .setReportDelay(0)
//            .build()
//
//        // Don't use filters to get all devices including ELA beacons
//        val filters = ArrayList<ScanFilter>()
//
//        handler.postDelayed({ stopScanning() }, SCAN_PERIOD)
//
//        try {
//            if (ActivityCompat.checkSelfPermission(
//                    context, Manifest.permission.BLUETOOTH_SCAN
//                ) == PackageManager.PERMISSION_GRANTED
//            ) {
//                bluetoothLeScanner?.startScan(filters, settings, leScanCallback)
//                Log.d(TAG, "Started scanning with aggressive settings")
//            } else {
//                Log.e(TAG, "Missing BLUETOOTH_SCAN permission")
//                scanCallback?.onScanFailed("Missing BLUETOOTH_SCAN permission")
//            }
//        } catch (e: Exception) {
//            Log.e(TAG, "Error starting scan", e)
//            isScanning = false
//            scanCallback?.onScanFailed("Error: ${e.message}")
//        }
//    }
//
//    fun stopScanning() {
//        if (bluetoothLeScanner != null && isScanning) {
//            try {
//                if (ActivityCompat.checkSelfPermission(
//                        context, Manifest.permission.BLUETOOTH_SCAN
//                    ) == PackageManager.PERMISSION_GRANTED
//                ) {
//                    bluetoothLeScanner?.stopScan(leScanCallback)
//                    isScanning = false
//                    Log.d(TAG, "Stopped scanning - found ${detectedBeacons.size} devices")
//                    scanCallback?.onScanStopped(detectedBeacons.size)
//                }
//            } catch (e: Exception) {
//                Log.e(TAG, "Error stopping scan", e)
//            }
//        }
//    }
//
//    private val leScanCallback = object : android.bluetooth.le.ScanCallback() {
//        override fun onScanResult(callbackType: Int, result: ScanResult) {
//            val device = result.device
//            val scanRecord = result.scanRecord
//            var batteryLevel: Float? = null
//            var manufacturerInfo: String? = null
//
//            // Log device found for debugging
//            Log.d(TAG, "Device found: ${device.address}")
//
//            scanRecord?.let { record ->
//                val rawBytes = record.bytes
//                if (rawBytes != null) {
//                    // Full hex dump of all advertising data
//                    Log.d(TAG, "Raw data: ${bytesToHex(rawBytes)}")
//
//                    // Try to identify if this is an ELA beacon
//                    if (isELABeacon(device.address, rawBytes)) {
//                        // Extract battery level using ELA specific logic
//                        batteryLevel = 0f
//                        manufacturerInfo = "ELA Beacon"
//                        Log.d(TAG, "ELA Beacon identified: ${device.address}")
//                    } else {
//                        // Generic manufacturer data extraction
//                        val manufacturerData = record.manufacturerSpecificData
//                        if (manufacturerData != null && manufacturerData.size() > 0) {
//                            val id = manufacturerData.keyAt(0)
//                            manufacturerInfo = "0x${id.toString(16)}"
//                            Log.d(TAG, "Manufacturer ID: $manufacturerInfo")
//                        }
//                    }
//                }
//
//                // Try to find battery service data
//                record.serviceData?.forEach { (uuid, data) ->
//                    Log.d(TAG, "Service UUID: $uuid, data: ${bytesToHex(data)}")
//                    if (uuid.toString().startsWith("0000180f")) {
//                        // Standard Battery Service
//                        if (data.isNotEmpty()) {
//                            batteryLevel = (data[0].toInt() and 0xFF).toFloat()
//                            Log.d(TAG, "Battery from standard service: $batteryLevel%")
//                        }
//                    }
//                }
//            }
//
//            val deviceName = if (ActivityCompat.checkSelfPermission(
//                    context, Manifest.permission.BLUETOOTH_CONNECT
//                ) == PackageManager.PERMISSION_GRANTED
//            ) {
//                device.name ?: "Unknown Device"
//            } else {
//                "Unknown Device (Permission Denied)"
//            }
//
//            val beacon = BeaconDevice(
//                address = device.address,
//                name = deviceName,
//                rssi = result.rssi,
//                batteryLevel = batteryLevel,
//                manufacturerData = manufacturerInfo
//            )
//
//            // Add to our local list
//            val existingIndex = detectedBeacons.indexOfFirst { it.address == beacon.address }
//            if (existingIndex >= 0) {
//                detectedBeacons[existingIndex] = beacon
//            } else {
//                detectedBeacons.add(beacon)
//                Log.d(TAG, "New device found: ${beacon.address}, Name: ${beacon.name}")
//            }
//
//            // Notify through callback
//            handler.post {
//                scanCallback?.onDeviceFound(beacon)
//            }
//        }
//
//        override fun onBatchScanResults(results: List<ScanResult>) {
//            for (result in results) {
//                onScanResult(0, result)
//            }
//        }
//
//        override fun onScanFailed(errorCode: Int) {
//            isScanning = false
//            val errorMessage = when(errorCode) {
//                android.bluetooth.le.ScanCallback.SCAN_FAILED_ALREADY_STARTED -> "Scan already started"
//                android.bluetooth.le.ScanCallback.SCAN_FAILED_APPLICATION_REGISTRATION_FAILED -> "Application registration failed"
//                android.bluetooth.le.ScanCallback.SCAN_FAILED_FEATURE_UNSUPPORTED -> "BLE not supported"
//                android.bluetooth.le.ScanCallback.SCAN_FAILED_INTERNAL_ERROR -> "Internal error"
//                else -> "Unknown error code: $errorCode"
//            }
//            Log.e(TAG, "Scan failed: $errorMessage")
//            handler.post {
//                scanCallback?.onScanFailed(errorMessage)
//            }
//        }
//    }
//
//    // Helper function to identify ELA beacons
//    private fun isELABeacon(address: String, data: ByteArray): Boolean {
//        // Check for ELA MAC address patterns (common prefixes)
//        if (address.startsWith("CC:97:6E:")) {
//            Log.d(TAG, "ELA beacon identified by MAC prefix: $address")
//            return true
//        }
//        return false
//    }
//
//    private fun isLocationEnabled(): Boolean {
//        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
//                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
//    }
//
//    private fun isBluetoothEnabled(): Boolean {
//        return ::bluetoothAdapter.isInitialized && bluetoothAdapter.isEnabled
//    }
//
//    private fun promptEnableLocation() {
//        scanCallback?.onScanFailed("Location services must be enabled for BLE scanning")
//    }
//
//    private fun promptEnableBluetooth() {
//        scanCallback?.onScanFailed("Bluetooth must be enabled for BLE scanning")
//    }
//
//    // Helper function to convert byte array to hex string
//    private fun bytesToHex(bytes: ByteArray): String {
//        val hexChars = "0123456789ABCDEF"
//        val result = StringBuilder(bytes.size * 2)
//        for (b in bytes) {
//            val i = b.toInt() and 0xFF
//            result.append(hexChars[i shr 4])
//            result.append(hexChars[i and 0x0F])
//        }
//        return result.toString()
//    }
//}
//@Composable
//fun BeaconScannerScreen(
//    isScanning: Boolean,
//    beacons: List<BeaconDevice>,
//    onScanClicked: () -> Unit,
//    onStopScanClicked: () -> Unit,
//) {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(
//            text = "ELA Beacon Scanner",
//            style = MaterialTheme.typography.headlineMedium,
//            modifier = Modifier.padding(bottom = 16.dp)
//        )
//
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceEvenly
//        ) {
//            Button(
//                onClick = onScanClicked,
//                enabled = !isScanning,
//                modifier = Modifier.weight(1f)
//            ) {
//                Text("Start Scanning")
//            }
//
//            Spacer(modifier = Modifier.width(8.dp))
//
//            Button(
//                onClick = onStopScanClicked,
//                enabled = isScanning,
//                modifier = Modifier.weight(1f)
//            ) {
//                Text("Stop Scanning")
//            }
//        }
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        if (isScanning) {
//            CircularProgressIndicator(
//                modifier = Modifier.padding(16.dp)
//            )
//        }
//
//        Divider(modifier = Modifier.padding(vertical = 8.dp))
//
//        Text(
//            text = "Found ${beacons.size} devices",
//            style = MaterialTheme.typography.bodyLarge,
//            modifier = Modifier.padding(vertical = 8.dp)
//        )
//
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxWidth()
//                .weight(1f)
//        ) {
//            items(beacons.sortedByDescending { it.rssi }) { beacon ->
//                BeaconItem(beacon = beacon)
//                Spacer(modifier = Modifier.height(4.dp))
//            }
//        }
//    }
//}
//
//@Composable
//fun BeaconItem(beacon: BeaconDevice) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 4.dp),
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//        ) {
//            Text(
//                text = beacon.name,
//                style = MaterialTheme.typography.titleMedium,
//                fontWeight = FontWeight.Bold
//            )
//
//            Spacer(modifier = Modifier.height(4.dp))
//
//            Text(
//                text = "Address: ${beacon.address}",
//                style = MaterialTheme.typography.bodyMedium
//            )
//
//            Text(
//                text = "Signal: ${beacon.rssi} dBm",
//                style = MaterialTheme.typography.bodyMedium
//            )
//
//            beacon.batteryLevel?.let {
//                Text(
//                    text = "Battery: $it%",
//                    style = MaterialTheme.typography.bodyMedium,
//                    fontWeight = FontWeight.Bold
//                )
//            }
//
//            beacon.manufacturerData?.let {
//                Text(
//                    text = "Manufacturer: $it",
//                    style = MaterialTheme.typography.bodyMedium
//                )
//            }
//        }
//    }
//}