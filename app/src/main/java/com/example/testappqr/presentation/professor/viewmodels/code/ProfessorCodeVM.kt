package com.example.testappqr.presentation.professor.viewmodels.code

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testappqr.data.models.CodeDTO
import com.example.testappqr.domain.usecase.professor.ProfessorGetCodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@HiltViewModel
class ProfessorCodeVM @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getCodeUseCase: ProfessorGetCodeUseCase
) : ViewModel() {

    val codeState = savedStateHandle.getStateFlow("codeState", CodeState())

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
        updateState { it.copy(qrCodeModal = show)}
    }
    fun getCodes(userId : String) {
        viewModelScope.launch {
            val codes = getCodeUseCase(userId)
            updateState { it.copy( codes = codes) }
        }
    }

    private fun updateState(update: (CodeState) -> CodeState) {
        savedStateHandle["codeState"] = update(codeState.value)
    }
}

@Parcelize
data class CodeState(
    val qrCodeModal: Boolean = false,
    val codes : CodeDTO? = null
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