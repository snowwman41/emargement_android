package com.example.testappqr.core.network



import com.example.testappqr.data.models.ApiSSOResponse
import com.example.testappqr.data.models.ModuleDTO
import com.example.testappqr.data.models.ModuleLazyDTO
import com.example.testappqr.data.models.SessionDTO
import com.example.testappqr.data.models.SignatureDTO
import retrofit2.http.Body
import retrofit2.http.GET

import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Url
import java.util.UUID


interface ApiService {

    @POST("auth/token/verify")
    suspend fun verifyToken (): Boolean

    @GET("sessions")
    suspend fun getSessions (): List<SessionDTO>

    @POST("sign")
    suspend fun postSignature (@Body signatureDTO: SignatureDTO): List<SignatureDTO>

    @POST("addSession")
    suspend fun postSession (@Body sessionDTO: SessionDTO): List<SessionDTO>

    @GET
    suspend fun casValidate (@Url url : String): ApiSSOResponse

    @GET("modules/professor/{professorId}")
    suspend fun getModules (@Path("professorId") professorId : String): List<ModuleLazyDTO>

    @POST("create-module")
    suspend fun createModule(@Body moduleDTO: ModuleLazyDTO): List<ModuleLazyDTO>

    @GET("modules/{moduleId}")
    suspend fun getModule (@Path("moduleId") moduleId : UUID): ModuleDTO

    @GET("openSession/{sessionId}")
    suspend fun openSession (@Path("sessionId") sessionId : UUID): String

    @GET("closeSession/{sessionId}")
    suspend fun closeSession (@Path("sessionId") sessionId : UUID): Unit
}

