package com.example.testappqr.presentation.student.screens

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.testappqr.models.CodeType
import com.example.testappqr.presentation.login.viewmodels.LoginVM
import com.example.testappqr.presentation.navigation.StudentNavigationView
import com.example.testappqr.presentation.student.viewmodels.StudentCodeVM
import com.example.testappqr.presentation.student.views.QrcodeScanner
import java.util.UUID

@Composable
fun StudentQrCodeScreen(navController: NavHostController, loginVM: LoginVM,studentCodeVM : StudentCodeVM, sessionId : UUID) {
    StudentNavigationView(
        navController,
        title = "Qr code Scanner",
        showBackButton = true,
        loginVM = loginVM
    ) {

        var hasPermission by rememberSaveable { mutableStateOf(false) }
        val studentCodeState by studentCodeVM.studentCodeState.collectAsStateWithLifecycle()
        val loginState by loginVM.loginState.collectAsStateWithLifecycle()

        RequestCameraPermission(onPermissionGranted = { hasPermission = true })
        if (hasPermission) {
            QrcodeScanner({ code,context ->
                if (studentCodeState.codes[0].qrCode == code) {
                    loginState.userData?.authenticationSuccess?.attributes?.uid?.let {
                        studentCodeVM.sign(
                            sessionId = sessionId,
                            verificationCode = code,
                            codeType = CodeType.QR,
                            studentId = it
                        )
                    }
                    navController.popBackStack()
                } else {
                    Log.e("QRCode DETECTED : ", code)
                    Toast.makeText(context, "QR Code Not Matched", Toast.LENGTH_SHORT).show()
                }

            })
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
                    context as ComponentActivity,
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
                        data = Uri.fromParts("package", context.packageName, null)
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
