package com.example.testappqr.presentation.student.views

import android.util.Log
import androidx.annotation.OptIn
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.Executors
import kotlin.math.min

@Composable
fun QrcodeScanner(onBarcodeDetected: (String) -> Unit) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val previewView = remember { PreviewView(context) }
    val barcodeScanner = remember { BarcodeScanning.getClient() }
    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }

    Box(modifier = Modifier.fillMaxSize()) {
        // Camera Preview
        AndroidView(
            factory = { previewView },
            modifier = Modifier.fillMaxSize()
        )

        // Semi-transparent overlay with a transparent hole in the middle
        Canvas(modifier = Modifier.fillMaxSize()) {
            val scanBoxSize = min(size.width, size.height) * 0.6f
            val topLeft = Offset(
                (size.width - scanBoxSize) / 2,
                (size.height - scanBoxSize) / 2
            )

            // Semi-transparent overlay
            drawRect(
                color = Color.Black.copy(alpha = 0.5f),
                size = size
            )

            // Transparent scan area
            drawRect(
                color = Color.Transparent,
                topLeft = topLeft,
                size = Size(scanBoxSize, scanBoxSize),
                style = Stroke(width = 5f)
            )

            // Draw corner brackets
            val cornerLength = scanBoxSize / 4
            val strokeWidth = 5f
            val cornerColor = Color.Green

            // Top left corner
            drawLine(cornerColor, topLeft, Offset(topLeft.x + cornerLength, topLeft.y), strokeWidth, StrokeCap.Round)
            drawLine(cornerColor, topLeft, Offset(topLeft.x, topLeft.y + cornerLength), strokeWidth, StrokeCap.Round)

            // Top right corner
            val topRight = Offset(topLeft.x + scanBoxSize, topLeft.y)
            drawLine(cornerColor, topRight, Offset(topRight.x - cornerLength, topRight.y), strokeWidth, StrokeCap.Round)
            drawLine(cornerColor, topRight, Offset(topRight.x, topRight.y + cornerLength), strokeWidth, StrokeCap.Round)

            // Bottom left corner
            val bottomLeft = Offset(topLeft.x, topLeft.y + scanBoxSize)
            drawLine(cornerColor, bottomLeft, Offset(bottomLeft.x + cornerLength, bottomLeft.y), strokeWidth, StrokeCap.Round)
            drawLine(cornerColor, bottomLeft, Offset(bottomLeft.x, bottomLeft.y - cornerLength), strokeWidth, StrokeCap.Round)

            // Bottom right corner
            val bottomRight = Offset(topLeft.x + scanBoxSize, topLeft.y + scanBoxSize)
            drawLine(cornerColor, bottomRight, Offset(bottomRight.x - cornerLength, bottomRight.y), strokeWidth, StrokeCap.Round)
            drawLine(cornerColor, bottomRight, Offset(bottomRight.x, bottomRight.y - cornerLength), strokeWidth, StrokeCap.Round)
        }
    }

    LaunchedEffect(key1 = Unit) {
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }

            val imageAnalyzer = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .also { analysis ->
                    analysis.setAnalyzer(cameraExecutor) { imageProxy ->
                        processImageProxy(
                            imageProxy = imageProxy,
                            barcodeScanner = barcodeScanner,
                            onBarcodeDetected = onBarcodeDetected
                        )
                    }
                }

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    CameraSelector.DEFAULT_BACK_CAMERA,
                    preview,
                    imageAnalyzer
                )
            } catch (e: Exception) {
                Log.e("QRScanner", "Camera binding failed", e)
            }
        }, ContextCompat.getMainExecutor(context))
    }

    // Clean up resources when leaving the composition
    DisposableEffect(key1 = Unit) {
        onDispose {
            cameraExecutor.shutdown()
            barcodeScanner.close()
        }
    }
}

@OptIn(ExperimentalGetImage::class)
private fun processImageProxy(
    imageProxy: ImageProxy,
    barcodeScanner: com.google.mlkit.vision.barcode.BarcodeScanner,
    onBarcodeDetected: (String) -> Unit
) {
    val mediaImage = imageProxy.image ?: run {
        imageProxy.close()
        return
    }

    val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
    val imageWidth = imageProxy.width.toFloat()
    val imageHeight = imageProxy.height.toFloat()

    // Define our scan area (center 40% of the image)
    val scanAreaSize = min(imageWidth, imageHeight) * 0.4f
    val centerX = imageWidth / 2
    val centerY = imageHeight / 2
    val scanRect = android.graphics.RectF(
        centerX - scanAreaSize / 2,
        centerY - scanAreaSize / 2,
        centerX + scanAreaSize / 2,
        centerY + scanAreaSize / 2
    )

    barcodeScanner.process(image)
        .addOnSuccessListener { barcodes ->
            // Find the first valid barcode in the scan area
            val validBarcode = barcodes.firstOrNull { barcode ->
                val boundingBox = barcode.boundingBox
                if (boundingBox != null && barcode.rawValue != null) {
                    val centerX = boundingBox.exactCenterX()
                    val centerY = boundingBox.exactCenterY()
                    scanRect.contains(centerX, centerY)
                } else {
                    false
                }
            }

            // If we found a valid barcode, send it
            validBarcode?.rawValue?.let { onBarcodeDetected(it) }
        }
        .addOnFailureListener { exception ->
            Log.e("QRScanner", "Barcode scanning failed", exception)
        }
        .addOnCompleteListener {
            imageProxy.close()
        }
}