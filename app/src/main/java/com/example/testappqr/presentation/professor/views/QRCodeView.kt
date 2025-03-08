package com.example.testappqr.presentation.professor.views

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale

//import com.example.testappqr.data.datasource.remote.RetrofitApi

import android.graphics.Bitmap
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import com.example.testappqr.presentation.sharedviews.ModalView
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter

@Composable
fun QRCodeView( code : String, onDismissRequest : () -> Unit, ) {

    ModalView(onDismissRequest = onDismissRequest){
        Image(modifier= Modifier.fillMaxWidth(),
            bitmap = generateQRCode(code).asImageBitmap(),
            contentDescription = "QR Code",
            contentScale = ContentScale.Crop
        )
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
