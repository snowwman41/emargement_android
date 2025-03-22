package com.example.testappqr.presentation.student.viewmodels


import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testappqr.data.models.SessionLazyDTO
import com.example.testappqr.domain.usecase.professor.ProfessorSessionsByModuleUseCase
import com.example.testappqr.domain.usecase.student.StudentSessionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import javax.inject.Inject

@HiltViewModel
class StudentSessionsVM @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val studentSessionsUseCase: StudentSessionsUseCase
) : ViewModel() {
    val studentSessionsState = savedStateHandle.getStateFlow(
        "studentSessionsState",
        StudentSessionsState()
    )

    fun getSessions() {
        viewModelScope.launch {
            val sessions = studentSessionsUseCase("")
            updateState { it.copy(sessionsList = sessions) }
        }
    }

    private fun updateState(update: (StudentSessionsState) -> StudentSessionsState) {
        savedStateHandle["studentSessionsState"] =
            update(studentSessionsState.value)
    }
}

@Parcelize
data class StudentSessionsState(
    val sessionsList: @RawValue List<SessionLazyDTO> = emptyList()
) : Parcelable