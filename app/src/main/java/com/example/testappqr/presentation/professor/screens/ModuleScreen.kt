package com.example.testappqr.presentation.professor.screens



import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.testappqr.presentation.professor.viewmodels.ModuleViewModel
import com.example.testappqr.presentation.professor.views.AddSessionView
import com.example.testappqr.presentation.professor.views.QRCodeView
import com.example.testappqr.presentation.sharedviews.BasicButton
import com.example.testappqr.presentation.navigation.NavigationView
import com.example.testappqr.presentation.sharedviews.SessionView


@Composable
fun ModuleScreen(
    navController: NavHostController, viewModel: ModuleViewModel = hiltViewModel()
) {

    val moduleState by viewModel.moduleState.collectAsStateWithLifecycle()

    NavigationView(navController = navController, title = "Module", showBackButton = true) {
        Column {
            LaunchedEffect(Unit) { viewModel.getModule() }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                BasicButton(onClick = { viewModel.toggleAddSessionModal(true) }, text = "Add session")
            }
            if (moduleState.module != null) {

                LazyColumn {
                    items(moduleState.module!!.sessions) {
                        SessionView(it, modifier = Modifier
                            .padding(6.dp)
                            .clickable {
                                viewModel.toggleQRCodeModal(
                                    show = true,
                                    it.sessionId!!
                                )
                            })
                    }
                }
            }
        }
        if (moduleState.qrCodeModal) {
            QRCodeView(moduleState.qrCode, onDismissRequest = { viewModel.toggleQRCodeModal(false) })
        }
        if (moduleState.sessionModal) {
            AddSessionView({ viewModel.toggleAddSessionModal(false) })
        }
    }
}
