package com.example.testappqr.ui.screens.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.testappqr.SharedApiResponseModel
import com.example.testappqr.models.ApiSSOResponse
import com.example.testappqr.models.AuthenticationSuccess

@Composable

fun UserComponent(sharedApiResponseModel: SharedApiResponseModel) {
    Column {
        sharedApiResponseModel.apiSSOResponse?.
            authenticationSuccess?.let { authenticationSuccess ->
            Text(authenticationSuccess.user)
            Text((authenticationSuccess.attributes.mail))
            Text((authenticationSuccess.attributes.displayName))
            Text((authenticationSuccess.attributes.coGroup))


        }
    }
}