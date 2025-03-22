package com.example.testappqr.presentation.professor.views

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

import com.example.testappqr.presentation.professor.viewmodels.AddSessionVM
import com.example.testappqr.presentation.sharedviews.ModalView
import java.util.Calendar

@Composable
fun AddSessionView(onDismissRequest: () -> Unit, viewModel: AddSessionVM = hiltViewModel()) {
    val sessionState by viewModel.addSessionState.collectAsStateWithLifecycle()
    ModalView(onDismissRequest = onDismissRequest,
        onAddRequest = {
            onDismissRequest()
            viewModel.addSession()
        }, showButtons = true) {
        Column(
            modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Enter a new Session")
            TextField(label = { Text("Session name") },
                value = sessionState.sessionName,
                onValueChange = { viewModel.onChangeSessionName(it) })

            DatePickerField("Date", sessionState.date) {
                viewModel.onChangeDate(it)
            }
            TimePickerField("Start Time", sessionState.startTime) {
                viewModel.onChangeStartTime(it)
            }
            TimePickerField("End Time", sessionState.endTime) {
                viewModel.onChangeEndTime(it)
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
        context, { _, selectedHour, selectedMinute ->
            val formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
            onTimeSelected(formattedTime)
        }, hour, minute, true
    )

    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable { timePickerDialog.show() }) {
        Text(
            "$label: $time", style = TextStyle(fontSize = 18.sp), modifier = Modifier.padding(8.dp)
        )
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

    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable { datePickerDialog.show() }) {
        Text(
            "$label: $date", style = TextStyle(fontSize = 18.sp), modifier = Modifier.padding(8.dp)
        )
    }
}

