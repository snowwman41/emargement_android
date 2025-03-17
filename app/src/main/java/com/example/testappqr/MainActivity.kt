package com.example.testappqr

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.testappqr.data.models.Attributes
import com.example.testappqr.data.models.AuthenticationSuccess
import com.example.testappqr.data.models.SSODTO
import com.example.testappqr.presentation.Beacon.BeaconScannerScreen
import com.example.testappqr.presentation.Beacon.BeaconVM
import com.example.testappqr.presentation.navigation.NavGraph
import com.example.testappqr.theme.EmargementTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.UUID

//@AndroidEntryPoint
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            EmargementTheme {
////                NavGraph(SharedModel())
//                BeaconScannerScreen()
//            }
//        }
//    }
//}
//@AndroidEntryPoint
//class MainActivity : ComponentActivity() {
//    private val beaconViewModel: BeaconVM by viewModels()
//
//    private val permissionRequest = registerForActivityResult(
//        ActivityResultContracts.RequestMultiplePermissions()
//    ) { permissions ->
//        val allGranted = permissions.entries.all { it.value }
//        beaconViewModel.setPermissionsGranted(allGranted)
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            EmargementTheme {
//                NavGraph(SharedModel())
//            }
//        }
//
//        // Check if permissions are already granted
//        if (!beaconViewModel.checkPermissions()) {
//            requestPermissions()
//        } else {
//            beaconViewModel.setPermissionsGranted(true)
//        }
//    }
//
//    private fun requestPermissions() {
//        val permissionList = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//            arrayOf(
//                android.Manifest.permission.ACCESS_FINE_LOCATION,
//                android.Manifest.permission.BLUETOOTH_SCAN,
//                android.Manifest.permission.BLUETOOTH_CONNECT
//            )
//        } else {
//            arrayOf(
//                android.Manifest.permission.ACCESS_FINE_LOCATION,
//                android.Manifest.permission.BLUETOOTH
//            )
//        }
//
//        permissionRequest.launch(permissionList)
//    }
//}
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val beaconViewModel: BeaconVM by viewModels()

    private val requestMultiplePermissions = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.entries.all { it.value }
        beaconViewModel.setPermissionsGranted(allGranted)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            EmargementTheme {
                BeaconScannerScreen()
            }
        }

        requestPermissions()
    }

    private fun requestPermissions() {
        val permissionsToRequest = mutableListOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            permissionsToRequest.add(android.Manifest.permission.BLUETOOTH_SCAN)
            permissionsToRequest.add(android.Manifest.permission.BLUETOOTH_CONNECT)
            permissionsToRequest.add(android.Manifest.permission.BLUETOOTH_ADMIN)
        } else {
            permissionsToRequest.add(android.Manifest.permission.BLUETOOTH)
            permissionsToRequest.add(android.Manifest.permission.BLUETOOTH_ADMIN)
        }

        requestMultiplePermissions.launch(permissionsToRequest.toTypedArray())
    }
}


class SharedModel {
    var apiSSOResponse: SSODTO? = SSODTO(
        authenticationSuccess = AuthenticationSuccess(
            user = "s23022841", attributes = Attributes(
                amuComposante = "sciences",
                coGroup = "AMU.M2_IMI_CCI-SMI5T1-V302-2024",
                mail = "abdelaziz.SOLTANI@etu.univ-amu.fr",
                eduPersonAffiliation = listOf("member", "student"),
                displayName = "Abd elaziz SOLTANI",
                givenName = "Abd elaziz",
                amuCampus = "L",
                supannEtuAnneeInscription = "2024",
                amuDateValidation = "20230823102953Z",
                supannEntiteAffectation = "SC7",
                uid = "s23022841",
                eduPersonPrimaryAffiliation = "student",
                supannEtuEtape = "SMI5T1",
                supannCivilite = "M.",
                eduPersonPrincipalName = "s23022841@univ-amu.fr",
                memberOf = listOf(
                    "cn=amu:ufr:sciences:ldap:etudiants,ou=groups,dc=univ-amu,dc=fr",
                    "cn=amu:campus:luminy:ldap:etudiants,ou=groups,dc=univ-amu,dc=fr"
                ),
                sn = "SOLTANI"
            )
        )
    )
    var moduleId: UUID? = null
    var sessionId: UUID? = null
    val development: Boolean = true

}
