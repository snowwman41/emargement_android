package com.example.testappqr.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun formatDate(timestamp: Long): String {
    val date = Date(timestamp)
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    return format.format(date)
}
