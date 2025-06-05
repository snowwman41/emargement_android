package com.example.testappqr.domain.repository

import com.example.testappqr.domain.usecase.util.ApiResult
import com.example.testappqr.data.models.CodeDTO
import com.example.testappqr.data.models.ModuleLazyDTO
import com.example.testappqr.data.models.SessionDTO
import com.example.testappqr.data.models.SessionLazyDTO
import com.example.testappqr.data.models.SignatureLazyDTO
import com.example.testappqr.data.models.SpecialityCreationDTO
import com.example.testappqr.data.models.SpecialityLazyDTO
import com.example.testappqr.data.models.UserCreationDTO
import java.util.UUID

interface StudentRepository {
    suspend fun studentActiveSessions (userId : String): List<SessionLazyDTO>
    suspend fun studentCodeBySession (sessionId : UUID): List<CodeDTO>
    suspend fun studentSign (signatureLazyDTO: SignatureLazyDTO): ApiResult<SessionDTO>
    suspend fun studentCreateSpeciality (specialityCreationDTO : SpecialityCreationDTO): ApiResult<SpecialityLazyDTO>
    suspend fun studentCreateUser (userCreationDTO : UserCreationDTO): ApiResult<Unit>
    suspend fun studentAddToSpeciality (studentSpeciality : Map<String,String>) : ApiResult<Unit>
    suspend fun studentModules(studentId: String): ApiResult<List<ModuleLazyDTO>>
}

