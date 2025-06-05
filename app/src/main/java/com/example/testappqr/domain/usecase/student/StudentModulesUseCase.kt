package com.example.testappqr.domain.usecase.student


import com.example.testappqr.domain.repository.ProfessorRepository
import com.example.testappqr.domain.repository.StudentRepository
import com.example.testappqr.domain.usecase.util.ApiResult
import com.example.testappqr.data.models.ModuleLazyDTO
import com.example.testappqr.data.models.SessionDTO
import com.example.testappqr.data.models.UserCreationDTO
import java.util.UUID
import javax.inject.Inject

class StudentModulesUseCase @Inject constructor(private val studentRepository: StudentRepository) {
    suspend operator fun invoke(studentId : String) : ApiResult<List<ModuleLazyDTO>>{
        return studentRepository.studentModules(studentId)
    }
}