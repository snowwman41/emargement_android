package com.example.testappqr.presentation.professor.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.testappqr.data.models.SignatureDTO
import com.example.testappqr.presentation.login.viewmodels.LoginVM
import com.example.testappqr.presentation.navigation.NavigationView
import com.example.testappqr.presentation.professor.viewmodels.home.ProfessorSessionVM
import com.example.testappqr.presentation.sharedviews.DateCard
import com.example.testappqr.presentation.sharedviews.SessionViewHeader

@Composable
fun ProfessorSessionScreen(
    navController: NavHostController,
    professorSessionVM: ProfessorSessionVM = hiltViewModel(),
    loginVM: LoginVM
) {
    val professorSessionState by professorSessionVM.professorSessionState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        professorSessionVM.getSession()
    }

    NavigationView(
        navController = navController,
        showBackButton = true,
        title = professorSessionState.session?.sessionName ?: "Session",
        loginVM = loginVM
    ) {

        professorSessionState.session?.let { session ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                SessionViewHeader(session,professorSessionVM)
                Spacer(modifier = Modifier.height(20.dp))

                LazyColumn {
                    items(session.signatures!!) { signature ->
                        SignatureView(signature)
                    }
                }
            }
        }
    }
}


@Composable
fun SignatureView(signature: SignatureDTO) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = signature.student.lastName, style = TextStyle(fontSize = 20.sp))
                Text(
                    text = signature.student.firstName,
                    style = TextStyle(fontSize = 16.sp, color = Color.Gray)
                )
            }
        }
    }
}




