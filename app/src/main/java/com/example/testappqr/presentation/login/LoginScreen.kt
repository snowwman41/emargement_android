package com.example.testappqr.presentation.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController

import com.example.testappqr.core.network.RetrofitApi
import com.example.testappqr.data.models.ApiSSOResponse
import com.example.testappqr.data.models.Attributes
import com.example.testappqr.data.models.AuthenticationSuccess
import com.example.testappqr.SharedModel


@Composable
fun LoginScreen(navController: NavHostController, sharedModel: SharedModel) {
    var isTokenValid by remember { mutableStateOf(false) }
    if (sharedModel.development) {

        //mocking the sso
        sharedModel.apiSSOResponse= ApiSSOResponse(
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
        LaunchedEffect(Unit) {
            navController.navigate("professor")
        }

    }else{
        LaunchedEffect(Unit) {
            isTokenValid = verifyToken()
        }
        if (!isTokenValid) {
            SSOWebViewComponent(navController,sharedModel)
        }
    }

}

suspend fun verifyToken(): Boolean {
    return RetrofitApi.api.verifyToken()
}
