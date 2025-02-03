package com.example.testappqr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testappqr.models.ApiSSOResponse
import com.example.testappqr.ui.screens.home.HomeScreen
import com.example.testappqr.ui.screens.login.LoginScreen


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App(viewModel())
        }
    }
}
class SharedApiResponseModel : ViewModel() {
    var apiSSOResponse: ApiSSOResponse? = null
}
@Composable
fun App(sharedApiResponseModel:SharedApiResponseModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("home") {
                HomeScreen(navController, sharedApiResponseModel)
            }
        composable("login") { LoginScreen(navController,sharedApiResponseModel) }
    }
}



