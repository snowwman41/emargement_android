package com.example.testappqr.network



import com.example.testappqr.models.ApiSSOResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET

import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Url


interface ApiService {

    @POST("auth/token/verify")
    suspend fun verifyToken (): Boolean

    @GET
    suspend fun casValidate (@Url url : String): ApiSSOResponse
}

