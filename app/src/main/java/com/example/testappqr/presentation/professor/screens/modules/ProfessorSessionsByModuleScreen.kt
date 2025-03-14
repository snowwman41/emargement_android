package com.example.testappqr.presentation.professor.screens.modules

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.testappqr.presentation.navigation.NavigationView
import com.example.testappqr.presentation.navigation.Routes
import com.example.testappqr.presentation.professor.viewmodels.modules.ProfessorSessionsByModuleVM
import com.example.testappqr.presentation.sharedviews.SessionView

@Composable
fun ProfessorSessionsByModuleScreen(navController: NavHostController, professorSessionsByModuleVM: ProfessorSessionsByModuleVM = hiltViewModel()) {
    val professorSessionsByModuleState by professorSessionsByModuleVM.professorSessionsByModuleState.collectAsStateWithLifecycle()

    NavigationView(navController,showBackButton = true)  {
        LaunchedEffect(Unit) {
            professorSessionsByModuleVM.getSessionsByModule()
        }

        LazyColumn {
            items(professorSessionsByModuleState.sessionsList) { session ->
                SessionView(session, Modifier.clickable {
                    navController.navigate(Routes.PROFESSOR_SESSION_BY_MODULE(session.sessionId.toString(),session.sessionId.toString()))
                })
            }
        }
    }
}