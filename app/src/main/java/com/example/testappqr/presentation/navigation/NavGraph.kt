package com.example.testappqr.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.testappqr.SharedModel
import com.example.testappqr.presentation.login.screens.LoginScreen
import com.example.testappqr.presentation.professor.screens.ProfessorCodeScreen
import com.example.testappqr.presentation.professor.screens.modules.ProfessorModuleNavigationScreen
import com.example.testappqr.presentation.professor.screens.modules.ProfessorModulesScreen
import com.example.testappqr.presentation.professor.screens.home.ProfessorSessionScreen
import com.example.testappqr.presentation.professor.screens.modules.ProfessorSessionsByModuleScreen
import com.example.testappqr.presentation.professor.screens.home.ProfessorSessionsScreen
import com.example.testappqr.presentation.professor.screens.modules.ProfessorStudentsByModuleScreen
import com.example.testappqr.presentation.student.screens.StudentScreen

@Composable
fun NavGraph(sharedModel: SharedModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.LOGIN) {

        //PROFESSOR
        composable(Routes.LOGIN) {
            LoginScreen(navController, sharedModel)
        }

        composable(Routes.PROFESSOR_SESSIONS) {
            ProfessorSessionsScreen(navController)
        }
        composable(Routes.PROFESSOR_SESSION("{sessionId}"),
            arguments = listOf(navArgument("sessionId") { type = NavType.StringType })
        ) {
            ProfessorSessionScreen(navController)
        }



        composable(Routes.PROFESSOR_MODULES) {
            ProfessorModulesScreen(navController)
        }
        composable(Routes.PROFESSOR_MODULE("{moduleId}"),
            arguments = listOf(navArgument("moduleId") { type = NavType.StringType })
        ) { backStackEntry ->
            ProfessorModuleNavigationScreen(navController, backStackEntry.arguments!!.getString("moduleId")!!)
        }
        composable(Routes.PROFESSOR_SESSIONS_BY_MODULE("{moduleId}"),
            arguments = listOf(navArgument("moduleId") { type = NavType.StringType })
        ) { backStackEntry ->
            backStackEntry.arguments!!.getString("moduleId").let {
                ProfessorSessionsByModuleScreen(navController)
            }
        }

        composable(Routes.PROFESSOR_STUDENTS_BY_MODULE("{moduleId}"),
            arguments = listOf(navArgument("moduleId") { type = NavType.StringType })
        ) { backStackEntry ->
            backStackEntry.arguments!!.getString("moduleId").let {
                ProfessorStudentsByModuleScreen(navController)
            }
        }


        composable(Routes.PROFESSOR_SESSION_BY_MODULE("{sessionId}","{moduleId}"),
            arguments = listOf(navArgument("moduleId") { type = NavType.StringType })
        ) { backStackEntry ->
            backStackEntry.arguments!!.getString("moduleId").let {
                ProfessorSessionScreen(navController)
            }
        }

        composable(Routes.PROFESSOR_CODE) {
            ProfessorCodeScreen(navController)
        }
        //STUDENT
        composable(Routes.STUDENT) {
            StudentScreen(navController)
        }
        composable(Routes.STUDENT_QRCODE_SCANNER) {

        }
    }

}
