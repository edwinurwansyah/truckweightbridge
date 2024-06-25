package com.example.truckweightbridge.util

import java.text.SimpleDateFormat
import java.util.Calendar


fun String.toSafeInt(): Int {
    return try {
        this.toInt()
    }catch (e:Exception){
        0
    }
}

fun String.toSafeLong(): Long {
    return try {
        this.toLong()
    }catch (e:Exception){
        0L
    }
}

fun getCurrentDateTimeMillis(): Long {
    val calendar = Calendar.getInstance()
    return calendar.timeInMillis
}

fun Long.convertTimeMillisToString( format:String = "dd MMM yyyy"): String {
    val convert = SimpleDateFormat(format)
    return convert.format(this)
}
fun getCurrentTimeUsingCalendar(): Triple<Int,Int,String> {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.MINUTE, 30)
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)
    val amPm = if (calendar.get(Calendar.AM_PM) == Calendar.AM) "AM" else "PM"
    return Triple(hour, minute, amPm)
}