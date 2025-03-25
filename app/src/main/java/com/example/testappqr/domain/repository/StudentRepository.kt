package com.example.testappqr.domain.repository

import com.example.testappqr.models.CodeDTO
import com.example.testappqr.models.SessionDTO
import com.example.testappqr.models.SessionLazyDTO
import com.example.testappqr.models.SignatureLazyDTO
import java.util.UUID

interface StudentRepository {
    suspend fun studentActiveSessions (userId : String): List<SessionLazyDTO>
    suspend fun studentCodeBySession (sessionId : UUID): List<CodeDTO>
    suspend fun studentSign (signatureLazyDTO: SignatureLazyDTO ): SessionDTO


}