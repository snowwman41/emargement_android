package com.example.testappqr.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.util.UUID

@Parcelize
data class CodeDTO(
    val codeId: UUID,
    val readableCode: String,
    val qrCode: String,
    val beaconId: String?,
    val teacher: @RawValue TeacherLazyDTO
) : Parcelable
