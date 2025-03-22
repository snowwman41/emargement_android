package com.example.testappqr.presentation.professor.viewmodels.modules

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
import java.util.UUID
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

    fun getSessionsByModule(moduleId : UUID) {
        viewModelScope.launch {
            val sessions = professorSessionsByModuleUseCase()
            updateState { it.copy(sessionsList = sessions) }
        }
    }
    fun showAddSession(showAddSession: Boolean){
        updateState { it.copy(showAddSession = showAddSession) }

    }
    private fun updateState(update: (ProfessorSessionsByModuleState) -> ProfessorSessionsByModuleState) {
        savedStateHandle["ProfessorSessionsByModuleState"] =
            update(professorSessionsByModuleState.value)
    }
}

@Parcelize
data class ProfessorSessionsByModuleState(
    val sessionsList: @RawValue List<SessionLazyDTO> = emptyList(),
    val showAddSession : Boolean = false
) : Parcelable