package com.example.checkpoint2

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class TravelChecklistActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_travel_checklist)

        val listView: ListView = findViewById(R.id.travel_checklist_list_view)
        val checklist = resources.getStringArray(R.array.travel_checklist_items)
        listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, checklist)
    }
}
