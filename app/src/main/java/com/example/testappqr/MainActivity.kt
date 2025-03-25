package com.example.testappqr

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.example.testappqr.models.Attributes
import com.example.testappqr.models.AuthenticationSuccess
import com.example.testappqr.models.SSODTO
import com.example.testappqr.presentation.beacon.BeaconVM

import com.example.testappqr.presentation.navigation.NavGraph
import com.example.testappqr.theme.EmargementTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.UUID
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import com.example.testappqr.presentation.login.viewmodels.LoginVM

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val beaconVM: BeaconVM by viewModels()

    private val requestMultiplePermissions = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.entries.all { it.value }
        beaconVM.setPermissionsGranted(allGranted)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            EmargementTheme {
                NavGraph()
            }
        }

        requestLocalisationAndBluetoothPermissions(isMainActivity = true)
        beaconVM.clearPermissionRequest()
    }
    fun requestLocalisationAndBluetoothPermissions(isMainActivity: Boolean) {
        val permissionsToRequest = mutableListOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            permissionsToRequest.add(android.Manifest.permission.BLUETOOTH_SCAN)
            permissionsToRequest.add(android.Manifest.permission.BLUETOOTH_CONNECT)
        } else {
            permissionsToRequest.add(android.Manifest.permission.BLUETOOTH)
        }

        // Check if any permission is denied and we can't show system dialog
        var canAskPermissions = true
        for (permission in permissionsToRequest) {
            if (checkSelfPermission(permission) == PackageManager.PERMISSION_DENIED &&
                !shouldShowRequestPermissionRationale(permission)) {
                canAskPermissions = false
                break
            }
        }

        if (canAskPermissions) {
            // We can show system permission dialog
            requestMultiplePermissions.launch(permissionsToRequest.toTypedArray())
        } else if (!isMainActivity) {
            // User has selected "Don't ask again", direct to settings
            Toast.makeText(
                this,
                "Please enable required permissions in app settings",
                Toast.LENGTH_LONG
            ).show()

            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.fromParts("package", packageName, null)
            }
            startActivity(intent)
        }
    }
}


