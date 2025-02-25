package com.example.testappqr.models

import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

data class SessionDTO(
    val sessionId: UUID,
    val moduleId: UUID,
    val sessionName: String,
    val date: String,
    val startTime: String,
    val endTime: String,
    val timestamp: String,
    val verificationCode: String,
    val active : Boolean
//    val signatures: Set<SignatureDTO>
)