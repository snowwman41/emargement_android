package com.example.testappqr.presentation.professor.viewmodels.modules

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testappqr.models.StudentDTO
import com.example.testappqr.domain.usecase.professor.ProfessorStudentsByModuleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ProfessorStudentsByModuleVM @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val professorStudentsByModuleUseCase: ProfessorStudentsByModuleUseCase
) : ViewModel() {
    val professorStudentsByModuleState = savedStateHandle.getStateFlow(
        "ProfessorStudentsByModuleState",
        ProfessorStudentsByModuleState()
    )

    fun getStudentsByModule(moduleId: UUID) {
        viewModelScope.launch {
            val students = professorStudentsByModuleUseCase(moduleId)
            updateState { it.copy(studentsList = students) }
        }
    }

    private fun updateState(update: (ProfessorStudentsByModuleState) -> ProfessorStudentsByModuleState) {
        savedStateHandle["ProfessorStudentsByModuleState"] =
            update(professorStudentsByModuleState.value)
    }
}

@Parcelize
data class ProfessorStudentsByModuleState(
    val studentsList: @RawValue List<StudentDTO> = emptyList()
) : Parcelable