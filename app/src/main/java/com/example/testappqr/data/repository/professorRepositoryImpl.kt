package com.example.testappqr.data.repository

import com.example.testappqr.data.datasource.remote.ApiService
import com.example.testappqr.domain.repository.ProfessorRepository
import com.example.testappqr.domain.usecase.util.ApiResult
import com.example.testappqr.models.CodeDTO
import com.example.testappqr.models.ModuleDTO
import com.example.testappqr.models.ModuleLazyDTO
import com.example.testappqr.models.SSODTO
import com.example.testappqr.models.SessionDTO
import com.example.testappqr.models.SessionLazyDTO
import com.example.testappqr.models.StudentDTO
import retrofit2.HttpException
import java.io.IOException
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfessorRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    ProfessorRepository {
    override suspend fun modules(userId: String): List<ModuleLazyDTO> {
        return apiService.getModules(
            userId = "s23022841"
        )
    }

    override suspend fun module(moduleId: UUID): ModuleDTO {
        return apiService.getModule(moduleId)
    }

    override suspend fun getCodeByTeacher(userId: String): CodeDTO {
        return apiService.getCodeByTeacher(userId)
    }

    override suspend fun addSession(session: SessionDTO): List<SessionLazyDTO> {
        return apiService.addSession(session)
    }

    override suspend fun openSession(sessionId: UUID): ApiResult<SessionDTO> {
        return try {
            val result = apiService.openSession(sessionId)
            ApiResult.Success(result)
        } catch (e: IOException) {
            ApiResult.Error(e, "Network error occurred")
        } catch (e: HttpException) {
            ApiResult.Error(e, "HTTP error: ${e.code()}")
        } catch (e: Exception) {
            ApiResult.Error(e, e.message ?: "Unknown error occurred")
        }
    }


    override suspend fun closeSession(sessionId: UUID): ApiResult<SessionDTO> {
        return try {
            val result = apiService.closeSession(sessionId)
            ApiResult.Success(result)
        } catch (e: IOException) {
            ApiResult.Error(e, "Network error occurred")
        } catch (e: HttpException) {
            ApiResult.Error(e, "HTTP error: ${e.code()}")
        } catch (e: Exception) {
            ApiResult.Error(e, e.message ?: "Unknown error occurred")
        }
    }

override suspend fun verifyToken(): Boolean {
    TODO("Not yet implemented")
}

override suspend fun getSessionsOfUserOnDate(
    userId: String,
    date: String
): ApiResult<List<SessionLazyDTO>> {
    return try {
        val result = apiService.getSessionsOfUserOnDate(userId, date)
        ApiResult.Success(result)
    } catch (e: IOException) {
        ApiResult.Error(e, "Network error occurred")
    } catch (e: HttpException) {
        ApiResult.Error(e, "HTTP error: ${e.code()}")
    } catch (e: Exception) {
        ApiResult.Error(e, e.message ?: "Unknown error occurred")
    }
}


override suspend fun getSession(sessionId: UUID): SessionDTO {
    return apiService.getSession(sessionId)
}

override suspend fun getStudentsByModule(moduleId: UUID): List<StudentDTO> {
    return apiService.getStudentsByModule(moduleId)
}

override suspend fun casValidate(url: String): SSODTO {
    TODO("Not yet implemented")
}

override suspend fun createModule(module: ModuleLazyDTO): List<ModuleLazyDTO> {
    TODO("Not yet implemented")
}


}