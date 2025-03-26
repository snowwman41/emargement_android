package com.example.testappqr.presentation.login.viewmodels

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testappqr.domain.usecase.login.GetUserDataUseCase
import com.example.testappqr.domain.usecase.student.StudentCreateSpecialityUseCase
import com.example.testappqr.domain.usecase.util.ApiResult
import com.example.testappqr.models.SSODTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import javax.inject.Inject

@HiltViewModel
class LoginVM @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getUserDataUseCase: GetUserDataUseCase,
    private val studentCreateSpecialityUseCase: StudentCreateSpecialityUseCase
) : ViewModel() {

    val loginState = savedStateHandle.getStateFlow("loginState", LoginState())

    fun updateIsLoading(isLoading: Boolean) {
        updateState { it.copy(isLoading = isLoading) }
    }

    fun updateWebViewError(webViewError: Boolean) {
        updateState { it.copy(webViewError = webViewError) }
    }

    fun updateShouldNavigate(shouldNavigate: Boolean) {
        updateState { it.copy(shouldNavigate = shouldNavigate) }
    }

    fun getUserData(request: String) {
        viewModelScope.launch {
            updateState { it.copy(isLoading = true) }
            when (val result = getUserDataUseCase(request)) {
                is ApiResult.Success -> {
                    updateState {
                        it.copy(
                            isLoading = false,
                            userData = result.data,
                        )
                    }
                    if (result.data.authenticationSuccess.attributes.eduPersonPrimaryAffiliation == "student")
                        addSpeciality(result.data.authenticationSuccess.attributes.coGroup)
                }

                is ApiResult.Error -> {
                    updateState {
                        it.copy(
                            isLoading = false,
                        )
                    }
                }

                is ApiResult.Loading -> {
                    updateState { it.copy(isLoading = true) }
                }
            }
        }
    }

    fun addSpeciality(specialityName: String) {
        viewModelScope.launch {
            when (val result = studentCreateSpecialityUseCase(specialityName)) {
                is ApiResult.Success -> {

                }

                is ApiResult.Error -> {

                }

                is ApiResult.Loading -> {
                }
            }
        }
//        updateState {
//            it.copy(
//                userData = it.userData?.copy(
//                    specialities = it.userData.specialities.plus(speciality)
//                )
//            )
//        }
    }


    fun updateUserData(userData: SSODTO?) {
        updateState { it.copy(userData = userData) }
    }

    private fun updateState(update: (LoginState) -> LoginState) {
        savedStateHandle["loginState"] = update(loginState.value)
    }

}

@Parcelize
data class LoginState(
    val isLoading: Boolean = true,
    val webViewError: Boolean = false,
    val shouldNavigate: Boolean = false,
    val userData: @RawValue SSODTO? = null
) : Parcelable