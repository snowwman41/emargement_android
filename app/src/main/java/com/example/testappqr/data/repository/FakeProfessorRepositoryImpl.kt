//package com.example.testappqr.data.repository
//
//import com.example.testappqr.domain.repository.ProfessorRepository
//import com.example.testappqr.domain.usecase.util.ApiResult
//import com.example.testappqr.models.CodeDTO
//import com.example.testappqr.models.CodeType
//import com.example.testappqr.models.ModuleDTO
//import com.example.testappqr.models.ModuleLazyDTO
//import com.example.testappqr.models.SSODTO
//import com.example.testappqr.models.SessionDTO
//import com.example.testappqr.models.SessionLazyDTO
//import com.example.testappqr.models.SignatureDTO
//import com.example.testappqr.models.StudentDTO
//import com.example.testappqr.models.TeacherDTO
//import com.example.testappqr.models.TeacherLazyDTO
//import retrofit2.HttpException
//import java.io.IOException
//import java.util.UUID
//import javax.inject.Inject
//import javax.inject.Singleton
//
//
//@Singleton
//class FakeProfessorRepositoryImpl @Inject constructor() : ProfessorRepository {
//
////    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
//
//    override suspend fun modules(userId: String): List<ModuleLazyDTO> {
//        return listOf(
//            ModuleLazyDTO(
//                moduleId = UUID.fromString("b3559075-e922-4a35-b57c-261c94955c86"),
//                moduleName = "Git",
//                specialityId = UUID.fromString("b06509ed-e7f1-46ff-980b-85ef0d5935e0")
//            ), ModuleLazyDTO(
//                moduleId = UUID.fromString("66af01d4-17eb-402c-9efd-06da619e6d2f"),
//                moduleName = "Mobile",
//                specialityId = UUID.fromString("b06509ed-e7f1-46ff-980b-85ef0d5935e0")
//            ), ModuleLazyDTO(
//                moduleId = UUID.fromString("8c6e3e0b-603a-443d-891d-5985a6a1dbd4"),
//                moduleName = "Environement Informatique",
//                specialityId = UUID.fromString("b06509ed-e7f1-46ff-980b-85ef0d5935e0")
//            )
//        )
//    }
//
//    override suspend fun module(moduleId: UUID): ModuleDTO {
//        TODO("Not yet implemented")
////        return ModuleDTO(
////            moduleId = UUID.fromString("66af01d4-17eb-402c-9efd-06da619e6d2f"),
////            moduleName = "Mobile",
////            specialityId = "b06509ed-e7f1-46ff-980b-85ef0d5935e0",
////            teachers = listOf(
////                TeacherDTO(
////                    "s23022841", listOf(
////                        ModuleLazyDTO(
////                            UUID.fromString("66af01d4-17eb-402c-9efd-06da619e6d2f"),
////                            "Mobile",
////                            UUID.fromString("b06509ed-e7f1-46ff-980b-85ef0d5935e0")
////                        )
////                    ), "Prof", "test2@mail.de", "test2",
////                    code = CodeDTO(
////                        UUID.fromString("66af01d4-17eb-402c-9efd-06da619e6d2f"),
////                        "250",
////                        qrCode = "TODO()",
////                        beaconId = "TODO()",
////                        teacher =
////                    ))
////                ),
////                TeacherDTO("b24028566", "Oscar", "Bauer", "test@mail.de")
////            ),
////            sessions = listOf(
////                SessionLazyDTO(
////                    UUID.fromString("10dc002f-35fa-445d-9a1c-99a78940333e"),
////                    UUID.fromString("66af01d4-17eb-402c-9efd-06da619e6d2f"),
////                    "third",
////                    1740222636,
////                    1740225636,
////                    "test",
////                    false,
////
////                    ), SessionLazyDTO(
////                    UUID.fromString("35ec002f-35fa-445d-9a1c-99a78940111e"),
////                    UUID.fromString("66af01d4-17eb-402c-9efd-06da619e6d2f"),
////                    "second",
////                    1740222636,
////                    1740225636,
////                    "test",
////                    false
////
////                ), SessionLazyDTO(
////                    UUID.fromString("35ec002e-20fa-445d-9a1c-99a78940722e"),
////                    UUID.fromString("66af01d4-17eb-402c-9efd-06da619e6d2f"),
////                    "first",
////                    1740222636,
////                    1740225636,
////                    "test",
////                    false
////                )
////            )
////        )
//    }
//
//    override suspend fun getCodeByTeacher(userId: String): CodeDTO {
//        TODO("Not yet implemented")
//    }
//
//
//    override suspend fun openSession(sessionId: UUID): ApiResult<SessionDTO> {
//        TODO("Not yet implemented")
////        return try {
////            val result = SessionDTO(
////                sessionId = UUID.fromString("35ec002e-20fa-445d-9a1c-99a78940722e"),
////                sessionName = "first",
////                module = ModuleLazyDTO(
////                    UUID.fromString("66af01d4-17eb-402c-9efd-06da619e6d2f"),
////                    "Mobile",
////                    UUID.fromString("b06509ed-e7f1-46ff-980b-85ef0d5935e0")
////                ),
////                startTime = 1740222636,
////                endTime = 1740225636,
////                verificationCode = "250",
////                active = true,
////                signatures = listOf(
////                    SignatureDTO(
////                        id = UUID.fromString("1c0707a1-242d-451d-b911-da984aed985f"),
////                        student = StudentDTO("b24028599", "Oscar", "Bauer", ""),
////                        sessionId = UUID.fromString("35ec002e-20fa-445d-9a1c-99a78940722e"),
////                        verificationCode = "250",
////                        codeType = CodeType.QR
////                    ), SignatureDTO(
////                        id = UUID.fromString("d0e4aca8-7231-41e6-b15b-337c2033c22c"),
////                        student = StudentDTO("b24028599", "Oscar", "Bauer", ""),
////                        sessionId = UUID.fromString("35ec002e-20fa-445d-9a1c-99a78940722e"),
////                        codeType = CodeType.QR,
////                        verificationCode = "TODO()"
////                    ), SignatureDTO(
////                        id = UUID.fromString("e7a87e28-0c0b-4cdc-a143-66cc2446eb8f"),
////                        student = StudentDTO("b24028599", "Oscar", "Bauer", ""),
////                        sessionId = UUID.fromString("35ec002e-20fa-445d-9a1c-99a78940722e"),
////                        codeType = CodeType.QR,
////                        verificationCode = "TODO()"
////                    )
////                )
////            )
////            ApiResult.Success(result)
////        } catch (e: IOException) {
////            ApiResult.Error(e, "Network error occurred")
////        } catch (e: HttpException) {
////            ApiResult.Error(e, "HTTP error: ${e.code()}")
////        } catch (e: Exception) {
////            ApiResult.Error(e, e.message ?: "Unknown error occurred")
////        }
//    }
//
//    override suspend fun closeSession(sessionId: UUID): ApiResult<SessionDTO> {
//        TODO("Not yet implemented")
////        return try {
////            val result = SessionDTO(
////                sessionId = UUID.fromString("35ec002e-20fa-445d-9a1c-99a78940722e"),
////                sessionName = "first",
////                module = ModuleLazyDTO(
////                    UUID.fromString("66af01d4-17eb-402c-9efd-06da619e6d2f"),
////                    "Mobile",
////                    UUID.fromString("b06509ed-e7f1-46ff-980b-85ef0d5935e0")
////                ),
////                startTime = 1740222636,
////                endTime = 1740225636,
////                verificationCode = "250",
////                active = true,
////                signatures = listOf(
////                    SignatureDTO(
////                        id = UUID.fromString("1c0707a1-242d-451d-b911-da984aed985f"),
////                        student = StudentDTO("b24028599", "Oscar", "Bauer", ""),
////                        sessionId = UUID.fromString("35ec002e-20fa-445d-9a1c-99a78940722e"),
////                        verificationCode = "250",
////                        codeType = CodeType.QR
////                    ), SignatureDTO(
////                        id = UUID.fromString("d0e4aca8-7231-41e6-b15b-337c2033c22c"),
////                        student = StudentDTO("b24028599", "Oscar", "Bauer", ""),
////                        sessionId = UUID.fromString("35ec002e-20fa-445d-9a1c-99a78940722e"),
////                        codeType = CodeType.QR,
////                        verificationCode = "TODO()"
////                    ), SignatureDTO(
////                        id = UUID.fromString("e7a87e28-0c0b-4cdc-a143-66cc2446eb8f"),
////                        student = StudentDTO("b24028599", "Oscar", "Bauer", ""),
////                        sessionId = UUID.fromString("35ec002e-20fa-445d-9a1c-99a78940722e"),
////                        codeType = CodeType.QR,
////                        verificationCode = "TODO()"
////                    )
////                )
////            )
////            ApiResult.Success(result)
////        } catch (e: IOException) {
////            ApiResult.Error(e, "Network error occurred")
////        } catch (e: HttpException) {
////            ApiResult.Error(e, "HTTP error: ${e.code()}")
////        } catch (e: Exception) {
////            ApiResult.Error(e, e.message ?: "Unknown error occurred")
////        }
//    }
//
//    override suspend fun professorCreateUser(teacherLazyDTO: TeacherLazyDTO): ApiResult<Unit> {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun addSession(session: SessionDTO): List<SessionLazyDTO> {
//        return emptyList()
//    }
//
//    override suspend fun getSessionsOfUserOnDate(
//        userId: String,
//        date: String
//    ): ApiResult<List<SessionLazyDTO>> {
//
//        return try {
//            val result = listOf(
//                SessionLazyDTO(
//                    UUID.fromString("10dc002f-35fa-445d-9a1c-99a78940333e"),
//                    UUID.fromString("66af01d4-17eb-402c-9efd-06da619e6d2f"),
//                    "third",
//                    1740222636,
//                    1740225636,
//                    "test",
//                    true,
//
//                    ), SessionLazyDTO(
//                    UUID.fromString("35ec002f-35fa-445d-9a1c-99a78940111e"),
//                    UUID.fromString("66af01d4-17eb-402c-9efd-06da619e6d2f"),
//                    "second",
//                    1740222636,
//                    1740225636,
//                    "test",
//                    true
//
//                ), SessionLazyDTO(
//                    UUID.fromString("35ec002e-20fa-445d-9a1c-99a78940722e"),
//                    UUID.fromString("66af01d4-17eb-402c-9efd-06da619e6d2f"),
//                    "first",
//                    1740222636,
//                    1740225636,
//                    "test",
//                    true
//                )
//            )
//            ApiResult.Success(result)
//        } catch (e: IOException) {
//            ApiResult.Error(e, "Network error occurred")
//        } catch (e: HttpException) {
//            ApiResult.Error(e, "HTTP error: ${e.code()}")
//        } catch (e: Exception) {
//            ApiResult.Error(e, e.message ?: "Unknown error occurred")
//        }
//
//    }
//
////    override suspend fun getSessionsByModule(moduleId: UUID): List<SessionLazyDTO> {
////        return listOf(
////            SessionLazyDTO(
////                UUID.fromString("10dc002f-35fa-445d-9a1c-99a78940333e"),
////                UUID.fromString("66af01d4-17eb-402c-9efd-06da619e6d2f"),
////                "third for module",
////                1740222636,
////                1740225636,
////                "test",
////                true,
////
////                ), SessionLazyDTO(
////                UUID.fromString("35ec002f-35fa-445d-9a1c-99a78940111e"),
////                UUID.fromString("66af01d4-17eb-402c-9efd-06da619e6d2f"),
////                "second for module",
////                1740222636,
////                1740225636,
////                "test",
////                true
////
////            ), SessionLazyDTO(
////                UUID.fromString("35ec002e-20fa-445d-9a1c-99a78940722e"),
////                UUID.fromString("66af01d4-17eb-402c-9efd-06da619e6d2f"),
////                "first for module",
////                1740222636,
////                1740225636,
////                "test",
////                true
////            )
////        )
////    }
//
//    override suspend fun getSession(sessionId: UUID): SessionDTO {
//        TODO("Not yet implemented")
////        return SessionDTO(
////            sessionId = UUID.fromString("35ec002e-20fa-445d-9a1c-99a78940722e"),
////            sessionName = "first",
////            module = ModuleLazyDTO(
////                UUID.fromString("66af01d4-17eb-402c-9efd-06da619e6d2f"),
////                "Mobile",
////                UUID.fromString("b06509ed-e7f1-46ff-980b-85ef0d5935e0")
////            ),
////            startTime = 1740222636,
////            endTime = 1740225636,
////            verificationCode = "250",
////            active = true,
////            signatures = listOf(
////                SignatureDTO(
////                    id = UUID.fromString("1c0707a1-242d-451d-b911-da984aed985f"),
////                    student = StudentDTO("b24028599", "Oscar", "Bauer", ""),
////                    sessionId = UUID.fromString("35ec002e-20fa-445d-9a1c-99a78940722e"),
////                    verificationCode = "250",
////                    codeType = CodeType.QR
////                ), SignatureDTO(
////                    id = UUID.fromString("d0e4aca8-7231-41e6-b15b-337c2033c22c"),
////                    student = StudentDTO("b24028599", "Oscar", "Bauer", ""),
////                    sessionId = UUID.fromString("35ec002e-20fa-445d-9a1c-99a78940722e"),
////                    codeType = CodeType.QR,
////                    verificationCode = "TODO()"
////                ), SignatureDTO(
////                    id = UUID.fromString("e7a87e28-0c0b-4cdc-a143-66cc2446eb8f"),
////                    student = StudentDTO("b24028599", "Oscar", "Bauer", ""),
////                    sessionId = UUID.fromString("35ec002e-20fa-445d-9a1c-99a78940722e"),
////                    codeType = CodeType.QR,
////                    verificationCode = "TODO()"
////                )
////            )
////        )
//    }
//
//    override suspend fun getStudentsByModule(moduleId: UUID): List<StudentDTO> {
//        TODO("Not yet implemented")
////        return listOf(
////            StudentDTO(
////                studentId = "b24028599",
////                firstName = "Oscar",
////                lastName = "Bauer",
////                email = ""
////            ),
////            StudentDTO(
////                studentId = "b24028599",
////                firstName = "Oscar",
////                lastName = "Bauer",
////                email = ""
////            ),
////            StudentDTO(
////                studentId = "b24028599",
////                firstName = "Oscar",
////                lastName = "Bauer",
////                email = ""
////            ),
////        )
//
//    }
//
//    override suspend fun verifyToken(): Boolean {
//        TODO("Not yet implemented")
//    }
//
//
//    override suspend fun casValidate(url: String): SSODTO {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun createModule(module: ModuleLazyDTO): List<ModuleLazyDTO> {
//        TODO("Not yet implemented")
//    }
//
//
//}