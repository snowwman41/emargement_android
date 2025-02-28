
package com.example.testappqr.presentation.student

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.mlkit.vision.barcode.BarcodeScanner

@Composable
fun StudentPresenceView() {
    var sessionCode by remember { mutableStateOf("") }
    var isScanning by remember { mutableStateOf(false) }
    var scannedCode by remember { mutableStateOf("") }
    var hasPermission by remember { mutableStateOf(false) }

    RequestCameraPermission { hasPermission = true }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { isScanning = !isScanning }) {
        Text("Toggle scan")
    }
        if (hasPermission) {
            if (isScanning) {
                BarcodeScanner { code ->
                    scannedCode = code
                    isScanning = false
                }
            } else {
                if (scannedCode.isNotEmpty()) {

                    Row(horizontalArrangement = Arrangement.Center) {
                        Text(
                            "Signed to the session",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                    }
                }
            }
        } else {
            Text("Waiting for camera permission...")
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
                Toast.makeText(context, "Camera permission is required", Toast.LENGTH_LONG).show()
            }
        }
    )

    LaunchedEffect(Unit) {
        permissionLauncher.launch(Manifest.permission.CAMERA)
    }
}
