package com.example.testappqr.data.repository

import com.example.testappqr.data.datasource.remote.ApiService
import com.example.testappqr.data.models.ModuleDTO
import com.example.testappqr.data.models.ModuleLazyDTO
import com.example.testappqr.data.models.SSODTO
import com.example.testappqr.data.models.SessionDTO
import com.example.testappqr.data.models.SessionLazyDTO
import com.example.testappqr.data.models.StudentLazyDTO
import com.example.testappqr.domain.repository.ProfessorRepository
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfessorRepositoryImpl @Inject constructor(private val apiService: ApiService) : ProfessorRepository {
    override suspend fun modules(): List<ModuleLazyDTO> {
        return apiService.getModules(
            professorId = "s23022841"
        )
    }
    override suspend fun module(moduleId: UUID): ModuleDTO {
        return apiService.getModule(moduleId)
    }

    override suspend fun addSession(session: SessionLazyDTO): List<SessionLazyDTO> {
        return apiService.addSession(session)
    }

    override suspend fun openSession(sessionId: UUID): SessionLazyDTO {
        return apiService.openSession(sessionId)
    }

    override suspend fun closeSession(sessionId: UUID) {
        return apiService.closeSession(sessionId)
    }



    override suspend fun verifyToken(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getSessions(): List<SessionLazyDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun getSessionsByModule(): List<SessionLazyDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun getSession(): SessionDTO {
        TODO("Not yet implemented")
    }

    override suspend fun getStudentsByModule(moduleId: UUID): List<StudentLazyDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun casValidate(url: String): SSODTO {
        TODO("Not yet implemented")
    }

    override suspend fun createModule(module: ModuleLazyDTO): List<ModuleLazyDTO> {
        TODO("Not yet implemented")
    }


}