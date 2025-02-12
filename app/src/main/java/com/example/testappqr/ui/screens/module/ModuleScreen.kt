package com.example.testappqr.ui.screens.module

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.testappqr.SharedModel
import com.example.testappqr.models.ModuleDTO
import com.example.testappqr.models.SessionDTO
import com.example.testappqr.network.RetrofitApi
import com.example.testappqr.ui.common.components.BasicButton
import com.example.testappqr.ui.common.components.NavigationScreen
import com.example.testappqr.ui.screens.home.components.QRCodeComponent
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModuleScreen(navController: NavHostController, sharedModel: SharedModel) {
    var showModal by remember { mutableStateOf(false) }
    var qrCodeModal by remember { mutableStateOf(false) }
    var startTime by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("") }
    var sessionName by remember { mutableStateOf("") }
    var code by remember { mutableStateOf("") }

    var module by remember { mutableStateOf<ModuleDTO?>(null) }
    val coroutine = rememberCoroutineScope()

    NavigationScreen(navController = navController, title = "Module", showBackButton = true) {
        Column {
            if (sharedModel.moduleId != null) {
                LaunchedEffect(Unit) { module = getModule(sharedModel.moduleId!!) }
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                BasicButton(onClick = { showModal = true }, text = "Add session")
            }
            if (module != null) {
                LazyColumn {
                    items(module!!.sessions) {
                        Card(
                            modifier = Modifier
                                .fillParentMaxWidth()
                                .padding(8.dp)
                                .clickable {
                                    qrCodeModal = true; sharedModel.sessionId = it.sessionId
                                }
                        ) {
                            Row {
                                Column(
                                    modifier = Modifier
                                        .padding(10.dp)
                                ) {
                                    Text(
                                        it.sessionName,
                                        style = TextStyle(
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                    Text(
                                        it.startTime + " - " + it.endTime,
                                        style = TextStyle(fontSize = 18.sp)
                                    )
                                }
                                Column {
                                    if (it.active) {
                                        Text("Active", style = TextStyle(fontSize = 18.sp))
                                        Text(
                                            "Verification code : " + it.verificationCode,
                                            style = TextStyle(fontSize = 18.sp)
                                        )
                                    }
                                }

                            }
                        }
                    }
                }

                if (showModal) {
                    BasicAlertDialog(onDismissRequest = { showModal = false }) {
                        Surface(
                            shape = MaterialTheme.shapes.medium,
                            color = MaterialTheme.colorScheme.surface,
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text("Enter a new Session")
                                TextField(label = { Text("session name") },
                                    value = sessionName,
                                    onValueChange = { sessionName = it })
                                TextField(label = { Text("start time") },
                                    value = startTime,
                                    onValueChange = { startTime = it })

                                TextField(label = { Text("end time") },
                                    value = endTime,
                                    onValueChange = { endTime = it })
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    BasicButton(
                                        onClick = { showModal = false },
                                        text = "Cancel",
                                        textSize = 16.sp
                                    )
                                    BasicButton(onClick = {
                                        coroutine.launch {
                                            postSession(
                                                sessionId = UUID.randomUUID(),
                                                sharedModel = sharedModel,
                                                startTime = startTime,
                                                endTime = endTime,
                                                sessionName = sessionName
                                            )
                                            module = getModule(sharedModel.moduleId!!)
                                        }
                                        showModal = false
                                    }, text = "Add", textSize = 16.sp)
                                }
                            }
                        }
                    }
                }
                if (qrCodeModal) {
                    BasicAlertDialog(onDismissRequest = { qrCodeModal = false }) {
                        Surface(
                            shape = MaterialTheme.shapes.medium,
                            color = MaterialTheme.colorScheme.surface,
                            modifier = Modifier.padding(16.dp)
                        ) {
                            QRCodeComponent(sharedModel, code) { code = it }
                        }
                    }
                }
            }
        }

    }
}
    suspend fun getModule(moduleId: UUID): ModuleDTO {
        val response = RetrofitApi.api.getModule(moduleId)
        return response
    }

    suspend fun postSession(
        sessionId: UUID,
        sharedModel: SharedModel,
        startTime: String,
        endTime: String,
        sessionName: String
    ): List<SessionDTO> {
        val response = RetrofitApi.api.postSession(
            sessionDTO = SessionDTO(
                sessionId = sessionId,
                moduleId = sharedModel.moduleId!!,
                sessionName = sessionName,
                date = LocalDate.now().toString(),
                verificationCode = "",
                active = false,
                startTime = "10:00:00",
                endTime = "12:00:00"
            )
        )
        println(response)
        return response
    }

