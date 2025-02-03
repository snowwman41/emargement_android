//package com.example.attendanceapp
//
//import android.os.Bundle
//import android.widget.Toast
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material3.Button
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.tooling.preview.Preview
//import com.example.testappqr.ui.theme.TestAppQRTheme
//import com.journeyapps.barcodescanner.BarcodeCallback
//import com.journeyapps.barcodescanner.CompoundBarcodeView
//
//class ScanQRCodeActivity : ComponentActivity() {
//
//    private lateinit var barcodeScannerView: CompoundBarcodeView
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        barcodeScannerView = CompoundBarcodeView(this)
//
//        setContent {
//            QRCodeScanner(barcodeScannerView)
//        }
//    }
//
////    override fun onResume() {
////        super.onResume()
////        barcodeScannerView.resume() // Reprend la lecture du scanner
////    }
////
////    override fun onPause() {
////        super.onPause()
////        barcodeScannerView.pause() // Met en pause la lecture du scanner
////    }
//}
//
//@Composable
//fun QRCodeScanner(barcodeScannerView: CompoundBarcodeView) {
//    val context = LocalContext.current
//
//    barcodeScannerView.decodeSingle(object : BarcodeCallback {
//        override fun barcodeResult(result: com.journeyapps.barcodescanner.BarcodeResult?) {
//            result?.let {
//                val scannedData = it.text
//                Toast.makeText(context, "QR Code Scanné: $scannedData", Toast.LENGTH_SHORT).show()
//                // Gérer l'émargement ici (base de donnée)
//            }
//        }
//
//        override fun possibleResultPoints(resultPoints: List<com.google.zxing.ResultPoint>?) {}
//    })
//
//    Button(
//        modifier = Modifier.fillMaxSize(),
//        onClick = { barcodeScannerView.decodeSingle {} }
//    ) {
//        Text(text = "Scan QR Code")
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun QRCodeScannerPreview() {
//    TestAppQRTheme {
//        QRCodeScannerPreviewUI()
//    }
//}
//
//@Composable
//fun QRCodeScannerPreviewUI() {
//    Button(
//        modifier = Modifier.fillMaxSize(),
//        onClick = { /* Action pour prévisualisation */ }
//    ) {
//        Text(text = "Scan QR Code")
//    }
//}
