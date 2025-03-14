package com.example.testappqr.presentation.professor.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.testappqr.presentation.navigation.NavigationView
import com.example.testappqr.presentation.navigation.Routes
import com.example.testappqr.presentation.professor.viewmodels.home.ProfessorSessionsVM
import com.example.testappqr.presentation.sharedviews.SessionView
import java.text.SimpleDateFormat
import java.util.Locale

//
//@Composable
//fun ProfessorSessionsScreen(
//    navController: NavHostController,
//    professorSessionsVM: ProfessorSessionsVM = hiltViewModel()
//) {
//    val professorSessions by professorSessionsVM.professorSessionsState.collectAsStateWithLifecycle()
//    LaunchedEffect(Unit) {
//        professorSessionsVM.getSessions()
//    }
//
//    NavigationView(navController = navController, title = "Today's Sessions") {
//        if (professorSessions.sessionsList != null) {
//            LazyColumn {
//                items(professorSessions.sessionsList!!) { session ->
//                    SessionView(session,
//                        Modifier.clickable {
//                            navController.navigate(Routes.PROFESSOR_SESSION(session.sessionId.toString()))
//                        })
//                }
//            }
//        }
//    }
//
//}


@Composable
fun ProfessorSessionsScreen(
    navController: NavHostController,
    professorSessionsVM: ProfessorSessionsVM = hiltViewModel()
) {
    val professorSessions by professorSessionsVM.professorSessionsState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        professorSessionsVM.getSessions()
    }

    val currentDate = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(java.util.Date())

    NavigationView(navController = navController, title = "Today's Sessions") {
        Column (modifier = Modifier.padding(10.dp)) {
            Text(
                text = currentDate,
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 10.dp)
            )

            LazyColumn {
                items(professorSessions.sessionsList) { session ->
                    Column(modifier = Modifier.fillMaxWidth()) {
                        SessionView(session,
                            Modifier
                                .clickable {
                                    navController.navigate(Routes.PROFESSOR_SESSION(session.sessionId.toString()))
                                }
                                .padding(vertical = 4.dp)
                        )
                        Divider(thickness = 1.dp)
                    }
                }
            }
        }
    }
}