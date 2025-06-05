package com.example.testappqr.domain.repository

import com.example.testappqr.domain.usecase.util.ApiResult
import com.example.testappqr.data.models.SSODTO

interface LoginRepository {
    suspend fun getUserData(request : String): ApiResult<SSODTO>
}