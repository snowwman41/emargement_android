package com.example.testappqr.domain.usecase.professor

import com.example.testappqr.models.SessionLazyDTO
import com.example.testappqr.domain.repository.ProfessorRepository
import javax.inject.Inject


class ProfessorSessionsOnDateUseCase @Inject constructor(private val professorRepository: ProfessorRepository) {
    suspend operator fun invoke(userId : String, date: String): List<SessionLazyDTO> {
        return professorRepository.getSessionsOfUserOnDate(userId, date)
    }
}