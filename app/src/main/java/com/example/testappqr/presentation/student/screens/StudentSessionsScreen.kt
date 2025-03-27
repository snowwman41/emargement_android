package com.example.testappqr.presentation.student.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
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
import com.example.testappqr.presentation.login.viewmodels.LoginVM
import com.example.testappqr.presentation.navigation.NavigationView
import com.example.testappqr.presentation.navigation.Routes
import com.example.testappqr.presentation.navigation.StudentNavigationView
import com.example.testappqr.presentation.sharedviews.BasicButton
import com.example.testappqr.presentation.sharedviews.DateCard
import com.example.testappqr.presentation.sharedviews.SessionView
import com.example.testappqr.presentation.student.viewmodels.StudentSessionsVM
import java.text.SimpleDateFormat
import java.util.Locale




@Composable
fun StudentSessionsScreen(
    navController: NavHostController,
    studentSessionsVM: StudentSessionsVM = hiltViewModel(),
    loginVM: LoginVM
) {
    val studentSessions by studentSessionsVM.studentSessionsState.collectAsStateWithLifecycle()
    val loginState by loginVM.loginState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        loginState.userData?.authenticationSuccess?.attributes?.let {
            studentSessionsVM.getSessions(
                it.uid)
        }
    }

    StudentNavigationView(navController = navController, title = "Today's Sessions", loginVM = loginVM) {
        Column (modifier = Modifier.padding(10.dp)) {
            DateCard()
            LazyColumn {
                items(studentSessions.sessionsList) { session ->
                    Column(modifier = Modifier.fillMaxWidth()) {
                        SessionView(session,
                            Modifier
                                .clickable {
                                    navController.navigate(Routes.STUDENT_CODE_BY_SESSION(session.sessionId.toString()))
                                }
                                .padding(vertical = 4.dp)
                        )
                        HorizontalDivider(thickness = 1.dp, modifier = Modifier.padding(6.dp))
                    }
                }
            }
        }
    }
}