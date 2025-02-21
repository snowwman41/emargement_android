
package com.example.testappqr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testappqr.core.theme.EmargementTheme
import com.example.testappqr.data.models.ApiSSOResponse
import com.example.testappqr.presentation.login.LoginScreen
import com.example.testappqr.presentation.module.ModuleScreen
import com.example.testappqr.presentation.professor.ProfessorScreen
import com.example.testappqr.presentation.student.StudentScreen

import java.util.UUID

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmargementTheme {

                App(SharedModel())

            }
        }
    }
}

class SharedModel {
    var apiSSOResponse: ApiSSOResponse? = null
    var moduleId: UUID? = null
    var sessionId: UUID? = null
    val development: Boolean = true

}

@Composable
fun App(sharedModel: SharedModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController, sharedModel)
        }

        composable("professor") {
            ProfessorScreen(navController, sharedModel)
        }
        composable("student") {
            StudentScreen(navController, sharedModel)
        }
        composable("professor/module") {
            ModuleScreen(navController, sharedModel)
        }
    }
}

