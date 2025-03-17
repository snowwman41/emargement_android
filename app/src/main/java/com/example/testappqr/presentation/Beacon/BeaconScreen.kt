package com.example.testappqr.presentation.Beacon

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

//@Composable
//fun BeaconScreen(
//    viewModel: BeaconVM = hiltViewModel()
//) {
//    val isScanning by viewModel.isScanning.collectAsStateWithLifecycle()
//    val beacons by viewModel.detectedBeacons.collectAsStateWithLifecycle()
//    val permissionsGranted by viewModel.permissionsGranted.collectAsStateWithLifecycle()
//    val errorMessage by viewModel.errorMessage.collectAsStateWithLifecycle()
//    println("permissionsGranted")
//
//    println(permissionsGranted)
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        if (errorMessage != null) {
//            Text(
//                text = errorMessage!!,
//                color = MaterialTheme.colorScheme.error,
//                style = MaterialTheme.typography.bodyMedium,
//                modifier = Modifier.padding(bottom = 8.dp)
//            )
//        }
//
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceEvenly
//        ) {
//            Button(
//                onClick = { viewModel.startScanning() },
//                enabled = !isScanning && permissionsGranted
//            ) {
//                Text("Start Scanning")
//            }
//
//            Button(
//                onClick = { viewModel.stopScanning() },
//                enabled = isScanning
//            ) {
//                Text("Stop Scanning")
//            }
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        if (isScanning) {
//            CircularProgressIndicator(modifier = Modifier.padding(8.dp))
//        }
//
//        Text(
//            text = "Found ${beacons.size} devices",
//            style = MaterialTheme.typography.titleMedium,
//            modifier = Modifier.padding(vertical = 8.dp)
//        )
//
//        LazyColumn(modifier = Modifier.fillMaxWidth()) {
//            items(beacons) { device ->
//                BeaconDeviceItem(device)
//                Divider()
//            }
//        }
//    }
//}
//
@Composable
fun BeaconDeviceItem(device: BeaconDevice) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = device.name,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
            Text(text = "Address: ${device.address}")
            Text(text = "RSSI: ${device.rssi} dBm")
            device.batteryLevel?.let {
                Text(text = "Battery: ${it.toInt()}%")
            }
            device.manufacturerData?.let {
                Text(text = "Manufacturer: $it")
            }
        }
    }
}

@Composable
fun BeaconScannerScreen(
    viewModel: BeaconVM = hiltViewModel()
) {
    val isScanning by viewModel.isScanning.collectAsStateWithLifecycle()
    val beacons by viewModel.detectedBeacons.collectAsStateWithLifecycle()
    val permissionsGranted by viewModel.permissionsGranted.collectAsStateWithLifecycle()
    val isBluetoothEnabled by viewModel.isBluetoothEnabled.collectAsStateWithLifecycle()
    val errorMessage by viewModel.errorMessage.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "ELA Beacon Scanner",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        errorMessage?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { viewModel.startScanning() },
                enabled = !isScanning && permissionsGranted && isBluetoothEnabled,
                modifier = Modifier.weight(1f)
            ) {
                Text("Start Scanning")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = { viewModel.stopScanning() },
                enabled = isScanning,
                modifier = Modifier.weight(1f)
            ) {
                Text("Stop Scanning")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (isScanning) {
            CircularProgressIndicator(
                modifier = Modifier.padding(16.dp)
            )
        }

        Divider(modifier = Modifier.padding(vertical = 8.dp))

        Text(
            text = "Found ${beacons.size} devices",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(beacons.sortedByDescending { it.rssi }) { beacon ->
                BeaconItem(beacon = beacon)
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}