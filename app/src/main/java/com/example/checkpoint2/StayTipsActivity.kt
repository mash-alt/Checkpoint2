package com.example.checkpoint2

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class StayTipsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stay_tips)

        val listView: ListView = findViewById(R.id.stay_tips_list_view)
        val tips = resources.getStringArray(R.array.stay_tips_items)
        listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, tips)
    }
}
