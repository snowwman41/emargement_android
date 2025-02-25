package com.example.testappqr.presentation.module

import android.app.DatePickerDialog
import android.app.TimePickerDialog
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

import kotlinx.coroutines.launch
import com.example.testappqr.SharedModel
import com.example.testappqr.core.network.RetrofitApi
import com.example.testappqr.data.models.ModuleDTO
import com.example.testappqr.data.models.SessionDTO
import com.example.testappqr.presentation.sharedcomponents.BasicButton
import com.example.testappqr.presentation.sharedcomponents.NavigationScreen
import java.time.LocalDate
import java.util.UUID

import androidx.compose.ui.platform.LocalContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModuleScreen(navController: NavHostController, sharedModel: SharedModel) {
    var showModal by remember { mutableStateOf(false) }
    var qrCodeModal by remember { mutableStateOf(false) }
    var sessionName by remember { mutableStateOf("") }
    var date by remember { mutableStateOf(LocalDate.now().toString()) }
    var startTime by remember { mutableStateOf("00:00") }
    var endTime by remember { mutableStateOf("00:00") }
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
                                        // Text(
                                            // "Verification code : " + it.verificationCode,
                                            // style = TextStyle(fontSize = 18.sp)
                                        // )
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
                                TextField(label = { Text("Session name") },
                                    value = sessionName,
                                    onValueChange = { sessionName = it })
                                DatePickerField("Date", date) {
                                    date = it
                                }

                                TimePickerField("Start Time", startTime) {
                                    startTime = it
                                }
                                TimePickerField("End Time", endTime) {
                                    endTime = it
                                }

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
                                                date = date,
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
                            QRCodeView(sharedModel, code) { code = it }
                        }
                    }
                }
            }
        }

    }
}
@Composable
fun TimePickerField(label: String, time: String, onTimeSelected: (String) -> Unit) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = (calendar.get(Calendar.MINUTE) / 15) * 15
    val timePickerDialog = TimePickerDialog(
        context,
        { _, selectedHour, selectedMinute ->
            val formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
            onTimeSelected(formattedTime)
        },
        hour, minute, true
    )

    Row(modifier = Modifier.fillMaxWidth().clickable { timePickerDialog.show() }) {
        Text("$label: $time", style = TextStyle(fontSize = 18.sp), modifier = Modifier.padding(8.dp))
    }
}

@Composable
fun DatePickerField(label: String, date: String, onDateSelected: (String) -> Unit) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val formattedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
            onDateSelected(formattedDate)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Row(modifier = Modifier.fillMaxWidth().clickable { datePickerDialog.show() }) {
        Text("$label: $date", style = TextStyle(fontSize = 18.sp), modifier = Modifier.padding(8.dp))
    }
}

    suspend fun getModule(moduleId: UUID): ModuleDTO {
        val response = RetrofitApi.api.getModule(moduleId)
        return response
    }

    suspend fun postSession(
        sessionId: UUID,
        sharedModel: SharedModel,
        date: String,
        startTime: String,
        endTime: String,
        sessionName: String
    ): List<SessionDTO> {
        val timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        val response = RetrofitApi.api.postSession(
            sessionDTO = SessionDTO(
                sessionId = sessionId,
                moduleId = sharedModel.moduleId!!,
                sessionName = sessionName,
                date = date,
                verificationCode = "",
                active = false,
                startTime = "$startTime:00",
                endTime = "$endTime:00",
                timestamp = timestamp
            )
        )
        println(response)
        return response
    }

