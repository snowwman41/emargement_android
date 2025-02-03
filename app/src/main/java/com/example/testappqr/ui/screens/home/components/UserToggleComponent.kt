package com.example.testappqr.ui.screens.home.components

import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun UserSwitchComponent(isStudent : Boolean , onSwitchChange : (Boolean) -> Unit) {
    Column {
        if (isStudent) Text("Student") else Text("Professor")
        Switch(
            checked = isStudent,
            onCheckedChange = { onSwitchChange(it) }
        )
    }

}