package com.example.testappqr.presentation.sharedviews


import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp


@Composable
fun BasicButton(
    onClick: () -> Unit,
    text: String,
    isEnabled: Boolean = true,
    textSize: TextUnit = 20.sp,
    color: Color = MaterialTheme.colorScheme.secondary,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick, enabled = isEnabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = color
        ),
        modifier = modifier
    ) { Text(text, style = TextStyle(fontSize = textSize)) }
}