package com.example.testappqr.ui.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import com.example.testappqr.SharedApiResponseModel
import com.example.testappqr.models.SessionDTO
import com.example.testappqr.models.SignatureDTO
import com.example.testappqr.network.RetrofitApi
import com.example.testappqr.ui.screens.home.generateQRCode
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID


@Composable
fun QRCodeComponent(sharedApiResponseModel: SharedApiResponseModel) {
    val qrContent = "Nom: Monsieur X, Date: DD-MM-YYYY, Emargement: Présent"
    val qrCodeBitmap = generateQRCode(qrContent)
    val coroutine = rememberCoroutineScope()
    var course by remember { mutableStateOf("") }


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(value = "course", onValueChange = { course = it })
        Image(bitmap = qrCodeBitmap.asImageBitmap(), contentDescription = "QR Code")
        Button(onClick = { // coroutine code goes here for example
             }) {
            Text(text = "Générer un code QR")
        }
    }
}

suspend fun getSessions() {
    val response = RetrofitApi.api.getSessions()
    println(response)
}

suspend fun postSignature(
    id: UUID,
    userName: String,
    userFirstName: String,
    sessionId: UUID
) {
    val response = RetrofitApi.api.postSignature(
        signatureDTO = SignatureDTO(
            id,
            userName,
            userFirstName,
            sessionId
        )
    )
    println(response)
}
//coroutine.launch {
//    sharedApiResponseModel.apiSSOResponse?.authenticationSuccess?.attributes?.let {
//        postSignature(
//            id = UUID.randomUUID(),
//            userName = it.displayName,
//            userFirstName = it.displayName,
//            sessionId = UUID.fromString("35ec002e-20fa-445d-9a1c-99a78940722e")
//        )
//    }
//}

//
suspend fun postSession(sessionId: UUID, sessionName: String
) {
    val response = RetrofitApi.api.postSession(sessionDTO = SessionDTO(
        sessionId = sessionId,
        sessionName = sessionName,
        date = LocalDate.now().toString(),
        startTime = LocalTime.now().toString(),
        endTime = LocalTime.now().toString(),
        signatures = emptySet()
    )
    )
    println(response)
}
//coroutine.launch {
//    sharedApiResponseModel.apiSSOResponse?.authenticationSuccess?.attributes?.let {
//        postSession(
//            sessionId = UUID.fromString("35ec002e-20fa-445d-9a1c-99a78940722e"),
//            sessionName = "course"
//        )
//    }
//}
