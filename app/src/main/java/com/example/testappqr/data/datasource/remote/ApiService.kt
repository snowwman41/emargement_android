package com.example.testappqr.data.datasource.remote



import com.example.testappqr.data.models.SSODTO
import com.example.testappqr.data.models.ModuleDTO
import com.example.testappqr.data.models.ModuleLazyDTO
import com.example.testappqr.data.models.SessionLazyDTO
import retrofit2.http.Body
import retrofit2.http.GET

import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Url
import java.util.UUID


interface ApiService {

    @GET("modules/professor/{professorId}")
    suspend fun getModules (@Path("professorId") professorId : String): List<ModuleLazyDTO>

    @GET("modules/{moduleId}")
    suspend fun getModule (@Path("moduleId") moduleId : UUID): ModuleDTO

    @POST("addSession")
    suspend fun addSession (@Body sessionLazyDTO: SessionLazyDTO): List<SessionLazyDTO>

    @GET("openSession/{sessionId}")
    suspend fun openSession (@Path("sessionId") sessionId : UUID): SessionLazyDTO

    @GET("closeSession/{sessionId}")
    suspend fun closeSession (@Path("sessionId") sessionId : UUID): Unit

    @GET("students/{userId}/active-sessions")
    suspend fun getActiveSessions (@Path("userId") userId : String): List<SessionLazyDTO>

    @POST("auth/token/verify")
    suspend fun verifyToken (): Boolean

    @GET("sessions")
    suspend fun getSessions (): List<SessionLazyDTO>

    //validate sso ticket and get the user data
    @GET
    suspend fun ssoValidatation (@Url url : String): SSODTO

    @POST("create-module")
    suspend fun createModule(@Body moduleDTO: ModuleLazyDTO): List<ModuleLazyDTO>



//
////    @GET("sessions/{sessionId}")
////    suspend fun getSession (): List<SessionDTO>
//
//    @POST("sign")
//    suspend fun postSignature (@Body signatureDTO: SignatureDTO): List<SignatureDTO>

}

