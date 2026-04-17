package com.example.checkpoint2

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class PopularDestinationsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popular_destinations)

        val listView: ListView = findViewById(R.id.popular_destinations_list_view)
        val destinations = resources.getStringArray(R.array.popular_destinations)
        listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, destinations)
    }
}
