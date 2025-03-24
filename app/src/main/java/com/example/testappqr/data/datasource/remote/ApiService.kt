package com.example.testappqr.data.datasource.remote



import com.example.testappqr.data.models.CodeDTO
import com.example.testappqr.data.models.SSODTO
import com.example.testappqr.data.models.ModuleDTO
import com.example.testappqr.data.models.ModuleLazyDTO
import com.example.testappqr.data.models.SessionDTO
import com.example.testappqr.data.models.SessionLazyDTO
import com.example.testappqr.data.models.StudentLazyDTO
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
    suspend fun ssoValidatation (@Url url : String): SSODTO

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
//
//    @POST("sign")
//    suspend fun postSignature (@Body signatureDTO: SignatureDTO): List<SignatureDTO>

}

