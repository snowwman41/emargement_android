package com.example.testappqr.presentation.professor.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.testappqr.presentation.login.viewmodels.LoginVM
import com.example.testappqr.presentation.navigation.NavigationView
import com.example.testappqr.presentation.navigation.Routes
import com.example.testappqr.presentation.professor.viewmodels.home.ProfessorSessionsVM
import com.example.testappqr.presentation.sharedviews.DateCard
import com.example.testappqr.presentation.sharedviews.SessionView

@Composable
fun ProfessorSessionsScreen(
    navController: NavHostController,
    professorSessionsVM: ProfessorSessionsVM = hiltViewModel(),
    loginVM: LoginVM
) {
    val professorSessionState by professorSessionsVM.professorSessionsState.collectAsStateWithLifecycle()
    val loginState by loginVM.loginState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        loginState.userData?.authenticationSuccess?.attributes?.let {
            professorSessionsVM.getSessions(
                it.uid)
        }
    }

    NavigationView(navController = navController, title = "Today's Sessions", loginVM = loginVM) {
        Column (modifier = Modifier.padding(10.dp)) {
            DateCard()
            LazyColumn {
                items(professorSessionState.sessionsList) { session ->
                    Column(modifier = Modifier.fillMaxWidth()) {
                        SessionView(session,
                            Modifier
                                .clickable {
                                    navController.navigate(Routes.PROFESSOR_SESSION(session.sessionId.toString()))
                                }
                                .padding(vertical = 4.dp)
                        )
                        HorizontalDivider(thickness = 1.dp)
                    }
                }
            }
        }
    }
    if (professorSessionState.isLoading){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }    }

}