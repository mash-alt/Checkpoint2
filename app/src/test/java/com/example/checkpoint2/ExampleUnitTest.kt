package com.example.checkpoint2

import com.example.checkpoint2.util.BookingUtils
import org.junit.Assert.assertEquals
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun nightsBetween_returnsExpectedDays() {
        assertEquals(3, BookingUtils.nightsBetween("2026-04-20", "2026-04-23"))
    }

    @Test
    fun calculateTotal_usesNightsAndGuests() {
        val total = BookingUtils.calculateTotal(pricePerNight = 3200.0, nights = 2, guests = 3)
        assertEquals(19200.0, total, 0.0)
    }
}