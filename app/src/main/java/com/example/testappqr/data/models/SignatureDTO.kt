package com.example.testappqr.data.models

import java.util.UUID

data class SignatureDTO(
    val id: UUID,
    val userName: String,
    val userFirstName: String,
    val sessionId: UUID,
    val verificationCode :String
)
