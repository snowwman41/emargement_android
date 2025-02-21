package com.example.testappqr.presentation.student

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import androidx.compose.ui.viewinterop.AndroidView
import androidx.camera.view.PreviewView
import androidx.compose.foundation.border
import com.google.mlkit.vision.barcode.Barcode


import java.util.concurrent.Executors

class QRScannerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QRScannerScreen { scannedData ->
                val resultIntent = Intent().apply {
                    putExtra("SCANNED_QR_CODE", scannedData)
                }
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }
    }
}

@Composable
fun QRScannerScreen(onScanSuccess: (String) -> Unit) {
    val context = LocalContext.current
    val executor = remember { Executors.newSingleThreadExecutor() }
    var scanningActive by remember { mutableStateOf(true) }  // Etat de l'analyse

    Box(modifier = Modifier.fillMaxSize()) {
        // Affichage de la caméra
        CameraPreview(modifier = Modifier.fillMaxSize())

        // Affichage du rectangle pour guider le scan
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .size(250.dp) // You can adjust the size here for the rectangular area
                .border(2.dp, Color.White) // White border to create a rectangle
        )

        // Affichage du bouton de scan
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    // Permet d'activer/désactiver l'analyse
                    scanningActive = !scanningActive
                }
            ) {
                Text(if (scanningActive) "Arrêter le scan" else "Scanner un QR Code")
            }
        }

        // Action de lancer l'analyse de la caméra
        LaunchedEffect(scanningActive) {
            val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()
                val scanner = BarcodeScanning.getClient()
                val imageAnalyzer = ImageAnalysis.Builder().build().apply {
                    setAnalyzer(executor) { imageProxy ->
                        if (scanningActive) {
                            processImage(imageProxy, scanner, onScanSuccess)
                        } else {
                            imageProxy.close()
                        }
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


@Composable
fun CameraPreview(modifier: Modifier = Modifier) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            PreviewView(context).apply {
                implementationMode = PreviewView.ImplementationMode.COMPATIBLE
            }
        }
    )
}

@OptIn(ExperimentalGetImage::class)
private fun processImage(imageProxy: ImageProxy, scanner: BarcodeScanner, onScanSuccess: (String) -> Unit) {
    val mediaImage = imageProxy.image
    if (mediaImage == null) {
        imageProxy.close()
        return
    }

    val inputImage = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

    scanner.process(inputImage)
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
