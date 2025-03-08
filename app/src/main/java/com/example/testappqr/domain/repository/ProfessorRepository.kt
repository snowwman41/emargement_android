package com.example.testappqr.domain.repository

import com.example.testappqr.data.models.ModuleDTO
import com.example.testappqr.data.models.ModuleLazyDTO
import com.example.testappqr.data.models.SSODTO
import com.example.testappqr.data.models.SessionDTO
import java.util.UUID

interface ProfessorRepository {
    suspend fun modules(): List<ModuleLazyDTO>
    suspend fun module(moduleId: UUID): ModuleDTO
    suspend fun addSession(session: SessionDTO): List<SessionDTO>
    suspend fun openSession(sessionId: UUID): SessionDTO
    suspend fun closeSession(sessionId: UUID)

    // not yet implemented
    suspend fun verifyToken(): Boolean
    suspend fun getSessions(): List<SessionDTO>
    suspend fun casValidate(url: String): SSODTO
    suspend fun createModule(module: ModuleLazyDTO): List<ModuleLazyDTO>

    // Optionally, if needed:
    // suspend fun postSignature(signature: Signature): List<Signature>


}