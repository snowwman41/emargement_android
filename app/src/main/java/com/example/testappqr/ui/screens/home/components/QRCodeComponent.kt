package com.example.testappqr.ui.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import com.example.testappqr.ui.screens.home.generateQRCode


@Composable
fun QRCodeComponent() {
    val qrContent = "Nom: Monsieur X, Date: DD-MM-YYYY, Emargement: Présent"
    val qrCodeBitmap = generateQRCode(qrContent)

    var course by remember { mutableStateOf("") }


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(value = "course", onValueChange = { course = it })
        Image(bitmap = qrCodeBitmap.asImageBitmap(), contentDescription = "QR Code")
        Button(onClick = { /* Ajouter action de signature ici */ }) {
            Text(text = "Générer un code QR")
        }
    }
}