package com.example.testappqr.domain.usecase.student



import com.example.testappqr.data.models.CodeDTO
import com.example.testappqr.data.models.SessionLazyDTO
import com.example.testappqr.domain.repository.StudentRepository
import java.util.UUID
import javax.inject.Inject

class StudentCodeBySessionUseCase @Inject constructor(private val studentRepository: StudentRepository){
    suspend operator fun invoke(sessionId : UUID) : List<CodeDTO>{
        return studentRepository.studentCodeBySession(sessionId)
    }
}