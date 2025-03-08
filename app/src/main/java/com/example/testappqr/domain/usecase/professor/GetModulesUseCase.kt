package com.example.testappqr.domain.usecase.professor

import com.example.testappqr.data.models.ModuleLazyDTO
import com.example.testappqr.domain.repository.ProfessorRepository
import javax.inject.Inject

class GetModulesUseCase @Inject constructor(private val professorRepository: ProfessorRepository) {
    suspend operator fun invoke(): List<ModuleLazyDTO> {
        return professorRepository.modules()
    }
}