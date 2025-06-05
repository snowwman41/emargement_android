package com.example.testappqr.domain.usecase.professor

import com.example.testappqr.data.models.CodeDTO
import com.example.testappqr.data.models.ModuleLazyDTO
import com.example.testappqr.domain.repository.ProfessorRepository
import javax.inject.Inject


class ProfessorGetCodeUseCase @Inject constructor(private val professorRepository: ProfessorRepository) {
    suspend operator fun invoke(userId : String): CodeDTO {
        return professorRepository.getCodeByTeacher(userId)
    }
}