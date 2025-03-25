package com.example.testappqr.presentation.professor.screens


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.testappqr.presentation.beacon.BeaconProfessorView
import com.example.testappqr.presentation.login.viewmodels.LoginVM
import com.example.testappqr.presentation.navigation.NavigationView
import com.example.testappqr.presentation.professor.viewmodels.code.ProfessorCodeVM
import com.example.testappqr.presentation.professor.views.QRCodeView
import com.example.testappqr.presentation.sharedviews.BasicButton
import com.example.testappqr.presentation.sharedviews.TextCard


@Composable
fun ProfessorCodeScreen(
    navController: NavHostController,
    loginVM: LoginVM,
    professorCodeVM: ProfessorCodeVM = hiltViewModel(),

    ) {
    val loginState by loginVM.loginState.collectAsStateWithLifecycle()
    val codeState by professorCodeVM.codeState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        loginState.userData?.authenticationSuccess?.attributes?.uid?.let {
            professorCodeVM.getCodes(
                it
            )
        }
    }
    NavigationView(navController = navController, title = "Code", loginVM = loginVM) {

        Column {
            BasicButton(
                onClick = { professorCodeVM.toggleQRCodeModal(true) },
                text = "Show QR code",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            HorizontalDivider()
            codeState.codes?.readableCode?.let { TextCard(it, "Input Code") }
            HorizontalDivider()
            codeState.codes?.let {
                BeaconProfessorView(
                    professorCodeVM = professorCodeVM
                )
            }
        }

        if (codeState.qrCodeModal) {
            codeState.codes?.qrCode?.let {
                QRCodeView(
                    it,
                    onDismissRequest = { professorCodeVM.toggleQRCodeModal(false) })
            }
        }
    }
}

