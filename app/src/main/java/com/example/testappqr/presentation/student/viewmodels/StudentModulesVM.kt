package com.example.testappqr.presentation.student.viewmodels


import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testappqr.domain.usecase.student.StudentModulesUseCase
import com.example.testappqr.domain.usecase.util.handle
import com.example.testappqr.models.ModuleLazyDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import javax.inject.Inject

@HiltViewModel
class StudentModulesVM @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val studentModulesUseCase: StudentModulesUseCase

) : ViewModel() {
    val studentModulesState = savedStateHandle.getStateFlow(
        "studentModulesState",
        StudentModulesState()
    )

    fun getModules(userId: String) {
        viewModelScope.launch {
            studentModulesUseCase(userId).handle(
                onSuccess = { data ->
                    updateState { it.copy(modulesList = data) }
                }
            )
        }
    }

    private fun updateState(update: (StudentModulesState) -> StudentModulesState) {
        savedStateHandle["studentModulesState"] =
            update(studentModulesState.value)
    }
}

@Parcelize
data class StudentModulesState(
    val modulesList: @RawValue List<ModuleLazyDTO> = emptyList()
) : Parcelable