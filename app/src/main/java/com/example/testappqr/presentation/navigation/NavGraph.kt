package com.example.testappqr.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.testappqr.SharedModel
import com.example.testappqr.presentation.login.screens.LoginScreen
import com.example.testappqr.presentation.professor.screens.ModuleScreen
import com.example.testappqr.presentation.professor.screens.ProfessorScreen
import com.example.testappqr.presentation.student.screens.StudentScreen

@Composable
fun NavGraph(sharedModel: SharedModel){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.LOGIN) {
        composable(Routes.LOGIN) {
            LoginScreen(navController, sharedModel)
        }
        composable(Routes.PROFESSOR) {
            ProfessorScreen(navController)
        }
        composable(Routes.PROFESSOR_MODULE("{moduleId}"),
            arguments = listOf(navArgument("moduleId") {type = NavType.StringType})) {
            ModuleScreen(navController)
        }
        composable(Routes.STUDENT) {
            StudentScreen(navController)
        }
        composable(Routes.STUDENT_QRCODE_SCANNER) {

        }
    }

}
