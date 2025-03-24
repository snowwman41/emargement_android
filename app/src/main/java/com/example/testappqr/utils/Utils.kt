package com.example.testappqr.utils

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale

import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

fun convertToTimestamp(dateStr: String, timeStr: String): Long {
    // Parse the date and time
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    val date = java.time.LocalDate.parse(dateStr, dateFormatter)
    val time = java.time.LocalTime.parse(timeStr, timeFormatter)

    // Combine into LocalDateTime
    val dateTime = LocalDateTime.of(date, time)

    // Convert to timestamp (milliseconds since epoch)
    return dateTime.toInstant(ZoneOffset.UTC).epochSecond
}
fun formatTime(timestamp: Long): String {
    val date = Date(timestamp)
    val format = SimpleDateFormat("HH:mm", Locale.getDefault())
    return format.format(date)
}

fun formatDate(timestamp: Long): String {
    val date = Date(timestamp)
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return format.format(date)
}

fun formatTodaysDate(): String {
    val date = Date()
    val formatter = SimpleDateFormat("ddMMyyyy")

    return formatter.format(date)
}
