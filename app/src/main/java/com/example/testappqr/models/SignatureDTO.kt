package com.example.testappqr.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.util.UUID

@Parcelize
data class SignatureDTO(
    val id: UUID,
    val student : @RawValue StudentLazyDTO,
    val sessionId: UUID,
    val verificationCode :String,
    val codeType: CodeType
) : Parcelable

@Parcelize
data class SignatureLazyDTO(
    val id: UUID? = null,
    val studentId: String,
    val sessionId: UUID,
    val verificationCode: String,
    val codeType: CodeType
) : Parcelable

enum class CodeType {
    QR, READABLE, BEACON
}

