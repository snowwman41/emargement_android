package com.example.testappqr.presentation.professor.screens.modules



import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.testappqr.presentation.professor.viewmodels.code.ProfessorCodeVM
import com.example.testappqr.presentation.sharedviews.BasicButton
import com.example.testappqr.presentation.navigation.NavigationView
import com.example.testappqr.presentation.navigation.Routes


@Composable
fun ProfessorModuleNavigationScreen(
    navController: NavHostController, moduleId : String,viewModel: ProfessorCodeVM = hiltViewModel()
) {

    val moduleState by viewModel.moduleState.collectAsStateWithLifecycle()

    NavigationView(navController = navController, title = "Module", showBackButton = true) {

        Column {

            BasicButton(onClick = {navController.navigate(Routes.PROFESSOR_SESSIONS_BY_MODULE(moduleId))}, text ="sessions" )
            BasicButton(onClick = {navController.navigate(Routes.PROFESSOR_STUDENTS_BY_MODULE(moduleId))}, text = "STUDENTS")
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
