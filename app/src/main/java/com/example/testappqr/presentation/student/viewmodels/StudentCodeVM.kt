package com.example.testappqr.presentation.student.viewmodels


import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testappqr.domain.usecase.student.StudentCodeBySessionUseCase
import com.example.testappqr.domain.usecase.student.StudentSignUseCase
import com.example.testappqr.domain.usecase.util.ApiResult
import com.example.testappqr.models.CodeDTO
import com.example.testappqr.models.CodeType
import com.example.testappqr.models.SignatureLazyDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
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
            when (val result = studentSignUseCase(
                SignatureLazyDTO(
                    studentId = studentId,
                    sessionId = sessionId,
                    verificationCode = verificationCode,
                    codeType = codeType
                )
            )) {
                is ApiResult.Success -> {
                    updateState {
                        it.copy(
                            message = "you have successfully signed",
                            isLoading = false,
                        )
                    }
                }
                is ApiResult.Error -> {
                    Log.e("ERROR", result.message.toString())
                    updateState {
                        it.copy(
                            isLoading = false,
                            message = result.message ?: "An error occurred"
                        )
                    }
                }
                is ApiResult.Loading -> {
                    updateState { it.copy(isLoading = true) }
                }
            }

        }
    }
    fun clearErrorMessage(){
        updateState {
            it.copy(
                message = null
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
    val isLoading : Boolean = false,
    val message : String? = null
) : Parcelable