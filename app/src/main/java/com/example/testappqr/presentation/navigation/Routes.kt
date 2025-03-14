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

    val PROFESSOR_SESSIONS_BY_MODULE: (moduleId: String) -> String = { moduleId ->
        "professor/modules/${moduleId}/sessions"
    }
    val PROFESSOR_SESSION_BY_MODULE: (moduleId: String,sessionId : String) -> String = { moduleId,sessionId ->
        "professor/modules/${moduleId}/sessions/${sessionId}"

    }
    val PROFESSOR_STUDENTS_BY_MODULE: (moduleId: String) -> String = { moduleId ->
        "professor/modules/${moduleId}/students"
    }


    const val PROFESSOR_CODE = "professor/code"


    const val STUDENT = "student"
    const val STUDENT_QRCODE_SCANNER = "student/qrcode-scanner"

}