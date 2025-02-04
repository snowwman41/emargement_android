package com.example.testappqr.network



import com.example.testappqr.models.ApiSSOResponse
import com.example.testappqr.models.SessionDTO
import com.example.testappqr.models.SignatureDTO
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET

import retrofit2.http.Headers
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

    @POST("sign")
    suspend fun postSession (@Body sessionDTO: SessionDTO): List<SessionDTO>

    @GET
    suspend fun casValidate (@Url url : String): ApiSSOResponse
}

