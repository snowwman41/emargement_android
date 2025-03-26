package com.example.testappqr.domain.usecase.professor

import com.example.testappqr.domain.repository.ProfessorRepository
import com.example.testappqr.models.StudentDTO
import java.util.UUID
import javax.inject.Inject

class ProfessorStudentsByModuleUseCase @Inject constructor(
    private val professorRepository: ProfessorRepository
) {
    suspend operator fun invoke(moduleId: UUID) : List<StudentDTO> {
        return professorRepository.getStudentsByModule(moduleId)

    }
}