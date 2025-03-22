package com.example.testappqr.domain.repository

import com.example.testappqr.data.models.SessionLazyDTO

interface StudentRepository {
    suspend fun studentActiveSessions (userId : String): List<SessionLazyDTO>
}