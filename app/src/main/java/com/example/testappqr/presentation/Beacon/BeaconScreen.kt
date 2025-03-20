package com.example.testappqr.presentation.Beacon

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle


@Composable
fun BeaconItem(beacon: BeaconDevice) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = beacon.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Address: ${beacon.address}",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Signal: ${beacon.rssi} dBm",
                style = MaterialTheme.typography.bodyMedium
            )

            beacon.batteryLevel?.let {
                Text(
                    text = "Battery: $it%",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            beacon.manufacturerData?.let {
                Text(
                    text = "Manufacturer: $it",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
@Composable
fun BeaconScannerScreen(
    beaconVM: BeaconVM = hiltViewModel()
) {


    val beaconState by beaconVM.beaconState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        Text(
//            text = "ELA Beacon Scanner",
//            style = MaterialTheme.typography.headlineMedium,
//            modifier = Modifier.padding(bottom = 16.dp)
//        )

        beaconState.errorMessage?.let {
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
                onClick = { beaconVM.startScanning() },
                enabled = !beaconState.isScanning && beaconState.permissionsGranted,
                modifier = Modifier.weight(1f)
            ) {
                Text("Sign to session")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = { beaconVM.stopScanning() },
                enabled = beaconState.isScanning,
                modifier = Modifier.weight(1f)
            ) {
                Text("Stop Scanning")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (beaconState.isScanning) {
            CircularProgressIndicator(
                modifier = Modifier.padding(16.dp)
            )
        }

        Divider(modifier = Modifier.padding(vertical = 8.dp))

        Text(
            text = "Found ${beaconState.detectedBeacons.size} devices",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(beaconState.detectedBeacons.sortedByDescending { it.rssi }) { beacon ->
                BeaconItem(beacon = beacon)
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}