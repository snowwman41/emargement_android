package com.example.testappqr.data.models

import java.util.UUID

data class SessionLazyDTO(
    val sessionId: UUID? = null,
    val moduleId: UUID,
    val sessionName: String,
    val startTime: Long,
    val endTime: Long,
    val verificationCode: String,
    val active : Boolean
//    val signatures: Set<SignatureDTO>
)

data class SessionDTO(
    val sessionId: UUID? = null,
    val moduleId: UUID,
    val sessionName: String,
    val startTime: Long,
    val endTime: Long,
    val verificationCode: String,
    val active : Boolean,
    val signatures: List<SignatureDTO>
)