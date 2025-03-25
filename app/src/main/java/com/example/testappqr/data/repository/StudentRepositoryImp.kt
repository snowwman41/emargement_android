package com.example.testappqr.data.repository

import com.example.testappqr.data.datasource.remote.ApiService
import com.example.testappqr.models.CodeDTO
import com.example.testappqr.models.SessionLazyDTO
import com.example.testappqr.domain.repository.StudentRepository
import com.example.testappqr.models.SessionDTO
import com.example.testappqr.models.SignatureLazyDTO
import java.util.UUID
import javax.inject.Inject

class StudentRepositoryImp @Inject constructor(private val apiService: ApiService) : StudentRepository{


    override suspend fun studentActiveSessions(userId: String): List<SessionLazyDTO> {
        return apiService.getActiveSessionOfStudent(userId)
    }

    override suspend fun studentCodeBySession(sessionId: UUID): List<CodeDTO> {
        return apiService.getStudentCodeBySession(sessionId)
    }

    override suspend fun studentSign(signatureLazyDTO: SignatureLazyDTO): SessionDTO {
        return apiService.studentSign(signatureLazyDTO)
    }
}