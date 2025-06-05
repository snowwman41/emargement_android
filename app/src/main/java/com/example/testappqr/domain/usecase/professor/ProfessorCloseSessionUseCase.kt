package com.example.testappqr.domain.usecase.professor

import com.example.testappqr.domain.repository.ProfessorRepository
import com.example.testappqr.domain.usecase.util.ApiResult
import com.example.testappqr.data.models.SessionDTO
import java.util.UUID
import javax.inject.Inject

class ProfessorCloseSessionUseCase @Inject constructor(private val professorRepository: ProfessorRepository) {
    suspend operator fun invoke(sessionId: UUID) : ApiResult<SessionDTO>{
        return professorRepository.closeSession(sessionId)
    }
}