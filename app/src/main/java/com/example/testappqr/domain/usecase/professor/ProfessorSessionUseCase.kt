package com.example.testappqr.domain.usecase.professor

import com.example.testappqr.data.models.SessionDTO
import com.example.testappqr.data.models.SessionLazyDTO
import com.example.testappqr.domain.repository.ProfessorRepository
import javax.inject.Inject


class ProfessorSessionUseCase @Inject constructor(private val professorRepository: ProfessorRepository) {
    suspend operator fun invoke(): SessionDTO {
        return professorRepository.getSession()
    }
}