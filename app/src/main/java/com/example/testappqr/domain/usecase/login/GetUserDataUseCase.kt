package com.example.testappqr.domain.usecase.login

import com.example.testappqr.data.models.SSODTO
import com.example.testappqr.domain.repository.LoginRepository
import javax.inject.Inject

class GetUserDataUseCase @Inject constructor(private val loginRepository: LoginRepository){
    suspend operator fun invoke(request : String): SSODTO {
        return loginRepository.getUserData(request)
    }
}