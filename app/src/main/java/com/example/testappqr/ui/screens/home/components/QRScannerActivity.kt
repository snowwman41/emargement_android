package com.example.testappqr.ui.screens.home.components

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage

import java.util.concurrent.Executors

class QRScannerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QRScannerScreen { scannedData ->
                val resultIntent = Intent().apply {
                    putExtra("SCANNED_QR_CODE", scannedData)
                }
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }
    }
}

@Composable
fun QRScannerScreen(onScanSuccess: (String) -> Unit) {
    val context = LocalContext.current
    val executor = remember { Executors.newSingleThreadExecutor() }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Scanner le QR Code")

        LaunchedEffect(Unit) {
            val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()
                val scanner = BarcodeScanning.getClient()
                val imageAnalyzer = ImageAnalysis.Builder().build().apply {
                    setAnalyzer(executor) { imageProxy ->
                        processImage(imageProxy, scanner, onScanSuccess)
                    }
                }

                try {
                    val cameraSelector = androidx.camera.core.CameraSelector.DEFAULT_BACK_CAMERA
                    cameraProvider.bindToLifecycle(context as ComponentActivity, cameraSelector, imageAnalyzer)
                } catch (exc: Exception) {
                    exc.printStackTrace()
                }
            }, ContextCompat.getMainExecutor(context))
        }
    }
}

private fun processImage(imageProxy: ImageProxy, scanner: com.google.mlkit.vision.barcode.BarcodeScanner, onScanSuccess: (String) -> Unit) {
    val mediaImage = imageProxy.image ?: return
    val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

    scanner.process(image)
        .addOnSuccessListener { barcodes ->
            for (barcode in barcodes) {
                if (barcode.valueType == Barcode.TYPE_TEXT) {
                    onScanSuccess(barcode.rawValue ?: "")
                    imageProxy.close()
                    return@addOnSuccessListener
                }
            }
        }
        .addOnFailureListener { imageProxy.close() }
        .addOnCompleteListener { imageProxy.close() }
}
