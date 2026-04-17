package com.example.checkpoint2.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

object BookingUtils {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    fun parseDate(value: String): Date? {
        return try {
            dateFormat.parse(value)
        } catch (_: ParseException) {
            null
        }
    }

    fun formatDate(timestamp: Long): String = dateFormat.format(Date(timestamp))

    fun nightsBetween(checkIn: String, checkOut: String): Int {
        val inDate = parseDate(checkIn) ?: return 0
        val outDate = parseDate(checkOut) ?: return 0
        val diffMillis = outDate.time - inDate.time
        if (diffMillis <= 0L) return 0
        return TimeUnit.MILLISECONDS.toDays(diffMillis).toInt()
    }

    fun calculateTotal(pricePerNight: Double, nights: Int, guests: Int): Double {
        if (nights <= 0 || guests <= 0) return 0.0
        return pricePerNight * nights * guests
    }
}

