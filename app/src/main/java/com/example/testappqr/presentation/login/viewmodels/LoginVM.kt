package com.example.testappqr.presentation.login.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testappqr.data.models.SSODTO
import com.example.testappqr.domain.usecase.Login.GetUserDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class LoginVM @Inject constructor(private val savedStateHandle: SavedStateHandle,private val getUserDataUseCase : GetUserDataUseCase): ViewModel() {

    val loginState = savedStateHandle.getStateFlow("loginState",LoginState())

    fun updateIsLoading(isLoading: Boolean){
        updateState { it.copy(isLoading = isLoading) }
    }
    fun updateWebViewError(webViewError: Boolean){
        updateState { it.copy(webViewError = webViewError) }
    }
    fun updateShouldNavigate(shouldNavigate: Boolean){
        updateState { it.copy(shouldNavigate = shouldNavigate) }
    }
    fun getUserData(request : String): SSODTO? {
        viewModelScope.launch{
            getUserDataUseCase(request)
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
    private fun updateUserData(userData: SSODTO){
        updateState { it.copy(userData = userData) }
    }
    private fun updateState(update : (LoginState) -> LoginState){
        savedStateHandle["loginState"] = update(loginState.value)
    }

}
data class LoginState(
    val isLoading: Boolean = false,
    val webViewError: Boolean = false,
    val shouldNavigate: Boolean = false,
    val userData: SSODTO? = null
)