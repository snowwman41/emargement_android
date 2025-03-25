package com.example.testappqr.domain.usecase.login

import com.example.testappqr.models.SSODTO
import com.example.testappqr.domain.repository.LoginRepository
import com.example.testappqr.domain.usecase.util.ApiResult
import javax.inject.Inject

class GetUserDataUseCase @Inject constructor(private val loginRepository: LoginRepository){
    suspend operator fun invoke(request : String): ApiResult<SSODTO> {
        return loginRepository.getUserData(request)
    }
}