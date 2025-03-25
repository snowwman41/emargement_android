package com.example.testappqr.presentation.sharedviews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testappqr.models.SessionDTO
import com.example.testappqr.models.SessionLazyDTO
import com.example.testappqr.utils.formatDate
import com.example.testappqr.utils.formatTime

@Composable
fun SessionViewHeader(session: SessionDTO) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = session.sessionName, style = TextStyle(fontSize = 24.sp))
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Date: ${formatDate(session.startTime)}",
                style = TextStyle(fontSize = 18.sp)
            )
            Text(
                text = "Hour: ${formatTime(session.startTime)} - ${formatTime(session.endTime)}",
                style = TextStyle(fontSize = 18.sp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .align(Alignment.End)
                    .background(
                        if (session.active) Color.Green else Color.Red,
                        RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = if (session.active) "✅ Open" else "❌ Closed",
                    style = TextStyle(fontSize = 18.sp)
                )
            }
        }
    }
}