package com.example.testappqr.presentation.login.viewmodels

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testappqr.models.SSODTO
import com.example.testappqr.domain.usecase.login.GetUserDataUseCase
import com.example.testappqr.domain.usecase.util.ApiResult
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
            updateState { it.copy(isLoading = true) }
            when (val result = getUserDataUseCase(request)) {
                is ApiResult.Success -> {
                    updateState {
                        it.copy(
                            isLoading = false,
                            userData = result.data,
                        )
                    }
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