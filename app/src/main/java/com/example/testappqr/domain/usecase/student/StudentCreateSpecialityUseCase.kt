package com.example.testappqr.domain.usecase.student



import com.example.testappqr.models.CodeDTO
import com.example.testappqr.models.SessionLazyDTO
import com.example.testappqr.domain.repository.StudentRepository
import com.example.testappqr.domain.usecase.util.ApiResult
import com.example.testappqr.models.SpecialityLazyDTO
import java.util.UUID
import javax.inject.Inject

class StudentCreateSpecialityUseCase @Inject constructor(private val studentRepository: StudentRepository){
    suspend operator fun invoke(specialityName : String) : ApiResult<Unit>{
        return studentRepository.studentCreateSpeciality(Speciality(specialityName))
    }
}
data class Speciality(
    val specialityName : String
)