package com.example.testappqr.presentation.login.viewmodels

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testappqr.data.models.SSODTO
import com.example.testappqr.domain.usecase.login.GetUserDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class LoginVM @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getUserDataUseCase: GetUserDataUseCase
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
            val userData = getUserDataUseCase(request)
            println("userdata loginVM  :   $userData")
            updateUserData(userData)
        }
    }

    //    private fun handleValidationRequest(url: String): SSODTO? {
//        return runBlocking {
//            try {
//                val response = RetrofitApi.api.casValidate(url)
//                response
//            } catch (e: Exception) {
//                null
//            }
//        }
//    }
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