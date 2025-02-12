package com.example.testappqr.ui.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import com.example.testappqr.SharedModel
import com.example.testappqr.models.SessionDTO
import com.example.testappqr.models.SignatureDTO
import com.example.testappqr.network.RetrofitApi
import com.example.testappqr.ui.common.components.BasicButton
import com.example.testappqr.ui.screens.home.generateQRCode
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID


@Composable
fun QRCodeComponent(sharedModel: SharedModel, code : String,function : (String) -> Unit) {
    var show by remember { mutableStateOf(false) }
//    var qrCodeBitmap = generateQRCode(qrContent)
    val coroutine = rememberCoroutineScope()
    var course by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        coroutine.launch {
            function(openSession(sharedModel.sessionId!!).toString())

            show = true
//            generateQRCode(qrContent).asImageBitmap()
        }
    }
    if (show) {

        Image(bitmap = generateQRCode(code).asImageBitmap(), contentDescription = "QR Code", contentScale = ContentScale.Crop)

    }

}

suspend fun getSessions() {
    val response = RetrofitApi.api.getSessions()
    println(response)
}
suspend fun openSession(sessionId: UUID) :String {
    val response = RetrofitApi.api.openSession(sessionId)
    return response
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
//suspend fun postSession(sessionId: UUID, sessionName: String
//) {
//    val response = RetrofitApi.api.postSession(sessionDTO = SessionDTO(
//        sessionId = sessionId,
//        sessionName = sessionName,
//        date = LocalDate.now().toString(),
//        startTime = LocalTime.now().toString(),
//        endTime = LocalTime.now().toString(),
//        signatures = emptySet()
//    )
//    )
//    println(response)
//}
//coroutine.launch {
//    sharedApiResponseModel.apiSSOResponse?.authenticationSuccess?.attributes?.let {
//        postSession(
//            sessionId = UUID.fromString("35ec002e-20fa-445d-9a1c-99a78940722e"),
//            sessionName = "course"
//        )
//    }
//}
//            coroutine.launch {
//                sharedModel.apiSSOResponse?.authenticationSuccess?.attributes?.let {
//                    postSession(
//                        sessionId = UUID.fromString("35ec002e-20fa-445d-9a1c-99a78940722e"),
//                        sessionName = "course"
//                    )
//                }
//            }