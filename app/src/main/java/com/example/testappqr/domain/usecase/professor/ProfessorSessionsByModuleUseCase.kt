package com.example.testappqr.domain.usecase.professor

import com.example.testappqr.data.models.SessionLazyDTO
import com.example.testappqr.domain.repository.ProfessorRepository
import javax.inject.Inject


class ProfessorSessionsByModuleUseCase @Inject constructor(private val professorRepository: ProfessorRepository) {
    suspend operator fun invoke() : List<SessionLazyDTO> {
        return professorRepository.getSessionsByModule()
    }
}