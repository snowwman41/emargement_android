package com.example.testappqr.presentation.student.views

import android.Manifest
import android.content.Intent
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat

@Composable
fun StudentPresenceView() {
    var isScanning by remember { mutableStateOf(false) }
    var scannedCode by remember { mutableStateOf("") }
    var hasPermission by remember { mutableStateOf(false) }
    RequestCameraPermission(onPermissionGranted = { hasPermission = true })
    if (hasPermission) {
        QrcodeScanner { code ->
            scannedCode = code
        }
    }
}

@Composable
fun RequestCameraPermission(onPermissionGranted: () -> Unit) {
    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                onPermissionGranted()
            } else {
                // Show settings option if needed
                val showRationale = ActivityCompat.shouldShowRequestPermissionRationale(
                    context as androidx.activity.ComponentActivity,
                    Manifest.permission.CAMERA
                )
                if (!showRationale) {
                    // direct to full settings, if the rational sttings dont show
                    Toast.makeText(
                        context,
                        "Please enable camera permission in Settings",
                        Toast.LENGTH_LONG
                    ).show()

                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = android.net.Uri.fromParts("package", context.packageName, null)
                    }
                    context.startActivity(intent)
                } else {
                    Toast.makeText(context, "Camera permission is required", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    )
    LaunchedEffect(Unit) { permissionLauncher.launch(Manifest.permission.CAMERA) }
}
