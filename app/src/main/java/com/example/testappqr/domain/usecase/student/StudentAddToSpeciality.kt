package com.example.testappqr.domain.usecase.student


import com.example.testappqr.models.SessionLazyDTO
import com.example.testappqr.domain.repository.StudentRepository
import com.example.testappqr.domain.usecase.util.ApiResult
import javax.inject.Inject

class StudentAddToSpeciality @Inject constructor(private val studentRepository: StudentRepository){
    suspend operator fun invoke(studentSpeciality : Map<String,String>) : ApiResult<Unit> {
        return studentRepository.studentAddToSpeciality(studentSpeciality)
    }
}