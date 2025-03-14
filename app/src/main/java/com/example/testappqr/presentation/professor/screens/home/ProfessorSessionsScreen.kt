package com.example.testappqr.presentation.professor.screens.home

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
import com.example.testappqr.presentation.professor.viewmodels.home.ProfessorSessionsVM
import com.example.testappqr.presentation.sharedviews.SessionView

@Composable
fun ProfessorSessionsScreen(
    navController: NavHostController,
    professorSessionsVM: ProfessorSessionsVM = hiltViewModel()
) {
    val professorSessions by professorSessionsVM.professorSessionsState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        professorSessionsVM.getSessions()
    }

    NavigationView(navController = navController, title = "Today's Sessions") {
        if (professorSessions.sessionsList != null) {
            LazyColumn {
                items(professorSessions.sessionsList!!) { session ->
                    SessionView(session,
                        Modifier.clickable {
                            navController.navigate(Routes.PROFESSOR_SESSION(session.sessionId.toString()))
                        })
                }
            }
        }
    }

}