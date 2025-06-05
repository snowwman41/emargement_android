package com.example.testappqr.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.util.UUID

@Parcelize
data class SpecialityDTO(
    val id: UUID,
    val specialityName: String,
    val modules: @RawValue Set<ModuleLazyDTO>,
    val students: @RawValue Set<StudentDTO>
): Parcelable

@Parcelize
data class SpecialityLazyDTO(
    val id: UUID,
    val specialityName: String
) : Parcelable
