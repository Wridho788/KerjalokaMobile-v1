package com.ciptakerjaarunika.kerjaloka.ui.Screens.SearchScreen

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.ciptakerjaarunika.kerjaloka.R

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val searchJob = findViewById<SearchView>(R.id.search_bar)
        val btn_top_search_1 = findViewById<Button>(R.id.btn_top_search)
        val btn_top_search_2 = findViewById<Button>(R.id.btn_top_search_2)
        val btn_top_search_3 = findViewById<Button>(R.id.btn_top_search_3)
        val btn_top_search_4 = findViewById<Button>(R.id.btn_top_search_4)
        val btn_top_search_5 = findViewById<Button>(R.id.btn_top_search_5)

        val resultText = findViewById<TextView>(R.id.result_search_job)

        btn_top_search_1.setOnClickListener {
            Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show()
        }

        btn_top_search_2.setOnClickListener {
            Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show()
        }

        btn_top_search_3.setOnClickListener {
            Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show()
        }

        btn_top_search_4.setOnClickListener {
            Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show()
        }

        btn_top_search_5.setOnClickListener {
            Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show()
        }



    }
}