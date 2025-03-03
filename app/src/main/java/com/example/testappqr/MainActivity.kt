
package com.example.testappqr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testappqr.core.theme.EmargementTheme
import com.example.testappqr.data.models.ApiSSOResponse
import com.example.testappqr.data.models.Attributes
import com.example.testappqr.data.models.AuthenticationSuccess
import com.example.testappqr.presentation.login.LoginScreen
import com.example.testappqr.presentation.module.ModuleScreen
import com.example.testappqr.presentation.module.ModuleViewModel
import com.example.testappqr.presentation.professor.ProfessorScreen
import com.example.testappqr.presentation.student.StudentScreen
import dagger.hilt.android.AndroidEntryPoint

import java.util.UUID

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmargementTheme {

                App(SharedModel())

            }
        }
    }
}

class SharedModel {
    var apiSSOResponse: ApiSSOResponse? = ApiSSOResponse(
        authenticationSuccess = AuthenticationSuccess(
            user = "s23022841",
            attributes = Attributes(
                amuComposante = "sciences",
                coGroup = "AMU.M2_IMI_CCI-SMI5T1-V302-2024",
                mail = "abdelaziz.SOLTANI@etu.univ-amu.fr",
                eduPersonAffiliation = listOf("member", "student"),
                displayName = "Abd elaziz SOLTANI",
                givenName = "Abd elaziz",
                amuCampus = "L",
                supannEtuAnneeInscription = "2024",
                amuDateValidation = "20230823102953Z",
                supannEntiteAffectation = "SC7",
                uid = "s23022841",
                eduPersonPrimaryAffiliation = "student",
                supannEtuEtape = "SMI5T1",
                supannCivilite = "M.",
                eduPersonPrincipalName = "s23022841@univ-amu.fr",
                memberOf = listOf(
                    "cn=amu:ufr:sciences:ldap:etudiants,ou=groups,dc=univ-amu,dc=fr",
                    "cn=amu:campus:luminy:ldap:etudiants,ou=groups,dc=univ-amu,dc=fr"
                ),
                sn = "SOLTANI"
            )
        )
    )
    var moduleId: UUID? = null
    var sessionId: UUID? = null
    val development: Boolean = true

}

@Composable
fun App(sharedModel: SharedModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController, sharedModel)
        }

        composable("professor") {
            ProfessorScreen(navController, sharedModel)
        }
        composable("student") {
            StudentScreen(navController, sharedModel)
        }
        composable("professor/module") {
            ModuleScreen(navController, sharedModel, viewModel = ModuleViewModel())
        }
    }
}

