package com.example.testappqr.presentation.beacon

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.testappqr.models.CodeDTO
import com.example.testappqr.models.CodeType
import com.example.testappqr.presentation.professor.viewmodels.code.ProfessorCodeVM
import com.example.testappqr.presentation.sharedviews.BasicButton
import com.example.testappqr.presentation.sharedviews.TextCard
import com.example.testappqr.presentation.student.viewmodels.StudentCodeVM
import java.util.UUID


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
    beaconVM: BeaconVM = hiltViewModel(),
    isProfessor: Boolean = true,
    studentCodeVM: StudentCodeVM,
    professorCodeVM: ProfessorCodeVM = hiltViewModel(),
    sessionId: UUID? = null,
    userId: String? = null

) {

    val beaconState by beaconVM.beaconState.collectAsStateWithLifecycle()
    val studentCodeState by studentCodeVM.studentCodeState.collectAsStateWithLifecycle()
    val professorCodeState by professorCodeVM.codeState.collectAsStateWithLifecycle()

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        if (isProfessor) {
            if (professorCodeState.codes != null) {
                beaconVM.setBeaconId(listOf(professorCodeState.codes!!))
            }
        } else {
            beaconVM.setBeaconId(studentCodeVM.studentCodeState.value.codes)
        }


    }
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

        if (professorCodeState.codes != null) {
            TextCard(professorCodeState.codes!!.beaconId ?: "Beacon not available", "Beacon ID")
            Spacer(modifier = Modifier.height(10.dp))
        }
        beaconState.errorMessage?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            BasicButton(
                onClick = {
                    if (!beaconVM.checkPermissions()) {
                        // Request permissions directly if they're not granted
                        (context as? MainActivity)?.requestLocalisationAndBluetoothPermissions(
                            isMainActivity = false
                        )
                    } else {
                        // Only start scanning if permissions are granted
                        beaconVM.startScanning()
                    }
                },
                isEnabled = !beaconState.isScanning &&
                        !beaconState.permissionsGranted &&
                        beaconState.isBluetoothEnabled &&
                        beaconState.isLocationEnabled,
                modifier = Modifier.weight(1f),
                text = "Test beacon"
            )

            Spacer(modifier = Modifier.width(8.dp))

            BasicButton(
                onClick = { beaconVM.stopScanning() },
                isEnabled = beaconState.isScanning,
                text = "Stop Scanning",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (beaconState.isScanning) {
            CircularProgressIndicator(
                modifier = Modifier.padding(16.dp)
            )
        }

        HorizontalDivider()
        if (isProfessor) {
            Text(
                text = "Found ${beaconState.detectedBeacons.size} devices",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(vertical = 8.dp)
            )


            //
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                items(beaconState.detectedBeacons.sortedByDescending { it.rssi }) { beacon ->
                    BeaconItemView(beacon = beacon)
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        } else {
            if (beaconState.detectedBeacons.isNotEmpty()) {
                LaunchedEffect(Unit) {
                    Log.e("BEACN CODE", beaconState.detectedBeacons[0].name)
                    if (sessionId != null && userId != null) {
                        studentCodeVM.sign(
                            sessionId = sessionId,
                            beaconState.detectedBeacons[0].name,
                            codeType = CodeType.BEACON,
                            studentId = userId
                        )
                    }
                }
                Toast.makeText(
                    LocalContext.current,
                    "You have succesfully signed",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
