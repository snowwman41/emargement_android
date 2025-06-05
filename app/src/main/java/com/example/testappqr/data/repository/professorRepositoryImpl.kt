package com.example.testappqr.data.repository

import com.example.testappqr.data.datasource.remote.ApiService
import com.example.testappqr.domain.repository.ProfessorRepository
import com.example.testappqr.domain.usecase.util.ApiResult
import com.example.testappqr.domain.usecase.util.safeApiCall
import com.example.testappqr.data.models.CodeDTO
import com.example.testappqr.data.models.ModuleDTO
import com.example.testappqr.data.models.ModuleLazyDTO
import com.example.testappqr.data.models.SSODTO
import com.example.testappqr.data.models.SessionDTO
import com.example.testappqr.data.models.SessionLazyDTO
import com.example.testappqr.data.models.StudentDTO
import com.example.testappqr.data.models.TeacherLazyDTO
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfessorRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    ProfessorRepository {
    override suspend fun modules(userId: String): List<ModuleLazyDTO> {
        return apiService.professorModules(
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

        return safeApiCall { apiService.openSession(sessionId) }

    }


    override suspend fun closeSession(sessionId: UUID): ApiResult<SessionDTO> {
        return safeApiCall { apiService.closeSession(sessionId) }
    }

    override suspend fun professorCreateUser(teacherLazyDTO: TeacherLazyDTO): ApiResult<Unit> {
        return safeApiCall { apiService.createTeacher(teacherLazyDTO) }
    }

    override suspend fun verifyToken(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getSessionsOfUserOnDate(
        userId: String,
        date: String
    ): ApiResult<List<SessionLazyDTO>> {

        return safeApiCall { apiService.getSessionsOfUserOnDate(userId, date)}

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