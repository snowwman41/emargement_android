package com.example.testappqr.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TeacherLazyDTO(
    val userId: String,
    val firstName: String,
    val lastName: String,
    val email: String
) : Parcelable
