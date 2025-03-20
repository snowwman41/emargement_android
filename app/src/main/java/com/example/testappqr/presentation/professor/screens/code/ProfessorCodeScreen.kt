package com.example.testappqr.presentation.professor.screens



import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.testappqr.presentation.beacon.BeaconView
import com.example.testappqr.presentation.professor.viewmodels.code.ProfessorCodeVM
import com.example.testappqr.presentation.professor.views.QRCodeView
import com.example.testappqr.presentation.sharedviews.BasicButton
import com.example.testappqr.presentation.navigation.NavigationView


@Composable
fun ProfessorCodeScreen(
    navController: NavHostController, viewModel: ProfessorCodeVM = hiltViewModel()
) {

    val moduleState by viewModel.moduleState.collectAsStateWithLifecycle()

    NavigationView(navController = navController, title = "Module") {
//        BeaconScreen (
//            isScanning = isScanning.value,
//            beacons = detectedBeacons,
//            onScanClicked = { startScanning() },
//            onStopScanClicked = { beacon.stopScanning() }
//        )
        BeaconView()
//        Column {
//            BasicButton(
//                onClick = { viewModel.toggleQRCodeModal(true) },
//                text = "Show QR code"
//            )
////            Row { Text("code")
////                IconButton(content = Icon()) { }
////            }
//        }
//
//
//        if (moduleState.qrCodeModal) {
//            QRCodeView("moduleState.qrCode", onDismissRequest = { viewModel.toggleQRCodeModal(false) })

    }
}
