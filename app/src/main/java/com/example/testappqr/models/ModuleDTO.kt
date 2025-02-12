package com.example.testappqr.models

import java.util.UUID

data class ModuleDTO(
    val moduleId: UUID,
    val moduleName: String,
    val speciality: String,
    val professorId: String,
    var sessions: List<SessionDTO>
)
data class ModuleLazyDTO(
    val moduleId: UUID,
    val moduleName: String,
    val speciality: String,
    val professorId: String,
)


