package com.example.testappqr.data.models

import java.util.UUID

data class SignatureDTO(
    val id: UUID,
    val student : StudentLazyDTO,
    val sessionId: UUID,
    val verificationCode :String,
    val codeType: CodeType
)
enum class CodeType {
    QR, READABLE, BEACON
}