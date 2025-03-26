package com.example.testappqr.presentation.sharedviews

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.testappqr.models.ModuleLazyDTO
import java.util.UUID

@Composable
fun ModuleView(module: ModuleLazyDTO, modifier: Modifier) {
    Card(modifier = modifier) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)) {
            Text(
                module.moduleName!!,
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ModulePreview() {
    ModuleView(
        module = ModuleLazyDTO(
            UUID.randomUUID(),
            "Module 1", UUID.randomUUID()
        ),
        modifier = Modifier.fillMaxWidth()
    )
}