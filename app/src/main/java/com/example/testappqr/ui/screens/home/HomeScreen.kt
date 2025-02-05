package com.example.testappqr.ui.screens.home
import android.annotation.SuppressLint
import android.webkit.CookieManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.testappqr.SharedApiResponseModel
import com.example.testappqr.ui.screens.home.components.QRCodeComponent
import com.example.testappqr.ui.screens.home.components.UserComponent
import com.example.testappqr.ui.screens.home.components.UserSwitchComponent
import com.example.testappqr.ui.screens.home.components.StudentPresenceComponent


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavHostController, sharedApiResponseModel : SharedApiResponseModel) {
    var isStudent by remember{ mutableStateOf(false) }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                val cookieManager = CookieManager.getInstance()
                cookieManager.removeAllCookies(null)
                cookieManager.flush()
                navController.navigate("login")

            }) {
                Icon(Icons.Default.Lock, contentDescription = "Disconnect")
            }
        },
        modifier = Modifier.fillMaxSize(),
    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            UserSwitchComponent(isStudent, onSwitchChange = { isStudent = it })
            UserComponent(sharedApiResponseModel)

            if (isStudent) {
                StudentPresenceComponent(sharedApiResponseModel)
            } else {
                QRCodeComponent(sharedApiResponseModel)
            }
        }
    }
}


