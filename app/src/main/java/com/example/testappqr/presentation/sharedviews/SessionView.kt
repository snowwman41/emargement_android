package com.example.testappqr.presentation.sharedviews

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testappqr.data.models.SessionLazyDTO
import com.example.testappqr.utils.formatDate
import com.example.testappqr.utils.formatTime
import java.util.UUID
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color


@Composable
fun SessionView(it: SessionLazyDTO, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth().padding(6.dp)
    ) {
        Row (
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(10.dp).fillMaxWidth()
        ) {
            Column {
                Text(
                    it.sessionName,
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    "UE",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                )
            }
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Text(
                    formatDate(it.startTime),
                    style = TextStyle(fontSize = 16.sp)
                )
                Text(
                    "${formatTime(it.startTime)} - ${formatTime(it.endTime)}",
                    style = TextStyle(fontSize = 16.sp)
                )
            }
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(if (it.active) Color.Green else Color.Red, shape = CircleShape)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SessionPreview(){
    SessionView(
        SessionLazyDTO(
            sessionId = UUID.randomUUID(),
            moduleId = UUID.randomUUID(),
            sessionName = "Session 1",
            verificationCode = "",
            active = false,
            startTime = 1740222636,
            endTime = 1740222636

        )
    )
}
