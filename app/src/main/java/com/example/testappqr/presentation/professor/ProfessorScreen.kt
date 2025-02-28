package com.example.testappqr.presentation.professor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.testappqr.presentation.sharedcomponents.NavigationScreen
import com.example.testappqr.presentation.sharedcomponents.UserComponent

import com.example.testappqr.SharedModel
import com.example.testappqr.presentation.sharedcomponents.BasicButton

@Composable
fun ProfessorScreen(navController: NavHostController, sharedModel : SharedModel) {
    var isStudent by remember{ mutableStateOf(false) }
    NavigationScreen(navController = navController, title = "Modules") {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BasicButton(text = "Student", onClick = { navController.navigate("student") })
            ModulesView(sharedModel,navController)

        }
    }
}