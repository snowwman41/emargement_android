package com.example.testappqr.presentation.professor.viewmodels

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testappqr.data.models.SessionLazyDTO
import com.example.testappqr.domain.usecase.professor.ProfessorSessionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import javax.inject.Inject

@HiltViewModel
class ProfessorSessionsVM @Inject constructor(
    private val professorSessionsUseCase: ProfessorSessionsUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val professorSessionsState = savedStateHandle.getStateFlow("ProfessorSessionsState", ProfessorSessionsState())

    fun getSessions() {
        viewModelScope.launch {
            val sessions = professorSessionsUseCase()

            updateState { it.copy(sessionsList = sessions) }
        }
    }

    private fun updateState(update: (ProfessorSessionsState) -> ProfessorSessionsState) {
        savedStateHandle["ProfessorSessionsState"] = update(professorSessionsState.value)
    }

}

@Parcelize
data class ProfessorSessionsState(
    val sessionsList: @RawValue List<SessionLazyDTO>? = null
) : Parcelable