package com.example.testappqr.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.util.UUID
@Parcelize
data class SessionLazyDTO(
    val sessionId: UUID? = null,
    val moduleId: UUID,
    val sessionName: String,
    val startTime: Long,
    val endTime: Long,
    val verificationCode: String?,
    val active : Boolean
//    val signatures: Set<SignatureDTO>
) : Parcelable
@Parcelize
data class SessionDTO(
    val sessionId: UUID? = null,
    val module: ModuleLazyDTO,
    val sessionName: String,
    val startTime: Long,
    val endTime: Long,
    val verificationCode: String?,
    val active : Boolean,
    val signatures: @RawValue List<SignatureDTO> ? = emptyList()
) : Parcelable