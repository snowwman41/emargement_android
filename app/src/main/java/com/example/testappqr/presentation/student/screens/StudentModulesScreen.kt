package com.example.testappqr.presentation.student.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.testappqr.presentation.login.viewmodels.LoginVM
import com.example.testappqr.presentation.navigation.Routes
import com.example.testappqr.presentation.navigation.StudentNavigationView
import com.example.testappqr.presentation.professor.viewmodels.modules.ProfessorModulesVM

import com.example.testappqr.presentation.sharedviews.DateCard
import com.example.testappqr.presentation.sharedviews.ModuleView
import com.example.testappqr.presentation.student.viewmodels.StudentModulesVM

@Composable
fun StudentModulesScreen(navController: NavHostController,loginVM: LoginVM) {
    StudentNavigationView(navController = navController, title = "My modules", loginVM = loginVM) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DateCard()
            StudentModulesView(navController, loginVM = loginVM)
        }
    }

}
@Composable
fun StudentModulesView(
    navController: NavController,
    studentModulesVM: StudentModulesVM = hiltViewModel(),
    loginVM: LoginVM = hiltViewModel()

) {
    val studentModulesState by studentModulesVM.studentModulesState.collectAsStateWithLifecycle()
    val loginState by loginVM.loginState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        loginState.userData?.authenticationSuccess?.attributes?.let { studentModulesVM.getModules(it.uid) }
    }
    LazyColumn {
        items(studentModulesState.modulesList) { module ->
            ModuleView(module = module,
                modifier = Modifier
                    .padding(6.dp)

            )
        }
    }
}
