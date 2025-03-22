package com.example.testappqr.presentation.professor.viewmodels

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testappqr.domain.usecase.professor.AddSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddSessionVM @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val addSessionUseCase: AddSessionUseCase
) :
    ViewModel() {
    val addSessionState = savedStateHandle.getStateFlow("addSessionState", AddSessionState())

    fun onChangeSessionName(value: String) {
        updateState { it.copy(sessionName = value) }
    }

    fun onChangeDate(value: String) {
        updateState { it.copy(date = value) }
    }

    fun onChangeStartTime(value: String) {
        updateState { it.copy(startTime = value) }
    }

    fun onChangeEndTime(value: String) {
        updateState { it.copy(endTime = value) }
    }

    fun addSession() {
        val moduleId = UUID.fromString(savedStateHandle.get<String>("moduleId"))

        viewModelScope.launch {
            addSessionUseCase(
                moduleId = moduleId,
                sessionName = addSessionState.value.sessionName,
                date = addSessionState.value.date,
                startTime = addSessionState.value.startTime,
                endTime = addSessionState.value.endTime
            )
        }
        updateState { it.copy(endTime = "") }
    }

    private fun updateState(update: (AddSessionState) -> AddSessionState) {
        savedStateHandle["addSessionState"] = update(addSessionState.value)
    }
}

@Parcelize
data class AddSessionState(
    val sessionName: String = "",
    val date: String = LocalDate.now().toString(),
    val startTime: String = "00:00",
    val endTime: String = "00:00"
) : Parcelable