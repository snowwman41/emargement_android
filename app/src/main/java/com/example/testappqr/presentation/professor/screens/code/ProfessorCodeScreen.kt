package com.example.testappqr.presentation.professor.screens.code

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.testappqr.presentation.navigation.NavigationView

@Composable
fun ProfessorCodeScreen(navController: NavHostController) {
    NavigationView(navController) {Text("Code Screen") }

}