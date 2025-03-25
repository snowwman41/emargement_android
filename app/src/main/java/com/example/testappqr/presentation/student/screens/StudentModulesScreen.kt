package com.example.testappqr.presentation.student.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
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
import com.example.testappqr.presentation.navigation.NavigationView
import com.example.testappqr.presentation.navigation.Routes
import com.example.testappqr.presentation.professor.viewmodels.modules.ProfessorModulesVM
import com.example.testappqr.presentation.professor.views.ModuleView
import com.example.testappqr.presentation.professor.views.ModulesView
import com.example.testappqr.presentation.sharedviews.BasicButton

@Composable
fun StudentModulesScreen(navController: NavHostController) {
    NavigationView(navController = navController, title = "My modules") {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("MODULES")
        //            ModulesView(navController)
        }
    }
}

//@Composable
//fun StudentModulesView(
//    navController: NavController,
//    professorVM: ProfessorModulesVM = hiltViewModel()
//) {
//    val professorState by professorVM.professorState.collectAsStateWithLifecycle()
//    LaunchedEffect(Unit) {
//        professorVM.getModules()
//    }
//    LazyColumn {
//        items(professorState.modulesList) { module ->
//            ModuleView(module = module,
//                modifier = Modifier
//                    .padding(6.dp)
//                    .clickable {
////                        navController.navigate(Routes.PROFESSOR_MODULE(module.moduleId.toString()))
////                        navController.navigateToProfessorModule(module.moduleId.toString()
//                    //                        )
//                    }
//            )
//        }
//    }
//}