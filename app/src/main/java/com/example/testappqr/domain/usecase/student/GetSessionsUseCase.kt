package com.example.testappqr.domain.usecase.student


import com.example.testappqr.domain.repository.StudentRepository
import javax.inject.Inject

class GetSessionsUseCase @Inject constructor(private val studentRepository: StudentRepository){
    suspend operator fun invoke(userId : String) {
        studentRepository.getActiveSessions(userId)
    }
}