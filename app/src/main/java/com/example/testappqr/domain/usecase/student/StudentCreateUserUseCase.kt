package com.example.testappqr.domain.usecase.student

import com.example.testappqr.domain.repository.ProfessorRepository
import com.example.testappqr.domain.repository.StudentRepository
import com.example.testappqr.domain.usecase.util.ApiResult
import com.example.testappqr.data.models.SessionDTO
import com.example.testappqr.data.models.UserCreationDTO
import java.util.UUID
import javax.inject.Inject

class StudentCreateUserUseCase @Inject constructor(private val studentRepository: StudentRepository) {
    suspend operator fun invoke(userCreationDTO: UserCreationDTO) : ApiResult<Unit>{
        return studentRepository.studentCreateUser(userCreationDTO)
    }
}