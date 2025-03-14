
package com.example.testappqr

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.example.testappqr.theme.EmargementTheme
import com.example.testappqr.data.models.SSODTO
import com.example.testappqr.data.models.Attributes
import com.example.testappqr.data.models.AuthenticationSuccess
import com.example.testappqr.presentation.navigation.NavGraph
import com.example.testappqr.presentation.student.views.BeaconScannerService
import dagger.hilt.android.AndroidEntryPoint

import java.util.UUID

//
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            EmargementTheme {
//                NavGraph(SharedModel())
//            }
//        }
//    }
//}
@AndroidEntryPoint
class MainActivity : ComponentActivity() {  // Changement d'extension vers AppCompatActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        startBeaconScannerService()

        setContent {
            EmargementTheme {
                NavGraph(SharedModel())
            }
        }
    }

    private fun startBeaconScannerService() {
        val intent = Intent(this, BeaconScannerService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent)
        } else {
            startService(intent)
        }
        Toast.makeText(this, "Beacon scanning started", Toast.LENGTH_SHORT).show()
    }
}

class SharedModel {
    var apiSSOResponse: SSODTO? = SSODTO(
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



