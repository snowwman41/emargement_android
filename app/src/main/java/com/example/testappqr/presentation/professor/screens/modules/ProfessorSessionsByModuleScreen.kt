package com.example.testappqr.presentation.professor.screens.modules

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
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
import com.example.testappqr.presentation.professor.viewmodels.modules.ProfessorSessionsByModuleVM
import com.example.testappqr.presentation.professor.views.AddSessionView
import com.example.testappqr.presentation.sharedviews.SessionView
import java.util.UUID

@Composable
fun ProfessorSessionsByModuleScreen(
    navController: NavHostController,
    moduleId : UUID,
    professorSessionsByModuleVM: ProfessorSessionsByModuleVM = hiltViewModel(),
    loginVM : LoginVM
) {
    val professorSessionsByModuleState by professorSessionsByModuleVM.professorSessionsByModuleState.collectAsStateWithLifecycle()

    NavigationView(navController, showBackButton = true, loginVM = loginVM) {
        LaunchedEffect(Unit) {
            professorSessionsByModuleVM.getSessionsByModule(moduleId)
        }

        Column (modifier = Modifier.fillMaxSize(),verticalArrangement = Arrangement.SpaceBetween, horizontalAlignment = Alignment.CenterHorizontally){
            LazyColumn(Modifier.fillMaxHeight(0.8f)) {
                items(professorSessionsByModuleState.sessionsList) { session ->
                    SessionView(session, Modifier.clickable {
                        navController.navigate(
                            Routes.PROFESSOR_SESSION_BY_MODULE(
                                session.sessionId.toString(),
                                session.sessionId.toString()
                            )
                        )
                    })
                }
            }
            FilledIconButton(modifier = Modifier.size(60.dp),colors= IconButtonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary,
                disabledContentColor = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.38f),
                disabledContainerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.38f),
            ),onClick = {professorSessionsByModuleVM.showAddSession(true)}) {
                Icon(Icons.Filled.Add, contentDescription = "Back")
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
        if (professorSessionsByModuleState.showAddSession){
            AddSessionView(moduleId,professorSessionsByModuleVM)
        }
    }
}