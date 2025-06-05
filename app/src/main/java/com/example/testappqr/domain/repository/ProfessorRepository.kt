package com.example.testappqr.domain.repository

import com.example.testappqr.domain.usecase.util.ApiResult
import com.example.testappqr.data.models.CodeDTO
import com.example.testappqr.data.models.ModuleDTO
import com.example.testappqr.data.models.ModuleLazyDTO
import com.example.testappqr.data.models.SSODTO
import com.example.testappqr.data.models.SessionDTO
import com.example.testappqr.data.models.SessionLazyDTO
import com.example.testappqr.data.models.StudentDTO
import com.example.testappqr.data.models.TeacherLazyDTO
import java.util.UUID

interface ProfessorRepository {
    //working
    suspend fun getSessionsOfUserOnDate(
        userId: String,
        date: String
    ): ApiResult<List<SessionLazyDTO>>

    suspend fun getSession(sessionId: UUID): SessionDTO
    suspend fun modules(userId: String): List<ModuleLazyDTO>
    suspend fun module(moduleId: UUID): ModuleDTO
    suspend fun getCodeByTeacher(userId: String): CodeDTO
    suspend fun openSession(sessionId: UUID): ApiResult<SessionDTO>
    suspend fun closeSession(sessionId: UUID): ApiResult<SessionDTO>
    suspend fun professorCreateUser(teacherLazyDTO: TeacherLazyDTO) : ApiResult<Unit>


    suspend fun addSession(session: SessionDTO): List<SessionLazyDTO>

    //active ?
//    suspend fun getSessionsByModule(moduleId : UUID): List<SessionLazyDTO>
    suspend fun getStudentsByModule(moduleId: UUID): List<StudentDTO>


    // not yet implemented
    suspend fun verifyToken(): Boolean

    suspend fun casValidate(url: String): SSODTO
    suspend fun createModule(module: ModuleLazyDTO): List<ModuleLazyDTO>


    // Optionally, if needed:
    // suspend fun postSignature(signature: Signature): List<Signature>


}