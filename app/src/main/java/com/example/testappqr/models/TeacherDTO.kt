package com.example.testappqr.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class TeacherDTO(
    val userId: String,
    val modules: @RawValue List<ModuleLazyDTO>?,
    val firstName: String,
    val lastName: String,
    val email: String,
    val code: @RawValue CodeDTO
) : Parcelable


@Parcelize
data class TeacherLazyDTO(
    val userId: String,
    val firstName: String,
    val lastName: String,
    val email: String
) : Parcelable


