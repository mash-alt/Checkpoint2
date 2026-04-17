package com.example.checkpoint2.data

import android.content.Context
import com.example.checkpoint2.model.Booking
import org.json.JSONArray
import org.json.JSONObject

class BookingRepository(context: Context) {
    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveBooking(booking: Booking) {
        val array = JSONArray(prefs.getString(KEY_BOOKINGS, "[]"))
        array.put(
            JSONObject().apply {
                put("hotelId", booking.hotelId)
                put("checkIn", booking.checkIn)
                put("checkOut", booking.checkOut)
                put("guests", booking.guests)
                put("totalPrice", booking.totalPrice)
            }
        )
        prefs.edit().putString(KEY_BOOKINGS, array.toString()).apply()
    }

    fun getBookings(): List<Booking> {
        val array = JSONArray(prefs.getString(KEY_BOOKINGS, "[]"))
        val bookings = mutableListOf<Booking>()
        for (i in 0 until array.length()) {
            val obj = array.getJSONObject(i)
            bookings += Booking(
                hotelId = obj.getInt("hotelId"),
                checkIn = obj.getString("checkIn"),
                checkOut = obj.getString("checkOut"),
                guests = obj.getInt("guests"),
                totalPrice = obj.getDouble("totalPrice")
            )
        }
        return bookings
    }

    companion object {
        private const val PREFS_NAME = "hotel_bookings"
        private const val KEY_BOOKINGS = "bookings"
    }
}

