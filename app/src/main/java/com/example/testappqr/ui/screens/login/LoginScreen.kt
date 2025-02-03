package com.example.testappqr.ui.screens.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import com.example.testappqr.SharedApiResponseModel
import com.example.testappqr.network.RetrofitApi
import com.example.testappqr.ui.screens.login.components.SSOWebViewComponent



@Composable
fun LoginScreen(navController: NavHostController, sharedApiResponseModel: SharedApiResponseModel) {
    var isTokenValid by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isTokenValid = verifyToken()
    }
    if (!isTokenValid) {
        SSOWebViewComponent(navController,sharedApiResponseModel)
    }
}

suspend fun verifyToken(): Boolean {
    return RetrofitApi.api.verifyToken()
}
