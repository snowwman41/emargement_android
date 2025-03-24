package com.example.testappqr.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.testappqr.presentation.login.screens.LoginScreen
import com.example.testappqr.presentation.login.viewmodels.LoginVM
import com.example.testappqr.presentation.professor.screens.ProfessorCodeScreen
import com.example.testappqr.presentation.professor.screens.home.ProfessorSessionScreen
import com.example.testappqr.presentation.professor.screens.home.ProfessorSessionsScreen
import com.example.testappqr.presentation.professor.screens.modules.ProfessorModuleNavigationScreen
import com.example.testappqr.presentation.professor.screens.modules.ProfessorModulesScreen
import com.example.testappqr.presentation.professor.screens.modules.ProfessorSessionsByModuleScreen
import com.example.testappqr.presentation.professor.screens.modules.ProfessorStudentsByModuleScreen
import com.example.testappqr.presentation.student.screens.StudentCodeScreen
import com.example.testappqr.presentation.student.screens.StudentQrCodeScreen
import com.example.testappqr.presentation.student.screens.StudentSessionsScreen
import java.util.UUID

@Composable
fun NavGraph(loginVM: LoginVM) {
    val navController = rememberNavController()

    val enterTransition: AnimatedContentTransitionScope<*>.() -> EnterTransition = {
        val initialOffset = IntOffset(0, 100)
        slideIn(
            initialOffset = { initialOffset },
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessLow
            )
        ) + fadeIn(spring())
    }

    val exitTransition: AnimatedContentTransitionScope<*>.() -> ExitTransition = {
        fadeOut(
            animationSpec = tween(
                durationMillis = 250,
                easing = FastOutLinearInEasing
            )
        )
    }

    val popEnterTransition: AnimatedContentTransitionScope<*>.() -> EnterTransition = {
        fadeIn(
            animationSpec = tween(
                durationMillis = 300,
                easing = LinearOutSlowInEasing
            )
        )
    }

    val popExitTransition: AnimatedContentTransitionScope<*>.() -> ExitTransition = {
        val targetOffset = IntOffset(0, 100)
        slideOut(
            targetOffset = { targetOffset },
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        ) + fadeOut(spring())
    }
    NavHost(navController = navController, startDestination = Routes.LOGIN) {

        //PROFESSOR
        composable(
            Routes.LOGIN,
            enterTransition = enterTransition,
            exitTransition = exitTransition,
            popEnterTransition = popEnterTransition,
            popExitTransition = popExitTransition
        ) {
            LoginScreen(navController, loginVM)

        }

        composable(
            Routes.PROFESSOR_SESSIONS,
            enterTransition = enterTransition,
            exitTransition = exitTransition,
            popEnterTransition = popEnterTransition,
            popExitTransition = popExitTransition
        ) {
            ProfessorSessionsScreen(navController, loginVM = loginVM)
        }
        composable(
            Routes.PROFESSOR_SESSION(
                "{sessionId}"
            ),
            enterTransition = enterTransition,
            exitTransition = exitTransition,
            popEnterTransition = popEnterTransition,
            popExitTransition = popExitTransition,
            arguments = listOf(navArgument("sessionId") { type = NavType.StringType })
        ) {
            ProfessorSessionScreen(navController)
        }

        composable(
            Routes.PROFESSOR_MODULES,
            enterTransition = enterTransition,
            exitTransition = exitTransition,
            popEnterTransition = popEnterTransition,
            popExitTransition = popExitTransition
        ) {
            ProfessorModulesScreen(navController, loginVM)
        }
        composable(
            Routes.PROFESSOR_MODULE(
                "{moduleId}",

                ),
            enterTransition = enterTransition,
            exitTransition = exitTransition,
            popEnterTransition = popEnterTransition,
            popExitTransition = popExitTransition,
            arguments = listOf(navArgument("moduleId") { type = NavType.StringType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("moduleId").let {
                ProfessorModuleNavigationScreen(navController, it!!)
            }
        }
        composable(
            Routes.PROFESSOR_SESSIONS_BY_MODULE(
                "{moduleId}",

                ), enterTransition = enterTransition,
            exitTransition = exitTransition,
            popEnterTransition = popEnterTransition,
            popExitTransition = popExitTransition,
            arguments = listOf(navArgument("moduleId") { type = NavType.StringType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("moduleId").let {
                ProfessorSessionsByModuleScreen(navController, UUID.fromString((it!!)))
            }
        }

        composable(
            Routes.PROFESSOR_STUDENTS_BY_MODULE(
                "{moduleId}",

                ),
            enterTransition = enterTransition,
            exitTransition = exitTransition,
            popEnterTransition = popEnterTransition,
            popExitTransition = popExitTransition,
            arguments = listOf(navArgument("moduleId") { type = NavType.StringType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("moduleId").let {
                ProfessorStudentsByModuleScreen(navController)
            }
        }


        composable(
            Routes.PROFESSOR_SESSION_BY_MODULE(
                "{sessionId}",
                "{moduleId}",

                ), enterTransition = enterTransition,
            exitTransition = exitTransition,
            popEnterTransition = popEnterTransition,
            popExitTransition = popExitTransition,
            arguments = listOf(navArgument("moduleId") { type = NavType.StringType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("moduleId").let {
                ProfessorSessionScreen(navController)
            }
        }

        composable(
            Routes.PROFESSOR_CODE,
            enterTransition = enterTransition,
            exitTransition = exitTransition,
            popEnterTransition = popEnterTransition,
            popExitTransition = popExitTransition
        ) {
            ProfessorCodeScreen(navController)
        }
        //STUDENT
        composable(
            Routes.STUDENT_SESSIONS,
            enterTransition = enterTransition,
            exitTransition = exitTransition,
            popEnterTransition = popEnterTransition,
            popExitTransition = popExitTransition
        ) {
            StudentSessionsScreen(navController)
        }
        composable(
            Routes.STUDENT_CODE_BY_SESSION(
                "{sessionId}",

                ),
            enterTransition = enterTransition,
            exitTransition = exitTransition,
            popEnterTransition = popEnterTransition,
            popExitTransition = popExitTransition,
            arguments = listOf(navArgument("sessionId") { type = NavType.StringType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("sessionId").let {
                StudentCodeScreen(navController, sessionId = UUID.fromString(it!!))
            }
        }
        composable(
            Routes.STUDENT_QRCODE_SCANNER_BY_SESSION(
                "{sessionId}",

                ),
            enterTransition = enterTransition,
            exitTransition = exitTransition,
            popEnterTransition = popEnterTransition,
            popExitTransition = popExitTransition,
            arguments = listOf(navArgument("sessionId") { type = NavType.StringType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("sessionId").let {
                StudentQrCodeScreen(navController)
            }
        }
        composable(
            Routes.STUDENT_MODULES,
            enterTransition = enterTransition,
            exitTransition = exitTransition,
            popEnterTransition = popEnterTransition,
            popExitTransition = popExitTransition
        ) {
            StudentSessionsScreen(navController)
        }
        composable(Routes.STUDENT_QRCODE_SCANNER) {

        }
    }

}
