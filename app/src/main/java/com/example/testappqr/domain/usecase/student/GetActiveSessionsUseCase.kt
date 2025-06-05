package com.example.testappqr.domain.usecase.student


import com.example.testappqr.data.models.SessionLazyDTO
import com.example.testappqr.domain.repository.StudentRepository
import javax.inject.Inject

class StudentActiveSessionsUseCase @Inject constructor(private val studentRepository: StudentRepository){
    suspend operator fun invoke(userId : String) : List<SessionLazyDTO> {
        return studentRepository.studentActiveSessions(userId)
    }
}