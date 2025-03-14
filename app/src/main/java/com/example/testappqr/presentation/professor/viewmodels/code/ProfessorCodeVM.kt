package com.example.testappqr.presentation.professor.viewmodels.code

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@HiltViewModel
class ProfessorCodeVM @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val moduleState = savedStateHandle.getStateFlow("moduleState", ModuleState())

    fun toggleQRCodeModal(show: Boolean) {
        if (show) {
            viewModelScope.launch {
                println("open")
            }
        } else {
            viewModelScope.launch {
                println("close")
            }
        }
        val qrCode = ((Math.random() * 1000).toInt() + 1).toString()
        updateState { it.copy(qrCodeModal = show, qrCode = qrCode) }
    }

    private fun updateState(update: (ModuleState) -> ModuleState) {
        savedStateHandle["moduleState"] = update(moduleState.value)
    }
}

@Parcelize
data class ModuleState(
    val qrCodeModal: Boolean = false,
    val qrCode: String = "",
) : Parcelable


//                openSessionUseCase(
//                    sessionId = sessionId!!
//                )
//                closeSessionUseCase(
//                    sessionId = moduleState.value.sessionId!!
//                )
//
//@Parcelize
//data class ModuleState(
////    val module: @RawValue ModuleDTO? = null,
//    val qrCodeModal: Boolean = false,
////    val sessionModal: Boolean = false,
//    val qrCode: String = "",
////    val sessionId: UUID? = null
//) : Parcelable


//    fun toggleAddSessionModal(show: Boolean) {
//        updateState { it.copy(sessionModal = show) }
//    }

//    fun getModule() {
//        val moduleId = checkNotNull(savedStateHandle.get<String>("moduleId"))
//        viewModelScope.launch {
//            val module = getModuleUserCase(UUID.fromString(moduleId))
//            updateState { it.copy(module = module) }
//        }
//    }