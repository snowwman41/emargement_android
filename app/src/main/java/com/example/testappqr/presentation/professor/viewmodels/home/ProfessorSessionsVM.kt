package com.example.testappqr.presentation.professor.viewmodels.home

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testappqr.data.models.SessionLazyDTO
import com.example.testappqr.domain.usecase.professor.ProfessorSessionsOnDateUseCase
import com.example.testappqr.utils.formatTodaysDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import javax.inject.Inject

@HiltViewModel
class ProfessorSessionsVM @Inject constructor(
    private val professorSessionsOnDateUseCase: ProfessorSessionsOnDateUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val professorSessionsState = savedStateHandle.getStateFlow("ProfessorSessionsState", ProfessorSessionsState())

    fun getSessions( userId : String) {
        viewModelScope.launch {
            println("userId : $userId")
            val sessions = professorSessionsOnDateUseCase(userId , formatTodaysDate())
            println(sessions)
            updateState { it.copy(sessionsList = sessions) }
        }
    }

    private fun updateState(update: (ProfessorSessionsState) -> ProfessorSessionsState) {
        savedStateHandle["ProfessorSessionsState"] = update(professorSessionsState.value)
    }

}

@Parcelize
data class ProfessorSessionsState(
    val sessionsList: @RawValue List<SessionLazyDTO> = emptyList()
) : Parcelable