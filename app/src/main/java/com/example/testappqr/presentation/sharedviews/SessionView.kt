package com.example.testappqr.presentation.sharedviews

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testappqr.data.models.SessionDTO
import com.example.testappqr.utils.formatDate
import java.util.UUID

@Composable
fun SessionView(it: SessionDTO, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize()
        ) {
            Text(
                it.sessionName,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                formatDate(it.startTime) + " - " + formatDate(it.endTime),
                style = TextStyle(fontSize = 18.sp)
            )
        }

    }
}
@Preview(showBackground = true)
@Composable
fun SessionPreview(){
    SessionView(
        SessionDTO(
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
