package com.example.testappqr.presentation.professor.screens.modules

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.testappqr.presentation.navigation.NavigationView
import com.example.testappqr.presentation.professor.viewmodels.modules.ProfessorStudentsByModuleVM
import com.example.testappqr.presentation.professor.viewmodels.home.ProfessorSessionVM
import java.util.UUID
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*


@Composable
fun ProfessorStudentsByModuleScreen(navController: NavHostController,
    professorStudentsByModuleVM: ProfessorStudentsByModuleVM = hiltViewModel(), professorSessionVM: ProfessorSessionVM = hiltViewModel()) {

    val professorStudentsByModuleState by professorStudentsByModuleVM.professorStudentsByModuleState.collectAsStateWithLifecycle()
    val professorSessionState by professorSessionVM.professorSessionState.collectAsStateWithLifecycle()

    val sessionName = professorSessionState.session?.sessionName ?: "Students"

    NavigationView(navController, showBackButton = true) {
        LaunchedEffect(Unit) {
            professorStudentsByModuleVM.getStudentsByModule(UUID.randomUUID())
        }

        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text(
                text = sessionName,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(professorStudentsByModuleState.studentsList) { student ->
                    StudentCard(student.firstName)
                }
            }
        }
    }
}
@Composable
fun StudentCard(name: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            Text(text = name, style = MaterialTheme.typography.bodyLarge)
        }
    }
}
