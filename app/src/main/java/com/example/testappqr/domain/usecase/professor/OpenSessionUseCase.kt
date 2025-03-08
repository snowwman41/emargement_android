package com.example.testappqr.domain.usecase.professor

import com.example.testappqr.domain.repository.ProfessorRepository
import java.util.UUID
import javax.inject.Inject

class OpenSessionUseCase @Inject constructor(private val professorRepository: ProfessorRepository) {
    suspend operator fun invoke(sessionId: UUID) {
        professorRepository.openSession(sessionId)
    }
}