package com.example.checkpoint2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.checkpoint2.data.HotelRepository
import com.example.checkpoint2.databinding.ActivityHotelDetailsBinding
import java.text.NumberFormat
import java.util.Locale

class HotelDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHotelDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHotelDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val hotelId = intent.getIntExtra(EXTRA_HOTEL_ID, -1)
        val hotel = HotelRepository.loadHotels(this).firstOrNull { it.id == hotelId }
        if (hotel == null) {
            finish()
            return
        }

        val currencyFormat = NumberFormat.getCurrencyInstance(Locale("en", "PH"))
        val imageId = resources.getIdentifier(hotel.image, "drawable", packageName)
        binding.bannerImage.setImageResource(if (imageId != 0) imageId else R.drawable.hotel_placeholder)
        binding.nameText.text = hotel.name
        binding.locationText.text = hotel.location
        binding.ratingText.text = getString(R.string.rating_value, hotel.rating)
        binding.priceText.text = getString(R.string.price_per_night, currencyFormat.format(hotel.pricePerNight))
        binding.descriptionText.text = hotel.description
        binding.amenitiesText.text = hotel.amenities.joinToString(" • ")

        binding.bookNowButton.setOnClickListener {
            startActivity(Intent(this, BookingActivity::class.java).apply {
                putExtra(BookingActivity.EXTRA_HOTEL_ID, hotel.id)
            })
        }
    }

    companion object {
        const val EXTRA_HOTEL_ID = "extra_hotel_id"
    }
}

