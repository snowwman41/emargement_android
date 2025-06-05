//package com.example.testappqr.domain
//
//import com.example.testappqr.data.models.CodeDTO
//import com.example.testappqr.data.models.CodeType
//import com.example.testappqr.data.models.ModuleDTO
//import com.example.testappqr.data.models.SessionDTO
//import com.example.testappqr.data.models.SignatureDTO
//import com.example.testappqr.data.models.SpecialityDTO
//import com.example.testappqr.data.models.StudentDTO
//import com.example.testappqr.data.models.TeacherDTO
//
//import com.example.testappqr.domain.models.Code
//import com.example.testappqr.domain.models.Module
//import com.example.testappqr.domain.models.Session
//import com.example.testappqr.domain.models.Signature
//import com.example.testappqr.domain.models.Speciality
//import com.example.testappqr.domain.models.Student
//import com.example.testappqr.domain.models.Teacher
//
//
//fun CodeDTO.toDomain(): Code {
//    return Code(
//        codeId = codeId,
//        readableCode = readableCode,
//        qrCode = qrCode,
//        beaconId = beaconId,
//        teacher = teacher.toDomain() // Explicitly map TeacherDTO to Teacher
//    )
//}
//
//fun TeacherDTO.toDomain(): Teacher {
//    return Teacher(
//        userId = userId,
//        modules = modules?.map(ModuleDTO::toDomain)?.toList() ?: emptyList(),
//        firstName = firstName,
//        lastName = lastName,
//        email = email,
//        code = code.toDomain()
//    )
//}
//
//fun ModuleDTO.toDomain(): Module {
//    return Module(
//        moduleId,
//        moduleName.orEmpty(),
//        specialityId.orEmpty(),
//        teachers.map(TeacherDTO::toDomain).toList(),
//        sessions.map(SessionDTO::toDomain).toList()
//    )
//}
//
//
//fun SessionDTO.toDomain(): Session {
//    return Session(
//        sessionId = sessionId,
//        module = module.toDomain(),
//        sessionName = sessionName,
//        startTime = startTime,
//        endTime = endTime,
//        verificationCode = verificationCode,
//        active = active,
//        signatures = signatures,
//    )
//}
//
//fun SignatureDTO.toDomain(): Signature {
//    return Signature(
//        id = id,
//        student = student.toDomain(),
//        sessionId = sessionId,
//        verificationCode = verificationCode,
//        codeType = codeType
//    )
//}
//
//fun StudentDTO.toDomain(): Student {
//    return Student(
//        userId =userId,
//        specialities = specialities.map(SpecialityDTO::toDomain).toSet(),
//        firstName = firstName,
//        lastName = lastName,
//        email = email
//    )
//}
//
//fun SpecialityDTO.toDomain() : Speciality {
//    return Speciality(
//        id = id,
//        specialityName = specialityName,
//        modules = modules.map(ModuleDTO :: toDomain).toSet(),
//        students = students.map(StudentDTO :: toDomain).toSet()
//    )
//}
//fun List<SessionDTO>.toDomain(): List<Session> {
//    return this.map { it.toDomain() }
//}
//
//
