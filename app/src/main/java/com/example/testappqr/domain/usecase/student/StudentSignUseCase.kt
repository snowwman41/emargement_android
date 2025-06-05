package com.example.testappqr.domain.usecase.student




import com.example.testappqr.data.models.CodeDTO
import com.example.testappqr.data.models.SessionLazyDTO
import com.example.testappqr.domain.repository.StudentRepository
import com.example.testappqr.domain.usecase.util.ApiResult
import com.example.testappqr.data.models.SessionDTO
import com.example.testappqr.data.models.SignatureLazyDTO
import java.util.UUID
import javax.inject.Inject

class StudentSignUseCase @Inject constructor(private val studentRepository: StudentRepository){
    suspend operator fun invoke(signatureLazyDTO : SignatureLazyDTO) : ApiResult<SessionDTO> {
        return studentRepository.studentSign(signatureLazyDTO)
    }
}