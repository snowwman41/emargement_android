package com.example.testappqr.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class ApiSSOResponse(
    val authenticationSuccess: AuthenticationSuccess
)

data class AuthenticationSuccess(

    val user: String,
    val attributes: Attributes
)

data class Attributes(
    val amuComposante: String,
    val coGroup: String,
    val mail: String,
    val eduPersonAffiliation: List<String>,
    val displayName: String,
    val givenName: String,
    val amuCampus: String,
    val supannEtuAnneeInscription: String,
    val amuDateValidation: String,
    val supannEntiteAffectation: String,
    val uid: String,
    val eduPersonPrimaryAffiliation: String,
    val supannEtuEtape: String,
    val supannCivilite: String,
    val eduPersonPrincipalName: String,
    val memberOf: List<String>,
    val sn: String
)
