package com.example.testappqr.data.repository

import com.example.testappqr.data.models.Attributes
import com.example.testappqr.data.models.AuthenticationSuccess
import com.example.testappqr.data.models.SSODTO
import com.example.testappqr.domain.repository.LoginRepository

class FakeLoginRepositoryImpl : LoginRepository {

    override suspend fun getUserData(request : String): SSODTO {
        return SSODTO(
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
                    uid = "b24028599",
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
    }
}