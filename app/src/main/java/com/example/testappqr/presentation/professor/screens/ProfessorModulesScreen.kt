@file:JvmName("ProfessorModulesScreenKt")

package com.example.testappqr.presentation.professor.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.testappqr.presentation.navigation.NavigationView
import com.example.testappqr.presentation.navigation.Routes

import com.example.testappqr.presentation.professor.views.ProfessorModulesView
import com.example.testappqr.presentation.sharedviews.BasicButton

@Composable
fun ProfessorModulesScreen(navController: NavHostController) {
    NavigationView(navController = navController, title = "My modules") {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BasicButton(text = "Student", onClick = { navController.navigate(Routes.STUDENT) })
            ProfessorModulesView(navController)
        }
    }
}