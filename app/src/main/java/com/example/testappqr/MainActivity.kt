package com.example.attendanceapp

import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.common.BitMatrix
import androidx.compose.ui.tooling.preview.Preview
import com.example.testappqr.ui.theme.TestAppQRTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QRCodeScreen()
        }
    }
}

@Composable
fun QRCodeScreen() {
    val qrContent = "Nom: Monsieur X, Date: DD-MM-YYYY, Emargement: Présent"
    val qrCodeBitmap = generateQRCode(qrContent)

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(bitmap = qrCodeBitmap.asImageBitmap(), contentDescription = "QR Code")
        Button(onClick = { /* Ajouter action de signature ici */ }) {
            Text(text = "Signer votre présence")
        }
    }
}

fun generateQRCode(content: String): Bitmap {
    val writer = QRCodeWriter()
    val bitMatrix: BitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 500, 500, mapOf(EncodeHintType.MARGIN to 1))
    return Bitmap.createBitmap(bitMatrix.width, bitMatrix.height, Bitmap.Config.RGB_565).apply {
        for (x in 0 until bitMatrix.width) {
            for (y in 0 until bitMatrix.height) {
                setPixel(x, y, if (bitMatrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TestAppQRTheme {
        QRCodeScreen()
    }
}

