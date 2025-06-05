package com.example.testappqr.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class StudentDTO(
    val userId: String,
    val specialities: Set<SpecialityLazyDTO>,
    val firstName: String,
    val lastName: String,
    val email: String
) : Parcelable


@Parcelize
data class StudentLazyDTO(
    val userId: String,
    val firstName: String,
    val lastName: String,
    val email: String
) : Parcelable

