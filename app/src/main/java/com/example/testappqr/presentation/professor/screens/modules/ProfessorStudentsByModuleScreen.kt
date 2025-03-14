package com.example.testappqr.presentation.professor.screens.modules

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.testappqr.presentation.navigation.NavigationView
import com.example.testappqr.presentation.navigation.Routes
import com.example.testappqr.presentation.professor.viewmodels.ProfessorStudentsByModuleVM
import com.example.testappqr.presentation.sharedviews.SessionView
import java.util.UUID


@Composable
fun ProfessorStudentsByModuleScreen(navController: NavHostController,
    professorStudentsByModuleVM: ProfessorStudentsByModuleVM = hiltViewModel()) {

    val professorStudentsByModuleState by professorStudentsByModuleVM.professorStudentsByModuleState.collectAsStateWithLifecycle()
    NavigationView(navController,showBackButton = true)  {
        LaunchedEffect(Unit) {
            professorStudentsByModuleVM.getStudentsByModule(UUID.randomUUID())
        }

        LazyColumn {
            items(professorStudentsByModuleState.studentsList) { student ->
                Text(student.firstName)
            }
        }
}}