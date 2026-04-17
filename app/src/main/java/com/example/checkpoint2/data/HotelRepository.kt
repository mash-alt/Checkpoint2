package com.example.checkpoint2.data

import android.content.Context
import com.example.checkpoint2.model.Hotel
import org.json.JSONArray

object HotelRepository {
    fun loadHotels(context: Context): List<Hotel> {
        val rawJson = context.assets.open("hotels.json").bufferedReader().use { it.readText() }
        val array = JSONArray(rawJson)
        val hotels = mutableListOf<Hotel>()
        for (i in 0 until array.length()) {
            val obj = array.getJSONObject(i)
            val amenitiesArray = obj.getJSONArray("amenities")
            val amenities = MutableList(amenitiesArray.length()) { index ->
                amenitiesArray.getString(index)
            }
            hotels += Hotel(
                id = obj.getInt("id"),
                name = obj.getString("name"),
                location = obj.getString("location"),
                pricePerNight = obj.getDouble("pricePerNight"),
                rating = obj.getDouble("rating"),
                image = obj.getString("image"),
                description = obj.getString("description"),
                amenities = amenities
            )
        }
        return hotels
    }
}

