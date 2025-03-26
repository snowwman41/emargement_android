package com.example.testappqr.data.datasource.remote


import com.example.testappqr.models.CodeDTO
import com.example.testappqr.models.ModuleDTO
import com.example.testappqr.models.ModuleLazyDTO
import com.example.testappqr.models.SSODTO
import com.example.testappqr.models.SessionDTO
import com.example.testappqr.models.SessionLazyDTO
import com.example.testappqr.models.SignatureLazyDTO
import com.example.testappqr.models.SpecialityCreationDTO
import com.example.testappqr.models.SpecialityLazyDTO
import com.example.testappqr.models.StudentDTO
import com.example.testappqr.models.TeacherLazyDTO
import com.example.testappqr.models.UserCreationDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Url
import java.util.UUID


interface ApiService {


    //working
//validate sso ticket and get the user data
    @GET
    suspend fun ssoValidatation(@Url url: String): SSODTO

    @GET("sessions/{userId}/{date}")
    suspend fun getSessionsOfUserOnDate(
        @Path("userId") userId: String,
        @Path("date") date: String
    ): List<SessionLazyDTO>

    @GET("sessions/{sessionId}")
    suspend fun getSession(@Path("sessionId") sessionId: UUID): SessionDTO

    @GET("teachers/{userId}/modules")
    suspend fun professorModules(@Path("userId") userId: String): List<ModuleLazyDTO>

    @GET("students/{userId}/modules")
    suspend fun studentModules(@Path("userId") userId: String): List<ModuleLazyDTO>

    @GET("modules/{moduleId}")
    suspend fun getModule(@Path("moduleId") moduleId: UUID): ModuleDTO

    @GET("modules/{moduleId}/students")
    suspend fun getStudentsByModule(@Path("moduleId") moduleId: UUID): List<StudentDTO>


    @GET("codes/{userId}")
    suspend fun getCodeByTeacher(@Path("userId") userId: String): CodeDTO

    @GET("students/{userId}/active-sessions")
    suspend fun getActiveSessionOfStudent(@Path("userId") userId: String): List<SessionLazyDTO>

    @GET("sessions/{sessionId}/code")
    suspend fun getStudentCodeBySession(@Path("sessionId") sessionId: UUID): List<CodeDTO>

    @POST("sign")
    suspend fun studentSign(@Body signatureLazyDTO: SignatureLazyDTO): SessionDTO

    @POST("addSession")
    suspend fun addSession(@Body sessionDTO: SessionDTO): List<SessionLazyDTO>

    @GET("openSession/{sessionId}")
    suspend fun openSession(@Path("sessionId") sessionId: UUID): SessionDTO

    @GET("closeSession/{sessionId}")
    suspend fun closeSession(@Path("sessionId") sessionId: UUID): SessionDTO

    @POST("create-student")
    suspend fun createStudent(@Body userCreationDTO: UserCreationDTO): Unit

    @POST("create-teacher")
    suspend fun createTeacher(@Body teacherLazyDTO: TeacherLazyDTO): Unit


    @POST("create-speciality")
    suspend fun createSpeciality(@Body specialityCreationDTO : SpecialityCreationDTO): SpecialityLazyDTO

    @POST("specialities/assign")
    suspend fun addStudentToSpeciality(@Body specialityStudent : Map<String, String>): Unit

//
//    @POST("sign")
//    suspend fun postSignature (@Body signatureDTO: SignatureDTO): List<SignatureDTO>


    @POST("auth/token/verify")
    suspend fun verifyToken(): Boolean

    @GET("sessions")
    suspend fun getSessions(): List<SessionLazyDTO>

}

