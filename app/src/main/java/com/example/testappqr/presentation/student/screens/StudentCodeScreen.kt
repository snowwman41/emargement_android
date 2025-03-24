package com.example.testappqr.presentation.student.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.testappqr.data.models.ModuleLazyDTO
import com.example.testappqr.data.models.SessionDTO
import com.example.testappqr.data.models.SignatureDTO
import com.example.testappqr.presentation.beacon.BeaconView
import com.example.testappqr.presentation.navigation.Routes
import com.example.testappqr.presentation.navigation.StudentNavigationView
import com.example.testappqr.presentation.sharedviews.BasicButton
import com.example.testappqr.presentation.sharedviews.SessionViewHeader
import java.util.UUID

@Composable
fun StudentCodeScreen(navController: NavHostController, sessionId: UUID) {
    StudentNavigationView(navController = navController, title = "Code", showBackButton = true) {
        Column {
//            SessionViewHeader(
//                SessionDTO(
//                    sessionId = UUID.fromString("35ec002e-20fa-445d-9a1c-99a78940722e"),
//                    sessionName = "first",
//                    module = ModuleLazyDTO(UUID.fromString("66af01d4-17eb-402c-9efd-06da619e6d2f"), "Mobile", UUID.fromString("b06509ed-e7f1-46ff-980b-85ef0d5935e0")),
//                    startTime = 1740222636,
//                    endTime = 1740225636,
//                    verificationCode = "250",
//                    active = true,
//                    signatures = listOf(
//                        SignatureDTO(
//                            id = UUID.fromString("1c0707a1-242d-451d-b911-da984aed985f"),
//                            studentId = "b24028599",
//                            sessionId = UUID.fromString("35ec002e-20fa-445d-9a1c-99a78940722e"),
//                            verificationCode = "250"
//                        ), SignatureDTO(
//                            id = UUID.fromString("d0e4aca8-7231-41e6-b15b-337c2033c22c"),
//                            studentId = "b12345678",
//                            sessionId = UUID.fromString("35ec002e-20fa-445d-9a1c-99a78940722e"),
//                            verificationCode = "250"
//                        ), SignatureDTO(
//                            id = UUID.fromString("e7a87e28-0c0b-4cdc-a143-66cc2446eb8f"),
//                            studentId = "z87654321",
//                            sessionId = UUID.fromString("35ec002e-20fa-445d-9a1c-99a78940722e"),
//                            verificationCode = "250"
//                        )
//                    )
//
//                )
//            )
            BasicButton(onClick = {
                navController.navigate(
                    Routes.STUDENT_QRCODE_SCANNER_BY_SESSION(
                        sessionId.toString()
                    )
                )
            }, text = "Scan QR Code")
            BeaconView(isProfessor = false)
//            BasicButton(onClick = {}, text = "validate with code")
        }
    }
}