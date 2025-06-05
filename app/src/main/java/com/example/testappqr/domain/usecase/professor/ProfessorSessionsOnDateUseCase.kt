package com.example.testappqr.domain.usecase.professor

import com.example.testappqr.data.models.SessionLazyDTO
import com.example.testappqr.domain.repository.ProfessorRepository
import com.example.testappqr.domain.usecase.util.ApiResult
import javax.inject.Inject


class ProfessorSessionsOnDateUseCase @Inject constructor(private val professorRepository: ProfessorRepository) {
    suspend operator fun invoke(userId : String, date: String): ApiResult<List<SessionLazyDTO>> {
        return professorRepository.getSessionsOfUserOnDate(userId, date)
    }
}