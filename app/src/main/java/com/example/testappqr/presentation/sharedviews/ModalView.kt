package com.example.testappqr.presentation.sharedviews

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalView(onDismissRequest: () -> Unit,onAddRequest: (() -> Unit)? = null,  showButtons: Boolean= false, content: @Composable () -> Unit) {
    BasicAlertDialog(onDismissRequest = onDismissRequest) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.padding(16.dp)
        ) {
            Column {
                content()
                if (showButtons) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        BasicButton(
                            onClick = onDismissRequest,
                            text = "Cancel",
                            textSize = 16.sp
                        )
                        BasicButton(
                            onClick = { if (onAddRequest != null) (onAddRequest()) },
                            text = "Add", textSize = 16.sp
                        )
                    }
                }
            }
        }

    }
}