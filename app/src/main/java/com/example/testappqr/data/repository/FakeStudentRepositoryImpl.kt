package com.example.testappqr.data.repository

import com.example.testappqr.data.models.SessionLazyDTO
import com.example.testappqr.domain.repository.StudentRepository
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
}