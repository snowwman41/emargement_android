package com.example.testappqr.presentation.professor.screens.modules

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.testappqr.presentation.login.viewmodels.LoginVM
import com.example.testappqr.presentation.navigation.NavigationView
import com.example.testappqr.presentation.professor.viewmodels.modules.ProfessorStudentsByModuleVM
import com.example.testappqr.presentation.sharedviews.DateCard
import java.util.UUID


@Composable
fun ProfessorStudentsByModuleScreen(
    navController: NavHostController,
    moduleId: String,
    professorStudentsByModuleVM: ProfessorStudentsByModuleVM = hiltViewModel(),
    loginVM: LoginVM
) {

    val professorStudentsByModuleState by professorStudentsByModuleVM.professorStudentsByModuleState.collectAsStateWithLifecycle()

    NavigationView(navController, showBackButton = true, loginVM = loginVM) {
        LaunchedEffect(Unit) {
            professorStudentsByModuleVM.getStudentsByModule(UUID.fromString(moduleId))
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Students enrolled to the module",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            DateCard()
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {

                items(professorStudentsByModuleState.studentsList) { student ->
                    StudentCard("${student.firstName} ${student.lastName}")
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
            Row {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "person icon",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = name, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}
