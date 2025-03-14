package com.example.testappqr.domain.repository

import com.example.testappqr.data.models.ModuleDTO
import com.example.testappqr.data.models.ModuleLazyDTO
import com.example.testappqr.data.models.SSODTO
import com.example.testappqr.data.models.SessionDTO
import com.example.testappqr.data.models.SessionLazyDTO
import com.example.testappqr.data.models.StudentLazyDTO
import java.util.UUID

interface ProfessorRepository {
    suspend fun modules(): List<ModuleLazyDTO>
    suspend fun module(moduleId: UUID): ModuleDTO
    suspend fun addSession(session: SessionLazyDTO): List<SessionLazyDTO>
    suspend fun openSession(sessionId: UUID): SessionLazyDTO
    suspend fun closeSession(sessionId: UUID)
    //active ?
    suspend fun getSessions(): List<SessionLazyDTO>
    suspend fun getSessionsByModule(): List<SessionLazyDTO>
    suspend fun getSession(): SessionDTO
    suspend fun getStudentsByModule(moduleId: UUID): List<StudentLazyDTO>


    // not yet implemented
    suspend fun verifyToken(): Boolean

    suspend fun casValidate(url: String): SSODTO
    suspend fun createModule(module: ModuleLazyDTO): List<ModuleLazyDTO>


    // Optionally, if needed:
    // suspend fun postSignature(signature: Signature): List<Signature>


}