package com.example.testappqr.presentation.beacon

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.testappqr.MainActivity
import com.example.testappqr.data.models.CodeType
import com.example.testappqr.presentation.sharedviews.BasicButton
import com.example.testappqr.presentation.student.viewmodels.StudentCodeVM
import java.util.UUID


@Composable
fun BeaconStudentView(
    beaconVM: BeaconVM = hiltViewModel(),
    studentCodeVM: StudentCodeVM,
    sessionId: UUID? = null,
    userId: String? = null

) {

    val beaconState by beaconVM.beaconState.collectAsStateWithLifecycle()
    val studentCodeState by studentCodeVM.studentCodeState.collectAsStateWithLifecycle()

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        beaconVM.setBeaconId(studentCodeState.codes)
    }
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {


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
                text = "Sign in with beacon"
            )

            Spacer(modifier = Modifier.width(8.dp))

        }

        Spacer(modifier = Modifier.height(8.dp))

        if (beaconState.isScanning) {
            CircularProgressIndicator(
                modifier = Modifier.padding(16.dp)
            )
        }

        HorizontalDivider()

        if (beaconState.detectedBeacons.isNotEmpty()) {
            LaunchedEffect(Unit) {
                if (sessionId != null && userId != null) {
                    studentCodeVM.sign(
                        sessionId = sessionId,
                        beaconState.detectedBeacons[0].name,
                        codeType = CodeType.BEACON,
                        studentId = userId
                    )
                }
            }
        }
    }
}

