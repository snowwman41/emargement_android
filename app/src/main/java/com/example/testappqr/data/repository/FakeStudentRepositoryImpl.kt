package com.example.testappqr.data.repository

import com.example.testappqr.data.models.SessionDTO
import com.example.testappqr.domain.repository.StudentRepository
import java.util.UUID
import javax.inject.Inject

class FakeStudentRepositoryImpl @Inject constructor() : StudentRepository {
    override suspend fun getActiveSessions(userId: String): List<SessionDTO> {
        return listOf(
            SessionDTO(
                UUID.fromString("10dc002f-35fa-445d-9a1c-99a78940333e"),
                UUID.fromString("66af01d4-17eb-402c-9efd-06da619e6d2f"),
                "third",
                1740222636,
                1740225636,
                "null",
                false,

                ),
            SessionDTO(
                UUID.fromString("35ec002f-35fa-445d-9a1c-99a78940111e"),
                UUID.fromString("66af01d4-17eb-402c-9efd-06da619e6d2f"),
                "second",
                1740222636,
                1740225636,
                "null",
                false

            ),
            SessionDTO(
                UUID.fromString("35ec002e-20fa-445d-9a1c-99a78940722e"),
                UUID.fromString("66af01d4-17eb-402c-9efd-06da619e6d2f"),
                "first",
                1740222636,
                1740225636,
                "null",
                false
            )
        )
    }
}