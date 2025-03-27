package com.example.testappqr.presentation.student.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.testappqr.models.CodeType
import com.example.testappqr.presentation.beacon.BeaconStudentView
import com.example.testappqr.presentation.login.viewmodels.LoginVM
import com.example.testappqr.presentation.navigation.Routes
import com.example.testappqr.presentation.navigation.StudentNavigationView
import com.example.testappqr.presentation.sharedviews.BasicButton
import com.example.testappqr.presentation.student.viewmodels.StudentCodeVM
import java.util.UUID

@Composable
fun StudentCodeScreen(
    navController: NavHostController,
    sessionId: UUID, loginVM: LoginVM,
    studentCodeVM: StudentCodeVM = hiltViewModel()
) {
    val studentCodeState by studentCodeVM.studentCodeState.collectAsStateWithLifecycle()
    val loginState by loginVM.loginState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    LaunchedEffect(studentCodeState.message) {
        studentCodeState.message?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            studentCodeVM.clearErrorMessage() // Clear the message after displaying
        }
    }
    LaunchedEffect(Unit) {
        studentCodeVM.getCodes(sessionId)
    }
    StudentNavigationView(
        navController = navController,
        title = "Code",
        showBackButton = true,
        loginVM = loginVM
    ) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            if (studentCodeState.codes.isNotEmpty()) {
                BasicButton(
                    onClick = {
                        navController.navigate(
                            Routes.STUDENT_QRCODE_SCANNER_BY_SESSION(
                                sessionId.toString()
                            )
                        )
                    }, text = "Sign with QR Code", modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp)
                )

                BeaconStudentView(
                    studentCodeVM = studentCodeVM,
                    sessionId = sessionId,
                    userId = loginState.userData?.authenticationSuccess?.attributes?.uid
                )
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextField(
                        value = studentCodeState.textFieldCode,
                        onValueChange = { studentCodeVM.updateTextFieldCode(it) },
                        label = { Text("Entrer le code de session") },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(onDone = {
                            Toast.makeText(context, "Code soumis ", Toast.LENGTH_SHORT).show()
                        }),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )

                    BasicButton(
                        onClick = {
                            loginState.userData?.authenticationSuccess?.attributes?.uid?.let {
                                studentCodeVM.sign(
                                    sessionId = sessionId,
                                    verificationCode = studentCodeState.textFieldCode,
                                    codeType = CodeType.READABLE,
                                    studentId = it
                                )
                            }
                        }, text = "Sign with code"

                    )
                }
            }
        }
    }
}
