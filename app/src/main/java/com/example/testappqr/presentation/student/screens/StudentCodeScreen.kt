package com.example.testappqr.presentation.student.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.testappqr.presentation.navigation.Routes
import com.example.testappqr.presentation.navigation.StudentNavigationView
import com.example.testappqr.presentation.sharedviews.BasicButton
import java.util.UUID

@Composable
fun StudentCodeScreen(navController: NavHostController,sessionId : UUID){
    StudentNavigationView(navController = navController, title = "Code", showBackButton = true) {
        Column {
            BasicButton(onClick = {navController.navigate(Routes.STUDENT_QRCODE_SCANNER_BY_SESSION(sessionId.toString()))}, text = "Scan QR Code")
            BasicButton(onClick = {}, text = "Sign up with beacon")
            BasicButton(onClick = {}, text = "validate with code")
        }
    }

}