package com.example.testappqr.presentation.module

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale

import kotlinx.coroutines.launch
import com.example.testappqr.SharedModel
import com.example.testappqr.core.network.RetrofitApi
import java.util.UUID

import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter

@Composable
fun QRCodeView(sharedModel: SharedModel, code : String, function : (String) -> Unit) {
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



fun generateQRCode(content: String): Bitmap {
    val writer = QRCodeWriter()
    val bitMatrix: BitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 500, 500, mapOf(
        EncodeHintType.MARGIN to 1))
    return Bitmap.createBitmap(bitMatrix.width, bitMatrix.height, Bitmap.Config.RGB_565).apply {
        for (x in 0 until bitMatrix.width) {
            for (y in 0 until bitMatrix.height) {
                setPixel(x, y, if (bitMatrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE)
            }
        }
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
//suspend fun postSignature(
//    id: UUID,
//    userName: String,
//    userFirstName: String,
//    sessionId: UUID
//) {
//    val response = RetrofitApi.api.postSignature(
//        signatureDTO = SignatureDTO(
//            id,
//            userName,
//            userFirstName,
//            sessionId,
//            ""
//        )
//    )
//    println(response)
//}
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