package com.example.testappqr.domain.usecase.student




import com.example.testappqr.models.CodeDTO
import com.example.testappqr.models.SessionLazyDTO
import com.example.testappqr.domain.repository.StudentRepository
import com.example.testappqr.domain.usecase.util.ApiResult
import com.example.testappqr.models.SessionDTO
import com.example.testappqr.models.SignatureLazyDTO
import java.util.UUID
import javax.inject.Inject

class StudentSignUseCase @Inject constructor(private val studentRepository: StudentRepository){
    suspend operator fun invoke(signatureLazyDTO : SignatureLazyDTO) : ApiResult<SessionDTO> {
        return studentRepository.studentSign(signatureLazyDTO)
    }
}