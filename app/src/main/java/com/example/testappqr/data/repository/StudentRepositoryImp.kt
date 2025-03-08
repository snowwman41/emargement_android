package com.example.testappqr.data.repository

import com.example.testappqr.data.datasource.remote.ApiService
import com.example.testappqr.data.models.SessionDTO
import com.example.testappqr.domain.repository.StudentRepository
import javax.inject.Inject

class StudentRepositoryImp @Inject constructor(private val apiService: ApiService) : StudentRepository{
    override suspend fun getActiveSessions(userId: String): List<SessionDTO> {
        return apiService.getActiveSessions(userId)
    }
}