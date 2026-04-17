package com.example.checkpoint2

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.example.checkpoint2.data.BookingRepository
import com.example.checkpoint2.data.HotelRepository
import com.example.checkpoint2.model.Booking
import com.example.checkpoint2.util.BookingUtils
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import java.text.NumberFormat
import java.util.Calendar
import java.util.Locale

class BookingActivity : AppCompatActivity() {
    private lateinit var bookingRepository: BookingRepository

    private lateinit var hotelNameText: TextView
    private lateinit var hotelPriceText: TextView
    private lateinit var checkInButton: MaterialButton
    private lateinit var checkOutButton: MaterialButton
    private lateinit var checkInDateText: TextView
    private lateinit var checkOutDateText: TextView
    private lateinit var guestsInput: TextInputEditText
    private lateinit var totalPriceText: TextView
    private lateinit var confirmBookingButton: MaterialButton

    private var checkInDate = ""
    private var checkOutDate = ""
    private var selectedHotelId = -1
    private var pricePerNight = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)
        bindViews()

        bookingRepository = BookingRepository(this)
        selectedHotelId = intent.getIntExtra(EXTRA_HOTEL_ID, -1)

        val hotel = HotelRepository.loadHotels(this).firstOrNull { it.id == selectedHotelId }
        if (hotel == null) {
            finish()
            return
        }

        pricePerNight = hotel.pricePerNight
        val currencyFormat = NumberFormat.getCurrencyInstance(Locale("en", "PH"))

        hotelNameText.text = hotel.name
        hotelPriceText.text = getString(R.string.price_per_night, currencyFormat.format(pricePerNight))
        checkInButton.setOnClickListener { showDatePicker(isCheckIn = true) }
        checkOutButton.setOnClickListener { showDatePicker(isCheckIn = false) }
        guestsInput.doAfterTextChanged { updateTotal() }

        confirmBookingButton.setOnClickListener {
            val guests = guestsInput.text?.toString()?.toIntOrNull() ?: 0
            val nights = BookingUtils.nightsBetween(checkInDate, checkOutDate)
            val total = BookingUtils.calculateTotal(pricePerNight, nights, guests)

            if (checkInDate.isBlank() || checkOutDate.isBlank() || nights <= 0 || guests <= 0) {
                MaterialAlertDialogBuilder(this)
                    .setTitle(R.string.invalid_booking)
                    .setMessage(R.string.invalid_booking_message)
                    .setPositiveButton(R.string.ok, null)
                    .show()
                return@setOnClickListener
            }

            val booking = Booking(
                hotelId = selectedHotelId,
                checkIn = checkInDate,
                checkOut = checkOutDate,
                guests = guests,
                totalPrice = total
            )
            bookingRepository.saveBooking(booking)

            MaterialAlertDialogBuilder(this)
                .setTitle(R.string.booking_confirmed)
                .setMessage(
                    getString(
                        R.string.booking_confirmation_message,
                        hotel.name,
                        checkInDate,
                        checkOutDate,
                        guests,
                        currencyFormat.format(total)
                    )
                )
                .setPositiveButton(R.string.ok) { _, _ -> finish() }
                .setCancelable(false)
                .show()
        }

        updateTotal()
    }

    private fun bindViews() {
        hotelNameText = findViewById(R.id.hotel_name_text)
        hotelPriceText = findViewById(R.id.hotel_price_text)
        checkInButton = findViewById(R.id.check_in_button)
        checkOutButton = findViewById(R.id.check_out_button)
        checkInDateText = findViewById(R.id.check_in_date_text)
        checkOutDateText = findViewById(R.id.check_out_date_text)
        guestsInput = findViewById(R.id.guests_input)
        totalPriceText = findViewById(R.id.total_price_text)
        confirmBookingButton = findViewById(R.id.confirm_booking_button)
    }

    private fun showDatePicker(isCheckIn: Boolean) {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth, 0, 0, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                val formattedDate = BookingUtils.formatDate(calendar.timeInMillis)
                if (isCheckIn) {
                    checkInDate = formattedDate
                    checkInDateText.text = formattedDate
                } else {
                    checkOutDate = formattedDate
                    checkOutDateText.text = formattedDate
                }
                updateTotal()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun updateTotal() {
        val nights = BookingUtils.nightsBetween(checkInDate, checkOutDate)
        val guests = guestsInput.text?.toString()?.toIntOrNull() ?: 0
        val total = BookingUtils.calculateTotal(pricePerNight, nights, guests)
        val currencyFormat = NumberFormat.getCurrencyInstance(Locale("en", "PH"))
        totalPriceText.text = getString(R.string.total_price_value, currencyFormat.format(total), nights)
    }

    companion object {
        const val EXTRA_HOTEL_ID = "extra_hotel_id"
    }
}
