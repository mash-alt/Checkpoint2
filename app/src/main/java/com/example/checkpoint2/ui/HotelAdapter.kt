package com.example.checkpoint2.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.checkpoint2.R
import com.example.checkpoint2.model.Hotel
import java.text.NumberFormat
import java.util.Locale

class HotelAdapter(
    private val onClick: (Hotel) -> Unit
) : RecyclerView.Adapter<HotelAdapter.HotelViewHolder>() {

    private val currencyFormat = NumberFormat.getCurrencyInstance(Locale("en", "PH"))
    private val items = mutableListOf<Hotel>()

    fun submitList(hotels: List<Hotel>) {
        items.clear()
        items.addAll(hotels)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_hotel_card, parent, false)
        return HotelViewHolder(view)
    }

    override fun onBindViewHolder(holder: HotelViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class HotelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image = itemView.findViewById<ImageView>(R.id.hotel_image)
        private val name = itemView.findViewById<TextView>(R.id.hotel_name)
        private val location = itemView.findViewById<TextView>(R.id.hotel_location)
        private val price = itemView.findViewById<TextView>(R.id.hotel_price)
        private val rating = itemView.findViewById<TextView>(R.id.hotel_rating)

        fun bind(hotel: Hotel) {
            val context = itemView.context
            val imageId = context.resources.getIdentifier(hotel.image, "drawable", context.packageName)
            image.setImageResource(if (imageId != 0) imageId else R.drawable.hotel_placeholder)
            name.text = hotel.name
            location.text = hotel.location
            price.text = context.getString(R.string.hotel_card_price, currencyFormat.format(hotel.pricePerNight))
            rating.text = context.getString(R.string.hotel_card_rating, hotel.rating)
            itemView.setOnClickListener { onClick(hotel) }
        }
    }
}
