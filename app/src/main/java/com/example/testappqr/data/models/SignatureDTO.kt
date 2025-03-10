package com.example.testappqr.data.models

import java.util.UUID

data class SignatureDTO(
    val id: UUID,
    val studentId : String,
    val sessionId: UUID,
    val verificationCode :String
)
