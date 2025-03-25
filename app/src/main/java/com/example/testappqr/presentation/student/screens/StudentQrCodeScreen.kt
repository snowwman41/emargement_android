package com.example.testappqr.presentation.student.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.testappqr.presentation.login.viewmodels.LoginVM
import com.example.testappqr.presentation.navigation.StudentNavigationView
import com.example.testappqr.presentation.student.views.StudentPresenceView

@Composable
fun StudentQrCodeScreen(navController: NavHostController,loginVM: LoginVM) {
    StudentNavigationView(navController, title = "Qr code Scanner", showBackButton = true, loginVM = loginVM) {
        StudentPresenceView()
    }
}