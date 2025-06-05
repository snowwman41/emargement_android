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

@Composable
fun SignatureView(it: SessionLazyDTO, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(6.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Text(
                it.sessionName,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Column(
                modifier = Modifier
                    .padding(10.dp)

            ) {
                Text(
                    formatDate(it.startTime),
                    style = TextStyle(fontSize = 18.sp)
                )
                Text(
                    formatTime(it.startTime) + " - " + formatTime(it.endTime),
                    style = TextStyle(fontSize = 18.sp)
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun SignaturePreview() {
    SignatureView(
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
