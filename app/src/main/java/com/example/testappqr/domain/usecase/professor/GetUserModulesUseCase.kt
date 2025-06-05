package com.example.testappqr.domain.usecase.professor

import com.example.testappqr.data.models.ModuleLazyDTO
import com.example.testappqr.domain.repository.ProfessorRepository
import javax.inject.Inject

class GetUserModulesUseCase @Inject constructor(private val professorRepository: ProfessorRepository) {
    suspend operator fun invoke(userId : String): List<ModuleLazyDTO> {
        return professorRepository.modules(userId)
    }
}