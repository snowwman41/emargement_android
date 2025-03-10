package com.example.testappqr.presentation.professor.viewmodels

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testappqr.data.models.SessionDTO
import com.example.testappqr.domain.usecase.professor.ProfessorSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import javax.inject.Inject

@HiltViewModel
class ProfessorSessionVM @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val professorSessionUseCase: ProfessorSessionUseCase
) :
    ViewModel() {
    val professorSessionState =
        savedStateHandle.getStateFlow("ProfessorSessionState", ProfessorSessionState())

    fun getSession() {
        viewModelScope.launch {
            val session = professorSessionUseCase()
            updateState { it.copy(session = session) }
        }
    }

    private fun updateState(update: (ProfessorSessionState) -> ProfessorSessionState) {
        savedStateHandle["ProfessorSessionState"] = update(professorSessionState.value)
    }
}

@Parcelize
data class ProfessorSessionState(
    val session: @RawValue SessionDTO? = null
) : Parcelable