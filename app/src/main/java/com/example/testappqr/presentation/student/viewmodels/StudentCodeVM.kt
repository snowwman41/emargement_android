package com.example.testappqr.presentation.student.viewmodels


import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testappqr.domain.usecase.student.StudentCodeBySessionUseCase
import com.example.testappqr.domain.usecase.student.StudentSignUseCase
import com.example.testappqr.domain.usecase.util.handle
import com.example.testappqr.data.models.CodeDTO
import com.example.testappqr.data.models.CodeType
import com.example.testappqr.data.models.SignatureLazyDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import java.io.IOException
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class StudentCodeVM @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val studentCodeBySessionUseCase: StudentCodeBySessionUseCase,
    private val studentSignUseCase: StudentSignUseCase
) : ViewModel() {
    val studentCodeState = savedStateHandle.getStateFlow(
        "studentCodeState",
        StudentCodeState()
    )

    fun getCodes(sessionId: UUID) {
        viewModelScope.launch {
            val codes = studentCodeBySessionUseCase(sessionId)
            Log.e("SESSION_ID", codes.toString())
            Log.e("CODES", codes.toString())
            updateState { it.copy(codes = codes) }
            Log.e("CODES state", studentCodeState.value.codes.toString())

        }
    }

    fun sign(sessionId: UUID, verificationCode: String, codeType: CodeType, studentId: String) {
        viewModelScope.launch {
            studentSignUseCase(
                SignatureLazyDTO(
                    studentId = studentId,
                    sessionId = sessionId,
                    verificationCode = verificationCode,
                    codeType = codeType
                )
            ).handle(
                onSuccess = { data ->
                    updateState {
                        it.copy(
                            message = "you have successfully signed",
                            isLoading = false,
                        )
                    }
                },
                onLoading = {
                    updateState { it.copy(isLoading = true) }
                },
                onError = { exception, message ->
                    updateState {
                        it.copy(
                            isLoading = false,
                            message = "Error : You may have already signed to the session"
                        )
                    }
                }
            )
        }
    }

    fun clearErrorMessage() {
        updateState {
            it.copy(
                message = null
            )
        }
    }

    fun updateTextFieldCode(code: String) {
        updateState {
            it.copy(
                textFieldCode = code
            )
        }
    }

    private fun updateState(update: (StudentCodeState) -> StudentCodeState) {
        savedStateHandle["studentCodeState"] =
            update(studentCodeState.value)
    }
}

@Parcelize
data class StudentCodeState(
    val codes: List<CodeDTO> = emptyList(),
    val isLoading: Boolean = false,
    val message: String? = null,
    val textFieldCode: String = ""
) : Parcelable