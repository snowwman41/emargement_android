package com.example.testappqr.presentation.navigation

object Routes {
    const val LOGIN = "login"

    const val PROFESSOR_SESSIONS = "professor/sessions"
    val PROFESSOR_SESSION: (sessionId: String) -> String = { sessionId ->
        "professor/sessions/${sessionId}"
    }

    const val PROFESSOR_MODULES = "professor/modules"
    val PROFESSOR_MODULE: (moduleId: String) -> String = { moduleId ->
        "professor/modules/${moduleId}"
    }

    const val PROFESSOR_CODE = "professor/code"


    const val STUDENT = "student"
    const val STUDENT_QRCODE_SCANNER = "student/qrcode-scanner"

}