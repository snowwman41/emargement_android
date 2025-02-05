package com.example.testappqr.ui.screens.home.components

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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.testappqr.SharedApiResponseModel

@Composable
fun StudentPresenceComponent(sharedApiResponseModel: SharedApiResponseModel) {
    var sessionCode by remember { mutableStateOf("") }
    val context = LocalContext.current

    val qrScannerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val scannedData = result.data?.getStringExtra("SCANNED_QR_CODE") ?: ""
            if (scannedData.isNotEmpty()) {
                sessionCode = scannedData
                Toast.makeText(context, "Session trouvée : $scannedData", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = sessionCode,
            onValueChange = { sessionCode = it },
            label = { Text("Entrer le code de session") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                Toast.makeText(context, "Code soumis : $sessionCode", Toast.LENGTH_SHORT).show()
            }),
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        Button(
            onClick = {
                Toast.makeText(context, "Code soumis : $sessionCode", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        ) {
            Text("Valider le code")
        }

        Button(
            onClick = { qrScannerLauncher.launch(Intent(context, QRScannerActivity::class.java)) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Scanner un QR Code")
        }
    }
}
