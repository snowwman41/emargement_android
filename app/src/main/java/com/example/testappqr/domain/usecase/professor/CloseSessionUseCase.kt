//package com.example.testappqr.domain.usecase.professor
//
//import com.example.testappqr.domain.repository.ProfessorRepository
//import com.example.testappqr.models.SessionDTO
//import java.util.UUID
//import javax.inject.Inject
//
//class CloseSessionUseCase @Inject constructor(private val professorRepository: ProfessorRepository) {
//    suspend operator fun invoke(sessionId: UUID) :SessionDTO {
//        return professorRepository.closeSession(sessionId)
//    }
//}