package com.example.testappqr.presentation.professor.views

//import com.example.testappqr.presentation.navigation.navigateToProfessorModule

import androidx.compose.foundation.clickable
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
import androidx.navigation.NavController
import com.example.testappqr.presentation.login.viewmodels.LoginVM
import com.example.testappqr.presentation.navigation.Routes
import com.example.testappqr.presentation.professor.viewmodels.modules.ProfessorModulesVM
import com.example.testappqr.presentation.sharedviews.ModuleView


@Composable
fun ProfessorModulesView(
    navController: NavController,
    professorModulesVM: ProfessorModulesVM = hiltViewModel(),
    loginVM: LoginVM = hiltViewModel()

) {
    val professorState by professorModulesVM.professorState.collectAsStateWithLifecycle()
    val loginState by loginVM.loginState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        loginState.userData?.authenticationSuccess?.attributes?.let {
            professorModulesVM.getModules(
                it.uid
            )
        }
    }
    LazyColumn {
        items(professorState.modulesList) { module ->
            ModuleView(module = module,
                modifier = Modifier
                    .padding(6.dp)
                    .clickable {
                        navController.navigate(Routes.PROFESSOR_MODULE(module.moduleId.toString()))
//                        navController.navigateToProfessorModule(module.moduleId.toString())
                    }
            )
        }
    }
}

