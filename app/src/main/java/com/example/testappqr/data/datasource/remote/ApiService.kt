package com.example.testappqr.data.datasource.remote



import com.example.testappqr.domain.usecase.util.ApiResult
import com.example.testappqr.models.CodeDTO
import com.example.testappqr.models.SSODTO
import com.example.testappqr.models.ModuleDTO
import com.example.testappqr.models.ModuleLazyDTO
import com.example.testappqr.models.SessionDTO
import com.example.testappqr.models.SessionLazyDTO
import com.example.testappqr.models.SignatureLazyDTO
import com.example.testappqr.models.StudentLazyDTO
import retrofit2.http.Body
import retrofit2.http.GET

import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Url
import java.util.UUID


interface ApiService {




    @POST("addSession")
    suspend fun addSession (@Body sessionDTO: SessionDTO): List<SessionLazyDTO>

    @GET("openSession/{sessionId}")
    suspend fun openSession (@Path("sessionId") sessionId : UUID): SessionLazyDTO

    @GET("closeSession/{sessionId}")
    suspend fun closeSession (@Path("sessionId") sessionId : UUID): Unit




    @POST("auth/token/verify")
    suspend fun verifyToken (): Boolean

    @GET("sessions")
    suspend fun getSessions (): List<SessionLazyDTO>





//working
//validate sso ticket and get the user data
    @GET
    suspend fun ssoValidatation (@Url url : String): ApiResult<SSODTO>

    @GET("sessions/{userId}/{date}")
    suspend fun getSessionsOfUserOnDate (@Path("userId") userId : String, @Path("date") date : String): List<SessionLazyDTO>

    @GET("sessions/{sessionId}")
    suspend fun getSession (@Path("sessionId") sessionId: UUID): SessionDTO

    @GET("teachers/{userId}/modules")
    suspend fun getModules (@Path("userId") userId : String): List<ModuleLazyDTO>

    @GET("modules/{moduleId}")
    suspend fun getModule (@Path("moduleId") moduleId : UUID): ModuleDTO

    @GET("modules/{moduleId}/students")
    suspend fun getStudentsByModule (@Path("moduleId") moduleId : UUID): List<StudentLazyDTO>


    @GET("codes/{userId}")
    suspend fun getCodeByTeacher (@Path("userId") userId : String): CodeDTO

    @GET("students/{userId}/active-sessions")
    suspend fun getActiveSessionOfStudent (@Path("userId") userId : String): List<SessionLazyDTO>

    @GET("sessions/{sessionId}/code")
    suspend fun getStudentCodeBySession (@Path("sessionId") sessionId : UUID): List<CodeDTO>

    @POST("sign")
    suspend fun studentSign (@Body signatureLazyDTO: SignatureLazyDTO): SessionDTO

//
//    @POST("sign")
//    suspend fun postSignature (@Body signatureDTO: SignatureDTO): List<SignatureDTO>

}

