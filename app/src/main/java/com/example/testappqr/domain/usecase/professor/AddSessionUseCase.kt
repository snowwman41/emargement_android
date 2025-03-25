package com.example.testappqr.domain.usecase.professor

import com.example.testappqr.models.ModuleDTO
import com.example.testappqr.models.ModuleLazyDTO
import com.example.testappqr.models.SessionDTO
import com.example.testappqr.models.SessionLazyDTO
import com.example.testappqr.domain.repository.ProfessorRepository
import com.example.testappqr.utils.convertToTimestamp
import java.util.UUID
import javax.inject.Inject

class AddSessionUseCase @Inject constructor(private val professorRepository: ProfessorRepository) {
    suspend operator fun invoke(
        moduleId: UUID,
        sessionName: String,
        date: String,
        startTime: String,
        endTime: String
    ) : List<SessionLazyDTO> {
        val startTimestamp = convertToTimestamp(date, startTime)
        val endTimestamp = convertToTimestamp(date, endTime)

        return professorRepository.addSession(
            session = SessionDTO(
                module = ModuleLazyDTO(moduleId = moduleId, null, null),
                sessionName = sessionName,
                startTime = startTimestamp,
                endTime = endTimestamp,
                verificationCode = null,
                active = false
            )
        )
    }
}