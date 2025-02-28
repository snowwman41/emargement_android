package com.example.testappqr.core.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitApi {
    private const val BASE_URL = "https://5a8cfbf2-dbe7-4e98-b7bd-ca4aaf08a889.mock.pstmn.io/"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}