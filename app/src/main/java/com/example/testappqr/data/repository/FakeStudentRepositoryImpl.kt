package com.example.testappqr.data.repository

import com.example.testappqr.domain.repository.StudentRepository
import com.example.testappqr.domain.usecase.util.ApiResult
import com.example.testappqr.models.CodeDTO
import com.example.testappqr.models.CodeType
import com.example.testappqr.models.ModuleLazyDTO
import com.example.testappqr.models.SessionDTO
import com.example.testappqr.models.SessionLazyDTO
import com.example.testappqr.models.SignatureDTO
import com.example.testappqr.models.SignatureLazyDTO
import com.example.testappqr.models.StudentLazyDTO
import com.example.testappqr.models.TeacherLazyDTO
import retrofit2.HttpException
import java.io.IOException
import java.util.UUID
import javax.inject.Inject

class FakeStudentRepositoryImpl @Inject constructor() : StudentRepository {
    suspend fun getActiveSessions(userId: String): List<SessionLazyDTO> {
        return listOf(
            SessionLazyDTO(
                UUID.fromString("10dc002f-35fa-445d-9a1c-99a78940333e"),
                UUID.fromString("66af01d4-17eb-402c-9efd-06da619e6d2f"),
                "third",
                1740222636,
                1740225636,
                "null",
                true,

                ),
            SessionLazyDTO(
                UUID.fromString("35ec002f-35fa-445d-9a1c-99a78940111e"),
                UUID.fromString("66af01d4-17eb-402c-9efd-06da619e6d2f"),
                "second",
                1740222636,
                1740225636,
                "null",
                true

            ),
            SessionLazyDTO(
                UUID.fromString("35ec002e-20fa-445d-9a1c-99a78940722e"),
                UUID.fromString("66af01d4-17eb-402c-9efd-06da619e6d2f"),
                "first",
                1740222636,
                1740225636,
                "null",
                true
            )
        )
    }

    override suspend fun studentActiveSessions(userId: String): List<SessionLazyDTO> {
        return listOf(
            SessionLazyDTO(
                UUID.fromString("10dc002f-35fa-445d-9a1c-99a78940333e"),
                UUID.fromString("66af01d4-17eb-402c-9efd-06da619e6d2f"),
                "third",
                1740222636,
                1740225636,
                "null",
                true,

                ),
            SessionLazyDTO(
                UUID.fromString("35ec002f-35fa-445d-9a1c-99a78940111e"),
                UUID.fromString("66af01d4-17eb-402c-9efd-06da619e6d2f"),
                "second",
                1740222636,
                1740225636,
                "null",
                true

            ),
            SessionLazyDTO(
                UUID.fromString("35ec002e-20fa-445d-9a1c-99a78940722e"),
                UUID.fromString("66af01d4-17eb-402c-9efd-06da619e6d2f"),
                "first",
                1740222636,
                1740225636,
                "null",
                true
            )
        )
    }

    override suspend fun studentCodeBySession(sessionId: UUID): List<CodeDTO> {
        return listOf(
            CodeDTO(
                UUID.fromString("35ec002e-20fa-445d-9a1c-99a78940722e"),
                "250",
                "1253654",
                "L ID 007435",
                TeacherLazyDTO("s23022841", "Second", "Prof", "test2@mail.de")
            )
        )
    }

    override suspend fun studentSign(signatureLazyDTO: SignatureLazyDTO): ApiResult<SessionDTO> {
        return try {
            val response = SessionDTO(
                sessionId = UUID.fromString("35ec002e-20fa-445d-9a1c-99a78940722e"),
                sessionName = "first",
                module = ModuleLazyDTO(
                    UUID.fromString("66af01d4-17eb-402c-9efd-06da619e6d2f"),
                    "Mobile",
                    UUID.fromString("b06509ed-e7f1-46ff-980b-85ef0d5935e0")
                ),
                startTime = 1740222636,
                endTime = 1740225636,
                verificationCode = "250",
                active = true,
                signatures = listOf(
                    SignatureDTO(
                        id = UUID.fromString("1c0707a1-242d-451d-b911-da984aed985f"),
                        student = StudentLazyDTO("b24028599", "Oscar", "Bauer", ""),
                        sessionId = UUID.fromString("35ec002e-20fa-445d-9a1c-99a78940722e"),
                        verificationCode = "250",
                        codeType = CodeType.QR
                    ), SignatureDTO(
                        id = UUID.fromString("d0e4aca8-7231-41e6-b15b-337c2033c22c"),
                        student = StudentLazyDTO("b24028599", "Oscar", "Bauer", ""),
                        sessionId = UUID.fromString("35ec002e-20fa-445d-9a1c-99a78940722e"),
                        codeType = CodeType.QR,
                        verificationCode = "TODO()"
                    ), SignatureDTO(
                        id = UUID.fromString("e7a87e28-0c0b-4cdc-a143-66cc2446eb8f"),
                        student = StudentLazyDTO("b24028599", "Oscar", "Bauer", ""),
                        sessionId = UUID.fromString("35ec002e-20fa-445d-9a1c-99a78940722e"),
                        codeType = CodeType.QR,
                        verificationCode = "TODO()"
                    )
                )
            )
            ApiResult.Success(response)
        } catch (e: IOException) {
            ApiResult.Error(e, "Network error occurred")
        } catch (e: HttpException) {
            ApiResult.Error(e, "HTTP error: ${e.code()}")
        } catch (e: Exception) {
            ApiResult.Error(e, e.message ?: "Unknown error occurred")
        }
    }
}