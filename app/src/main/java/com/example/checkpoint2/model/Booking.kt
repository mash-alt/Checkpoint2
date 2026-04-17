package com.example.checkpoint2.model

data class Booking(
    val hotelId: Int,
    val checkIn: String,
    val checkOut: String,
    val guests: Int,
    val totalPrice: Double
)

