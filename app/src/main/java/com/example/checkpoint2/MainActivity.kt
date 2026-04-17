package com.example.checkpoint2

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.checkpoint2.data.HotelRepository
import com.example.checkpoint2.data.SessionManager
import com.example.checkpoint2.databinding.ActivityMainBinding
import com.example.checkpoint2.model.Hotel
import com.example.checkpoint2.ui.HotelAdapter
import com.google.android.material.slider.RangeSlider
import com.google.android.material.slider.Slider

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var adapter: HotelAdapter

    private var allHotels: List<Hotel> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)
        if (!sessionManager.isLoggedIn()) {
            navigateToLogin()
            return
        }

        setupList()
        setupFilters()
        setupActions()
        loadHotels()
    }

    override fun onResume() {
        super.onResume()
        if (!sessionManager.isLoggedIn()) {
            navigateToLogin()
        }
    }

    private fun setupList() {
        adapter = HotelAdapter { hotel ->
            startActivity(Intent(this, HotelDetailsActivity::class.java).apply {
                putExtra(HotelDetailsActivity.EXTRA_HOTEL_ID, hotel.id)
            })
        }

        binding.hotelsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.hotelsRecyclerView.adapter = adapter
    }

    private fun setupFilters() {
        binding.destinationInput.doAfterTextChanged { applyFilters() }

        binding.priceRangeSlider.addOnChangeListener { _: RangeSlider, _, _ ->
            applyFilters()
        }

        binding.ratingSlider.addOnChangeListener { _: Slider, _, _ ->
            applyFilters()
        }
    }

    private fun setupActions() {
        binding.welcomeText.text = getString(R.string.welcome_user, sessionManager.getUsername())
        binding.popularDestinationsButton.setOnClickListener {
            startActivity(Intent(this, PopularDestinationsActivity::class.java))
        }
        binding.travelChecklistButton.setOnClickListener {
            startActivity(Intent(this, TravelChecklistActivity::class.java))
        }
        binding.stayTipsButton.setOnClickListener {
            startActivity(Intent(this, StayTipsActivity::class.java))
        }
        binding.logoutButton.setOnClickListener {
            sessionManager.logout()
            navigateToLogin()
        }
    }

    private fun loadHotels() {
        allHotels = HotelRepository.loadHotels(this)
        if (allHotels.isEmpty()) {
            adapter.submitList(emptyList())
            return
        }

        val minPrice = allHotels.minOf { it.pricePerNight }.toFloat()
        val maxPrice = allHotels.maxOf { it.pricePerNight }.toFloat()
        binding.priceRangeSlider.valueFrom = minPrice
        binding.priceRangeSlider.valueTo = maxPrice
        binding.priceRangeSlider.values = listOf(minPrice, maxPrice)
        applyFilters()
    }

    private fun applyFilters() {
        val query = binding.destinationInput.text?.toString()?.trim()?.lowercase().orEmpty()
        val priceValues = binding.priceRangeSlider.values
        val minPrice = priceValues.getOrNull(0) ?: 0f
        val maxPrice = priceValues.getOrNull(1) ?: Float.MAX_VALUE
        val minRating = binding.ratingSlider.value

        val filtered = allHotels.filter { hotel ->
            val matchesQuery = query.isBlank() ||
                hotel.location.lowercase().contains(query) ||
                hotel.name.lowercase().contains(query)
            val matchesPrice = hotel.pricePerNight in minPrice.toDouble()..maxPrice.toDouble()
            val matchesRating = hotel.rating >= minRating
            matchesQuery && matchesPrice && matchesRating
        }

        binding.resultCountText.text = getString(R.string.hotels_found, filtered.size)
        adapter.submitList(filtered)
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
