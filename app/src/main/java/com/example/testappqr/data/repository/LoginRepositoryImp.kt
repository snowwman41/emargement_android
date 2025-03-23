package com.example.testappqr.data.repository

import com.example.testappqr.data.datasource.remote.ApiService
import com.example.testappqr.data.models.SSODTO
import com.example.testappqr.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImp @Inject constructor(private val apiService : ApiService): LoginRepository {
    override suspend fun getUserData(request : String): SSODTO {
        return apiService.ssoValidatation(request)
    }
}