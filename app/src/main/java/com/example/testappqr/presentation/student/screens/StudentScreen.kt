package com.example.testappqr.presentation.student.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.testappqr.SharedModel
import com.example.testappqr.presentation.sharedviews.BasicButton
import com.example.testappqr.presentation.sharedviews.NavigationScreen
import com.example.testappqr.presentation.student.views.StudentPresenceView

@Composable
fun StudentScreen(navController: NavHostController, sharedModel: SharedModel) {

    NavigationScreen(navController = navController, title = "Student") {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BasicButton(text = "Professor", onClick = { navController.navigate("professor") })
            StudentPresenceView()
        }

    }
}