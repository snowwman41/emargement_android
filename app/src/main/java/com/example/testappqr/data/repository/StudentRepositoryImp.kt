package com.example.testappqr.data.repository

import com.example.testappqr.data.datasource.remote.ApiService
import com.example.testappqr.models.CodeDTO
import com.example.testappqr.models.SessionLazyDTO
import com.example.testappqr.domain.repository.StudentRepository
import com.example.testappqr.domain.usecase.util.ApiResult
import com.example.testappqr.models.Attributes
import com.example.testappqr.models.AuthenticationSuccess
import com.example.testappqr.models.SSODTO
import com.example.testappqr.models.SessionDTO
import com.example.testappqr.models.SignatureLazyDTO
import retrofit2.HttpException
import java.io.IOException
import java.util.UUID
import javax.inject.Inject

class StudentRepositoryImp @Inject constructor(private val apiService: ApiService) : StudentRepository{


    override suspend fun studentActiveSessions(userId: String): List<SessionLazyDTO> {
        return apiService.getActiveSessionOfStudent(userId)
    }

    override suspend fun studentCodeBySession(sessionId: UUID): List<CodeDTO> {
        return apiService.getStudentCodeBySession(sessionId)
    }

    override suspend fun studentSign(signatureLazyDTO: SignatureLazyDTO): ApiResult<SessionDTO> {

        return try {
            val result = apiService.studentSign(signatureLazyDTO)
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