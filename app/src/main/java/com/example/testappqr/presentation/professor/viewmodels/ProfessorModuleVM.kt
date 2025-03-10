package com.example.testappqr.presentation.professor.viewmodels

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testappqr.data.models.ModuleDTO
import com.example.testappqr.domain.usecase.professor.CloseSessionUseCase
import com.example.testappqr.domain.usecase.professor.GetModuleUseCase
import com.example.testappqr.domain.usecase.professor.OpenSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ModuleViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getModuleUserCase: GetModuleUseCase,
    private val openSessionUseCase: OpenSessionUseCase,
    private val closeSessionUseCase: CloseSessionUseCase

) : ViewModel() {

    val moduleState = savedStateHandle.getStateFlow("moduleState", ModuleState())

    fun toggleQRCodeModal(show: Boolean, sessionId : UUID? = null) {

        if (show){
            updateState { it.copy(sessionId = sessionId) }
            viewModelScope.launch {
                openSessionUseCase(
                    sessionId = sessionId!!
                )
            }
        }else{
            viewModelScope.launch {
                closeSessionUseCase(
                    sessionId = moduleState.value.sessionId!!
                )
            }
        }

        val qrCode = ((Math.random() * 1000).toInt() + 1).toString()
        updateState { it.copy(qrCodeModal = show, qrCode = qrCode) }
    }

    fun toggleAddSessionModal(show: Boolean) {
        updateState { it.copy(sessionModal = show) }
    }

    fun getModule() {
        val moduleId = checkNotNull(savedStateHandle.get<String>("moduleId"))
        viewModelScope.launch {
            val module = getModuleUserCase(UUID.fromString(moduleId))
            updateState { it.copy(module = module) }
        }
    }

    private fun updateState(update: (ModuleState) -> ModuleState) {
        savedStateHandle["moduleState"] = update(moduleState.value)
    }
}

@Parcelize
data class ModuleState(
    val module: @RawValue ModuleDTO? = null,
    val qrCodeModal: Boolean = false,
    val sessionModal: Boolean = false,
    val qrCode: String = "",
    val sessionId: UUID? = null
) : Parcelable
