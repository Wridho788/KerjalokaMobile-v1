package com.ciptakerjaarunika.kerjaloka.ui.Screens.SearchScreen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.ciptakerjaarunika.kerjaloka.R

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val searchJob = findViewById<SearchView>(R.id.search_bar)
//



    }
}