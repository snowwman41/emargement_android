package com.example.testappqr.presentation.navigation

import java.util.UUID

object Routes {
    const val LOGIN = "login"
    const val PROFESSOR = "professor"
    val PROFESSOR_MODULE : (moduleId : String) -> String = { moduleId  ->
        "professor/module/${moduleId}"
    }

    const val STUDENT = "student"
    const val STUDENT_QRCODE_SCANNER = "student/qrcode-scanner"

}