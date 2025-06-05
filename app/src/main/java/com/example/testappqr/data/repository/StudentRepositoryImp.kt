package com.example.testappqr.data.repository

import com.example.testappqr.data.datasource.remote.ApiService
import com.example.testappqr.data.models.CodeDTO
import com.example.testappqr.data.models.SessionLazyDTO
import com.example.testappqr.domain.repository.StudentRepository
import com.example.testappqr.domain.usecase.util.ApiResult
import com.example.testappqr.domain.usecase.util.safeApiCall
import com.example.testappqr.data.models.ModuleLazyDTO
import com.example.testappqr.data.models.SessionDTO
import com.example.testappqr.data.models.SignatureLazyDTO
import com.example.testappqr.data.models.SpecialityCreationDTO
import com.example.testappqr.data.models.SpecialityLazyDTO
import com.example.testappqr.data.models.UserCreationDTO
import retrofit2.HttpException
import java.io.IOException
import java.util.UUID
import javax.inject.Inject

class StudentRepositoryImp @Inject constructor(private val apiService: ApiService) :
    StudentRepository {


    override suspend fun studentActiveSessions(userId: String): List<SessionLazyDTO> {
        return apiService.getActiveSessionOfStudent(userId)
    }

    override suspend fun studentCodeBySession(sessionId: UUID): List<CodeDTO> {
        return apiService.getStudentCodeBySession(sessionId)
    }

    override suspend fun studentSign(signatureLazyDTO: SignatureLazyDTO): ApiResult<SessionDTO> {
        return safeApiCall{  apiService.studentSign(signatureLazyDTO)}
    }

    override suspend fun studentCreateSpeciality(specialityCreationDTO: SpecialityCreationDTO): ApiResult<SpecialityLazyDTO> {
        return safeApiCall{  apiService.createSpeciality(specialityCreationDTO)}
    }

    override suspend fun studentCreateUser(userCreationDTO: UserCreationDTO): ApiResult<Unit> {
        return safeApiCall{  apiService.createStudent(userCreationDTO)}

    }

    override suspend fun studentAddToSpeciality(studentSpeciality: Map<String, String>): ApiResult<Unit> {
        return safeApiCall{  apiService.addStudentToSpeciality(studentSpeciality)}

    }

    override suspend fun studentModules(studentId: String): ApiResult<List<ModuleLazyDTO>> {
        return safeApiCall{  apiService.studentModules(studentId)}
    }
}