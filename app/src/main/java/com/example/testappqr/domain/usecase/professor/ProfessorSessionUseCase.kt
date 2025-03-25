package com.example.testappqr.domain.usecase.professor

import com.example.testappqr.models.SessionDTO
import com.example.testappqr.models.SessionLazyDTO
import com.example.testappqr.domain.repository.ProfessorRepository
import java.util.UUID
import javax.inject.Inject


class ProfessorSessionUseCase @Inject constructor(private val professorRepository: ProfessorRepository) {
    suspend operator fun invoke(sessionId : UUID): SessionDTO {
        return professorRepository.getSession(sessionId)
    }
}