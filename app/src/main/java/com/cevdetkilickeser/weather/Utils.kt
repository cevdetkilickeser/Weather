package com.cevdetkilickeser.weather

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


object TimestampToDayOfWeekConverter {

    @JvmStatic
    fun convert(timestamp: Long): String {
        val dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault())
        val day = dateTime.dayOfWeek.toString().substring(0,1) + dateTime.dayOfWeek.toString().substring(1,3).lowercase()
        return day
    }

    @JvmStatic
    fun convert(date: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val dateTime = LocalDateTime.parse(date, formatter)
        return dateTime.plusHours(3).format(formatter)

    }
}

object HourTaker {

    @JvmStatic
    fun takeHour(date: String): String {
        val dateLocal = TimestampToDayOfWeekConverter.convert(date)
        val array = dateLocal.split(" ").toTypedArray()
        val hour = array[1].substring(0,5)
        return hour
    }
}
