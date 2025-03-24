package com.example.testappqr.presentation.professor.screens



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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

    NavigationView(navController = navController, title = "Code") {

        Column{
            BasicButton(
                onClick = { viewModel.toggleQRCodeModal(true) },
                text = "Show QR code",
                modifier=Modifier.fillMaxWidth().padding(8.dp)
            )
            HorizontalDivider()
            BeaconView()
        }

        if (moduleState.qrCodeModal) {
            QRCodeView(
                "moduleState.qrCode",
                onDismissRequest = { viewModel.toggleQRCodeModal(false) })
        }
    }
}
