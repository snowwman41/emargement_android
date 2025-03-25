package com.example.testappqr.domain.usecase.professor

import com.example.testappqr.models.CodeDTO
import com.example.testappqr.models.ModuleLazyDTO
import com.example.testappqr.domain.repository.ProfessorRepository
import javax.inject.Inject


class ProfessorGetCodeUseCase @Inject constructor(private val professorRepository: ProfessorRepository) {
    suspend operator fun invoke(userId : String): CodeDTO {
        return professorRepository.getCodeByTeacher(userId)
    }
}