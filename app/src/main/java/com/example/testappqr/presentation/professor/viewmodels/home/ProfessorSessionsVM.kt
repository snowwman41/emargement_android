package com.example.testappqr.presentation.professor.viewmodels.home

import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testappqr.models.SessionLazyDTO
import com.example.testappqr.domain.usecase.professor.ProfessorSessionsOnDateUseCase
import com.example.testappqr.domain.usecase.util.ApiResult
import com.example.testappqr.models.SignatureLazyDTO
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
            when (val result = professorSessionsOnDateUseCase(userId , formatTodaysDate())
            ){
                is ApiResult.Success -> {
                    Log.e("SESSIONS",result.data.toString())
                    updateState { it.copy(isLoading = false,sessionsList = result.data) }
                }
                is ApiResult.Error -> {
                    Log.e("ERROR", result.message.toString())
//                    updateState {
//                        it.copy(
//                            isLoading = false,
//                            message = result.message ?: "An error occurred"
//                        )
//                    }
                }
                is ApiResult.Loading -> {
                    updateState { it.copy(isLoading = true) }
                }
            }

        }
    }

    private fun updateState(update: (ProfessorSessionsState) -> ProfessorSessionsState) {
        savedStateHandle["ProfessorSessionsState"] = update(professorSessionsState.value)
    }

}

@Parcelize
data class ProfessorSessionsState(
    val sessionsList: @RawValue List<SessionLazyDTO> = emptyList(),
    val isLoading : Boolean = false
) : Parcelable