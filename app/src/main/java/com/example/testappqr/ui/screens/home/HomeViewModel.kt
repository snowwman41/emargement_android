package com.example.testappqr.ui.screens.home

import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter


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