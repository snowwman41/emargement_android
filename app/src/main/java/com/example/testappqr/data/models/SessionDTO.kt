package com.example.testappqr.data.models

import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

data class SessionDTO(
    val sessionId: UUID? = null,
    val moduleId: UUID,
    val sessionName: String,
    val startTime: Long,
    val endTime: Long,
    val verificationCode: String,
    val active : Boolean
//    val signatures: Set<SignatureDTO>
)