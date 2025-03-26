package com.example.testappqr.presentation.login.viewmodels

import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testappqr.domain.usecase.login.GetUserDataUseCase
import com.example.testappqr.domain.usecase.student.StudentCreateSpecialityUseCase
import com.example.testappqr.domain.usecase.student.StudentCreateUserUseCase
import com.example.testappqr.domain.usecase.util.handle
import com.example.testappqr.models.SSODTO
import com.example.testappqr.models.SpecialityLazyDTO
import com.example.testappqr.models.UserCreationDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import javax.inject.Inject

@HiltViewModel
class LoginVM @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getUserDataUseCase: GetUserDataUseCase,
    private val studentCreateSpecialityUseCase: StudentCreateSpecialityUseCase,
    private val studentCreateUserUseCase: StudentCreateUserUseCase
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
            getUserDataUseCase(request).handle(
                onSuccess = { userData ->
                    updateState {
                        it.copy(
                            isLoading = false,
                            userData = userData,
                        )
                    }
                    if (userData.authenticationSuccess.attributes.eduPersonPrimaryAffiliation == "student"){

                        studentCreateSpecialityUseCase(userData.authenticationSuccess.attributes.coGroup).handle(
                            onSuccess = { specialityData ->
                                studentCreateUserUseCase(
                                    userCreationDTO = userData.authenticationSuccess.attributes.let{
                                        UserCreationDTO(it.uid, it.sn, it.givenName, it.mail)
                                    }
                                )
                                        },
                            onLoading = {},
                            onError = { exception: Exception, s: String -> }
                        )
                    }
                },
                onLoading = {
                    updateState { it.copy(isLoading = true) }
                },
                onError = { exception: Exception, s: String ->
                    updateState { it.copy(isLoading = false) }
                }
            )
        }
    }

//    private fun addSpeciality(specialityName: String) : SpecialityLazyDTO? {
//        viewModelScope.launch {
//
//        }
//        return null
//    }
//        updateState {
//            it.copy(
//                userData = it.userData?.copy(
//                    specialities = it.userData.specialities.plus(speciality)
//                )
//            )
//        }



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