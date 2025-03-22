package com.example.testappqr.presentation.student.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.testappqr.presentation.beacon.BeaconView
import com.example.testappqr.presentation.navigation.StudentNavigationView

@Composable
fun StudentBeaconScreen(navController: NavHostController) {
    StudentNavigationView(
        navController = navController,
        title = "Beacon scanner",
        showBackButton = true
    ) {
        BeaconView()
    }
}