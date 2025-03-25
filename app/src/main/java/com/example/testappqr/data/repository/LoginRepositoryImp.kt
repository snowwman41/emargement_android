package com.example.testappqr.data.repository

import com.example.testappqr.data.datasource.remote.ApiService
import com.example.testappqr.models.SSODTO
import com.example.testappqr.domain.repository.LoginRepository
import com.example.testappqr.domain.usecase.util.ApiResult
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LoginRepositoryImp @Inject constructor(private val apiService : ApiService): LoginRepository {
    override suspend fun getUserData(request : String): ApiResult<SSODTO> {

        return try {
            val result = apiService.ssoValidatation(request)
            ApiResult.Success(result)
        } catch (e: IOException) {
            ApiResult.Error(e, "Network error occurred")
        } catch (e: HttpException) {
            ApiResult.Error(e, "HTTP error: ${e.code()}")
        } catch (e: Exception) {
            ApiResult.Error(e, e.message ?: "Unknown error occurred")
        }
    }
}