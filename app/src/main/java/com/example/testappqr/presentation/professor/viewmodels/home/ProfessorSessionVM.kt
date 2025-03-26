package com.example.testappqr.presentation.professor.viewmodels.home

import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testappqr.domain.usecase.professor.ProfessorCloseSessionUseCase
import com.example.testappqr.domain.usecase.professor.ProfessorOpenSessionUseCase
import com.example.testappqr.domain.usecase.professor.ProfessorSessionUseCase
import com.example.testappqr.domain.usecase.util.ApiResult
import com.example.testappqr.domain.usecase.util.handle
import com.example.testappqr.models.SessionDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ProfessorSessionVM @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val professorSessionUseCase: ProfessorSessionUseCase,
    private val professorOpenSessionUseCase: ProfessorOpenSessionUseCase,
    private val professorCloseSessionUseCase: ProfessorCloseSessionUseCase
) :
    ViewModel() {
    val professorSessionState =
        savedStateHandle.getStateFlow("ProfessorSessionState", ProfessorSessionState())

    fun getSession() {
        val sessionId: UUID = UUID.fromString(checkNotNull(savedStateHandle["sessionId"]))
        viewModelScope.launch {
            val session = professorSessionUseCase(sessionId = sessionId)
            updateState { it.copy(session = session) }
        }
    }

    fun openSession() {
        val sessionId: UUID = UUID.fromString(checkNotNull(savedStateHandle["sessionId"]))
        viewModelScope.launch {
            when (val result = professorOpenSessionUseCase(sessionId)
            ) {
                is ApiResult.Success -> {
                    Log.e("SESSIONS", result.data.toString())
                    updateState { it.copy(session = result.data) }
                }

                is ApiResult.Error -> {
                    Log.e("ERROR", result.message.toString())
                }

                is ApiResult.Loading -> {
                }
            }
        }

    }

    fun closeSession() {
        val sessionId: UUID = UUID.fromString(checkNotNull(savedStateHandle["sessionId"]))

        viewModelScope.launch {
            professorCloseSessionUseCase(sessionId).handle (
                onSuccess = { data -> updateState { it.copy(session = data) }}
            )
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