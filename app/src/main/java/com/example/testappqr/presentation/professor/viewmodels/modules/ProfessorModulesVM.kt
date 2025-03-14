package com.example.testappqr.presentation.professor.viewmodels.modules

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testappqr.data.models.ModuleLazyDTO
import com.example.testappqr.domain.usecase.professor.GetModulesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ProfessorModulesVM @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getModulesUseCase: GetModulesUseCase
) : ViewModel() {
    val professorState = savedStateHandle.getStateFlow("professorState", ProfessorState())

    fun getModules() {
        updateState { it.copy(isLoading = true) }
        viewModelScope.launch {
            val moduleList = getModulesUseCase()
            updateState { it.copy(modulesList = moduleList, isLoading = false) }
        }
    }

    private fun updateState(update: (ProfessorState) -> ProfessorState) {
        savedStateHandle["professorState"] = update(professorState.value)
    }
}

@Parcelize
data class ProfessorState(
    val modulesList: List<ModuleLazyDTO> = emptyList(),
    val isLoading: Boolean = true,
    val moduleId: UUID? = null
) : Parcelable