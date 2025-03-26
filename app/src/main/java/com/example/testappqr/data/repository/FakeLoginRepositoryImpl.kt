package com.example.testappqr.data.repository

import com.example.testappqr.domain.repository.LoginRepository
import com.example.testappqr.domain.usecase.util.ApiResult
import com.example.testappqr.models.Attributes
import com.example.testappqr.models.AuthenticationSuccess
import com.example.testappqr.models.SSODTO
import retrofit2.HttpException
import java.io.IOException

class FakeLoginRepositoryImpl : LoginRepository {
    //b24028599 student
    //s23022841 teacher
    // change uid and eduPersonPrimaryAffiliation in attributes
    override suspend fun getUserData(request: String): ApiResult<SSODTO> {
        return try {
            val response = SSODTO(
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
            ApiResult.Success(response)
        } catch (e: IOException) {
            ApiResult.Error(e, "Network error occurred")
        } catch (e: HttpException) {
            ApiResult.Error(e, "HTTP error: ${e.code()}")
        } catch (e: Exception) {
            ApiResult.Error(e, e.message ?: "Unknown error occurred")
        }

    }
}