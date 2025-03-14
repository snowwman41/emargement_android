package com.example.testappqr.presentation.professor.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.testappqr.data.models.SignatureDTO
import com.example.testappqr.presentation.navigation.NavigationView
import com.example.testappqr.presentation.professor.viewmodels.ProfessorSessionVM
import com.example.testappqr.utils.formatDate
import com.example.testappqr.utils.formatTime

@Composable
fun ProfessorSessionScreen(
    navController: NavHostController, professorSessionVM: ProfessorSessionVM = hiltViewModel()
) {
    val professorSessionState by professorSessionVM.professorSessionState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        professorSessionVM.getSession()
    }
    NavigationView(
        navController = navController,
        true,
        if (professorSessionState.session != null) professorSessionState.session!!.sessionName else "null"
    ) {
        if (professorSessionState.session != null) {
            Column(
                modifier = Modifier.padding(10.dp)

            ) {
                Text(
                    formatDate(professorSessionState.session!!.startTime),
                    style = TextStyle(fontSize = 18.sp)
                )
                Text(
                    formatTime(professorSessionState.session!!.startTime) + " - " + formatTime(
                        professorSessionState.session!!.endTime
                    ), style = TextStyle(fontSize = 18.sp)
                )
                LazyColumn {
                    items(professorSessionState.session!!.signatures) { signature ->
                        SignatureView(signature, Modifier
                            .padding(8.dp)
                            .fillParentMaxWidth())
                    }
                }
            }
        }
    }
}

@Composable
fun SignatureView(signature: SignatureDTO, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
    ) {
        Text(signature.studentId,Modifier.padding(10.dp))
    }
}


