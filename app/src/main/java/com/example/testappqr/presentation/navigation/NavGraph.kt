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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
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
fun NavGraph() {
    val navController = rememberNavController()
    val loginVM: LoginVM = hiltViewModel()

    NavHost(navController = navController, startDestination = Routes.LOGIN) {

        //PROFESSOR
        composable(
            Routes.LOGIN,
            enterTransition = Animation.enterTransition,
            exitTransition = Animation.exitTransition,
            popEnterTransition = Animation.popEnterTransition,
            popExitTransition = Animation.popExitTransition
        ) {
            LoginScreen(navController, loginVM)

        }

        composable(
            Routes.PROFESSOR_SESSIONS,
            enterTransition = Animation.enterTransition,
            exitTransition = Animation.exitTransition,
            popEnterTransition = Animation.popEnterTransition,
            popExitTransition = Animation.popExitTransition
        ) {
            ProfessorSessionsScreen(navController, loginVM = loginVM)
        }
        composable(
            Routes.PROFESSOR_SESSION(
                "{sessionId}"
            ),
            enterTransition = Animation.enterTransition,
            exitTransition = Animation.exitTransition,
            popEnterTransition = Animation.popEnterTransition,
            popExitTransition = Animation.popExitTransition,
            arguments = listOf(navArgument("sessionId") { type = NavType.StringType })
        ) {
            ProfessorSessionScreen(navController, loginVM = loginVM)
        }

        composable(
            Routes.PROFESSOR_MODULES,
            enterTransition = Animation.enterTransition,
            exitTransition = Animation.exitTransition,
            popEnterTransition = Animation.popEnterTransition,
            popExitTransition = Animation.popExitTransition
        ) {
            ProfessorModulesScreen(navController,  loginVM = loginVM)
        }
        composable(
            Routes.PROFESSOR_MODULE(
                "{moduleId}",

                ),
            enterTransition = Animation.enterTransition,
            exitTransition = Animation.exitTransition,
            popEnterTransition = Animation.popEnterTransition,
            popExitTransition = Animation.popExitTransition,
            arguments = listOf(navArgument("moduleId") { type = NavType.StringType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("moduleId").let {
                ProfessorModuleNavigationScreen(navController, it!!,loginVM = loginVM)
            }
        }
        composable(
            Routes.PROFESSOR_SESSIONS_BY_MODULE(
                "{moduleId}",

                ), enterTransition = Animation.enterTransition,
            exitTransition = Animation.exitTransition,
            popEnterTransition = Animation.popEnterTransition,
            popExitTransition = Animation.popExitTransition,
            arguments = listOf(navArgument("moduleId") { type = NavType.StringType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("moduleId").let {
                ProfessorSessionsByModuleScreen(navController, UUID.fromString((it!!)),loginVM=loginVM)
            }
        }

        composable(
            Routes.PROFESSOR_STUDENTS_BY_MODULE(
                "{moduleId}",

                ),
            enterTransition = Animation.enterTransition,
            exitTransition = Animation.exitTransition,
            popEnterTransition = Animation.popEnterTransition,
            popExitTransition = Animation.popExitTransition,
            arguments = listOf(navArgument("moduleId") { type = NavType.StringType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("moduleId").let {
                ProfessorStudentsByModuleScreen(navController, it!!,loginVM = loginVM)
            }
        }


        composable(
            Routes.PROFESSOR_SESSION_BY_MODULE(
                "{sessionId}",
                "{moduleId}",

                ), enterTransition = Animation.enterTransition,
            exitTransition = Animation.exitTransition,
            popEnterTransition = Animation.popEnterTransition,
            popExitTransition = Animation.popExitTransition,
            arguments = listOf(navArgument("moduleId") { type = NavType.StringType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("moduleId").let {
                ProfessorSessionScreen(navController, loginVM = loginVM)
            }
        }

        composable(
            Routes.PROFESSOR_CODE,
            enterTransition = Animation.enterTransition,
            exitTransition = Animation.exitTransition,
            popEnterTransition = Animation.popEnterTransition,
            popExitTransition = Animation.popExitTransition
        ) {
            ProfessorCodeScreen(navController, loginVM)
        }
        //STUDENT
        composable(
            Routes.STUDENT_SESSIONS,
            enterTransition = Animation.enterTransition,
            exitTransition = Animation.exitTransition,
            popEnterTransition = Animation.popEnterTransition,
            popExitTransition = Animation.popExitTransition
        ) {
            StudentSessionsScreen(navController, loginVM = loginVM)
        }
        composable(
            Routes.STUDENT_CODE_BY_SESSION(
                "{sessionId}",

                ),
            enterTransition = Animation.enterTransition,
            exitTransition = Animation.exitTransition,
            popEnterTransition = Animation.popEnterTransition,
            popExitTransition = Animation.popExitTransition,
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
            enterTransition = Animation.enterTransition,
            exitTransition = Animation.exitTransition,
            popEnterTransition = Animation.popEnterTransition,
            popExitTransition = Animation.popExitTransition,
            arguments = listOf(navArgument("sessionId") { type = NavType.StringType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("sessionId").let {
                StudentQrCodeScreen(navController)
            }
        }
        composable(
            Routes.STUDENT_MODULES,
            enterTransition = Animation.enterTransition,
            exitTransition = Animation.exitTransition,
            popEnterTransition = Animation.popEnterTransition,
            popExitTransition = Animation.popExitTransition
        ) {
            StudentSessionsScreen(navController, loginVM = loginVM)
        }
        composable(Routes.STUDENT_QRCODE_SCANNER) {

        }
    }

}

object Animation {
    private const val DURATION = 300
    private const val SLIDE_DISTANCE = 100

    val enterTransition: AnimatedContentTransitionScope<*>.() -> EnterTransition = {
        val initialOffset = IntOffset(0, SLIDE_DISTANCE)
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
                durationMillis = DURATION,
                easing = FastOutLinearInEasing
            )
        )
    }

    val popEnterTransition: AnimatedContentTransitionScope<*>.() -> EnterTransition = {
        fadeIn(
            animationSpec = tween(
                durationMillis = DURATION,
                easing = LinearOutSlowInEasing
            )
        )
    }

    val popExitTransition: AnimatedContentTransitionScope<*>.() -> ExitTransition = {
        val targetOffset = IntOffset(0, SLIDE_DISTANCE)
        slideOut(
            targetOffset = { targetOffset },
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        ) + fadeOut(spring())
    }
}

//fun NavGraphBuilder.student(navController: NavHostController) {
//    composable(
//        Routes.STUDENT_MODULES,
//        enterTransition = Animation.enterTransition,
//        exitTransition = Animation.exitTransition,
//        popEnterTransition = Animation.popEnterTransition,
//        popExitTransition = Animation.popExitTransition
//    ) {
//        StudentSessionsScreen(navController)
//    }
//}