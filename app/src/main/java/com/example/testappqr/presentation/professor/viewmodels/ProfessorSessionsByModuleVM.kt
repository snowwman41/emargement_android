package com.example.testappqr.presentation.professor.viewmodels

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testappqr.data.models.SessionLazyDTO
import com.example.testappqr.domain.usecase.professor.ProfessorSessionsByModuleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import javax.inject.Inject

@HiltViewModel
class ProfessorSessionsByModuleVM @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val professorSessionsByModuleUseCase: ProfessorSessionsByModuleUseCase
) : ViewModel() {
    val professorSessionsByModuleState = savedStateHandle.getStateFlow(
        "ProfessorSessionsByModuleState",
        ProfessorSessionsByModuleState()
    )

    fun getSessionsByModule() {
        viewModelScope.launch {
            val sessions = professorSessionsByModuleUseCase()
            updateState { it.copy(sessionsList = sessions) }
        }
    }

    private fun updateState(update: (ProfessorSessionsByModuleState) -> ProfessorSessionsByModuleState) {
        savedStateHandle["ProfessorSessionsByModuleState"] =
            update(professorSessionsByModuleState.value)
    }


}

@Parcelize
data class ProfessorSessionsByModuleState(
    val sessionsList: @RawValue List<SessionLazyDTO> = emptyList()
) : Parcelable