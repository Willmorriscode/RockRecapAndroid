package com.rockrecap.utilities

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

// Will format the amount of time logged on a route
fun formatRouteTime(time: Int): String {
    val days = time / 1440 // 1440 minutes in a day
    val remainingMinutesAfterDays = time % 1440
    val hours = remainingMinutesAfterDays / 60 // 60 minutes in an hour
    val minutes = remainingMinutesAfterDays % 60

    return buildString {
        if (days > 0) {
            append("${days}d ")
        }
        if (hours > 0) {
            append("${hours}h ")
        }
        if (minutes > 0 || (days == 0 && hours == 0)) {
            append("${minutes}m")
        }
    }.trim() // Remove any trailing spaces
}

// Will format the date logged on a route
fun formatRouteDate(timestamp: Long, option: Int): String {
    val formatter = when (option) {
        1 -> SimpleDateFormat("MMMM d, yyyy", Locale.getDefault())
        2 -> SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        else -> throw IllegalArgumentException("Invalid option: $option")
    }
    return formatter.format(Date(timestamp))
}

fun formatCompletionFloat(completionRate: Float): String{
    if(completionRate.toDouble() == 0.0){
        return "0%"
    }
    else{
        return floatToPercentage(completionRate) // formats the result to be a string in '##.#%' format
    }
}

@SuppressLint("DefaultLocale")
fun floatToPercentage(floatValue: Float): String {
    val percentage = floatValue * 100
    return if (percentage % 1 == 0f) {
        String.format("%.0f%%", percentage)  // No decimals if it's a whole number
    } else {
        String.format("%.2f%%", percentage)  // Keep 2 decimal places otherwise
    }
}

