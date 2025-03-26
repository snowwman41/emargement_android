package com.example.testappqr.models

import android.os.Parcelable

import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.util.UUID
@Parcelize
data class ModuleDTO(
    val moduleId: UUID,
    val moduleName: String,
    val specialityId: String,
    val teachers: @RawValue List<TeacherDTO>,
    var sessions  : @RawValue List<SessionLazyDTO>
) : Parcelable

@Parcelize
data class ModuleLazyDTO(
    val moduleId: UUID,
    val moduleName: String?,
    val specialityId: UUID?
) : Parcelable


