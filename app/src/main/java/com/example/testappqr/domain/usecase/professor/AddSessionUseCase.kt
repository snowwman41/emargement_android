package com.example.testappqr.domain.usecase.professor

import com.example.testappqr.data.models.SessionDTO
import com.example.testappqr.domain.repository.ProfessorRepository
import java.util.UUID
import javax.inject.Inject

class AddSessionUseCase @Inject constructor(private val professorRepository: ProfessorRepository) {
    suspend operator fun invoke(
        moduleId: UUID,
        sessionName: String,
        date: String,
        startTime: String,
        endTime: String
    ) {
        val timeStampStartTime = 1L
        val timeStampEndTime = 1L
        professorRepository.addSession(
            session = SessionDTO(
                moduleId = moduleId,
                sessionName = sessionName,
                startTime = timeStampStartTime,
                endTime = timeStampEndTime,
                verificationCode = "TODO()",
                active = false,
            )
        )
    }
}