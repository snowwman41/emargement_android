package com.example.testappqr.domain.repository

import com.example.testappqr.data.models.SessionDTO
import retrofit2.http.Path

interface StudentRepository {
    suspend fun getActiveSessions (userId : String): List<SessionDTO>

}