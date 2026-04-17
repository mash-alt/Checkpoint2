package com.example.checkpoint2.model

data class Hotel(
    val id: Int,
    val name: String,
    val location: String,
    val pricePerNight: Double,
    val rating: Double,
    val image: String,
    val description: String,
    val amenities: List<String>
)

