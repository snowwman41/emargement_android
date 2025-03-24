package com.example.testappqr.presentation.professor.screens.modules



import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.testappqr.presentation.professor.viewmodels.code.ProfessorCodeVM
import com.example.testappqr.presentation.navigation.NavigationView
import com.example.testappqr.presentation.navigation.Routes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testappqr.presentation.login.viewmodels.LoginVM


@Composable
fun ProfessorModuleNavigationScreen(
    navController: NavHostController,
    moduleId: String,
    loginVM : LoginVM
) {

    NavigationView(navController = navController, title = "Module", showBackButton = true, loginVM = loginVM) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ModuleButton(text = "SESSIONS") {
                    navController.navigate(Routes.PROFESSOR_SESSIONS_BY_MODULE(moduleId))
                }
                ModuleButton(text = "STUDENTS") {
                    navController.navigate(Routes.PROFESSOR_STUDENTS_BY_MODULE(moduleId))
                }
            }
        }
    }
}

@Composable
fun ModuleButton(text: String, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(60.dp)
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = text, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }


//        Column {
//            LaunchedEffect(Unit) { viewModel.getModule() }
//
//            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
//                BasicButton(onClick = { viewModel.toggleAddSessionModal(true) }, text = "Add session")
//            }
//            if (moduleState.module != null) {
//
//                LazyColumn {
//                    items(moduleState.module!!.sessions) {
//                        SessionView(it, modifier = Modifier
//                            .padding(6.dp)
//                            .clickable {
//                                viewModel.toggleQRCodeModal(
//                                    show = true,
//                                    it.sessionId!!
//                                )
//                            })
//                    }
//                }
//            }
//        }
//        if (moduleState.qrCodeModal) {
//            QRCodeView(moduleState.qrCode, onDismissRequest = { viewModel.toggleQRCodeModal(false) })
//        }
//        if (moduleState.sessionModal) {
//            AddSessionView({ viewModel.toggleAddSessionModal(false) })
//        }
    }
}
