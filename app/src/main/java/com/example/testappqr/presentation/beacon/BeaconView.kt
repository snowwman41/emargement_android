package com.example.testappqr.presentation.beacon

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.testappqr.MainActivity


@Composable
fun BeaconItemView(beacon: BeaconDevice) {
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
        }
    }
}

@Composable
fun BeaconView(
    beaconVM: BeaconVM = hiltViewModel()
) {

    val beaconState by beaconVM.beaconState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        beaconVM.getBeaconId("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


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
                onClick = {
                    if (!beaconVM.checkPermissions()) {
                        // Request permissions directly if they're not granted
                        (context as? MainActivity)?.requestPermissions(isMainActivity = false)
                    } else {
                        // Only start scanning if permissions are granted
                        beaconVM.startScanning()
                    }},
                enabled = !beaconState.isScanning &&
                        !beaconState.permissionsGranted &&
                        beaconState.isBluetoothEnabled &&
                        beaconState.isLocationEnabled,
                modifier = Modifier.weight(1f)
            ) {
                Text("Test your beacon")
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
        Card(Modifier.fillMaxWidth()) {
            if (beaconState.beaconId != null) {
                Text(beaconState.beaconId!!)
            } else {
                Text("undefined")
            }


        }

        Spacer(modifier = Modifier.height(8.dp))

        if (beaconState.isScanning) {
            CircularProgressIndicator(
                modifier = Modifier.padding(16.dp)
            )
        }

        HorizontalDivider()

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
                BeaconItemView(beacon = beacon)
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}