package com.example.testappqr.di

import com.example.testappqr.data.datasource.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
// mock server url postman : https://5a8cfbf2-dbe7-4e98-b7bd-ca4aaf08a889.mock.pstmn.io/
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Singleton
    @Provides
    fun provideNetworkService(): ApiService {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://192.168.112.164:8080/")
            .build()
        return retrofit.create(ApiService::class.java)
    }
}