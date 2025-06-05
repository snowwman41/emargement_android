package com.example.testappqr.domain.usecase.student



import com.example.testappqr.domain.repository.StudentRepository
import com.example.testappqr.domain.usecase.util.ApiResult
import com.example.testappqr.data.models.SpecialityCreationDTO
import com.example.testappqr.data.models.SpecialityLazyDTO
import javax.inject.Inject

class StudentCreateSpecialityUseCase @Inject constructor(private val studentRepository: StudentRepository){
    suspend operator fun invoke(specialityName : String) : ApiResult<SpecialityLazyDTO>{
        return studentRepository.studentCreateSpeciality(SpecialityCreationDTO(specialityName))
    }
}
