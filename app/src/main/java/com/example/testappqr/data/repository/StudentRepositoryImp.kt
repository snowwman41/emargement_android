package com.example.testappqr.data.repository

import com.example.testappqr.data.datasource.remote.ApiService
import com.example.testappqr.data.models.SessionLazyDTO
import com.example.testappqr.domain.repository.StudentRepository
import javax.inject.Inject

class StudentRepositoryImp @Inject constructor(private val apiService: ApiService) : StudentRepository{
    suspend fun getActiveSessions(userId: String): List<SessionLazyDTO> {
        return apiService.getActiveSessions(userId)
    }

    override suspend fun studentActiveSessions(userId: String): List<SessionLazyDTO> {
        TODO("Not yet implemented")
    }
}