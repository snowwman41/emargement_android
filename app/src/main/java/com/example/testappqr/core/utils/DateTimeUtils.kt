package com.example.testappqr.core.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun formatDate(timestamp: String): String {
    val date = Date(timestamp.toLong())
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    return format.format(date)
}
