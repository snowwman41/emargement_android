package com.example.testappqr.domain.usecase.professor

import com.example.testappqr.domain.repository.ProfessorRepository
import com.example.testappqr.domain.usecase.util.ApiResult
import com.example.testappqr.models.SessionDTO
import com.example.testappqr.models.TeacherLazyDTO
import java.util.UUID
import javax.inject.Inject

class ProfessorCreateUserUseCase @Inject constructor(private val professorRepository: ProfessorRepository) {
    suspend operator fun invoke(teacherLazyDTO: TeacherLazyDTO) : ApiResult<Unit>{
        return professorRepository.professorCreateUser(teacherLazyDTO)
    }
}