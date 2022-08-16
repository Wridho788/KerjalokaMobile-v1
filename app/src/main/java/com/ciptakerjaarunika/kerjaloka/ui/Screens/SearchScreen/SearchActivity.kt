package com.ciptakerjaarunika.kerjaloka.ui.Screens.SearchScreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import com.ciptakerjaarunika.kerjaloka.R

class SearchActivity : AppCompatActivity() {
    lateinit var searchJob: SearchView
    lateinit var listResultJob: ListView
    lateinit var list: ArrayList<String>
    lateinit var adapter: ArrayAdapter<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val searchJob = findViewById<SearchView>(R.id.search_bar)
        val listResultJob = findViewById<ListView>(R.id.listView)
        val resultText = findViewById<TextView>(R.id.result_search_job)
        val remove_history = findViewById<TextView>(R.id.btn_remove_latest_search)

        list = ArrayList()
        list.add("Apple")
        list.add("Banana")
        list.add("Pineapple")
        list.add("Orange")
        list.add("Mango")
        list.add("Grapes")
        list.add("Lemon")
        list.add("Melon")
        list.add("Watermelon")
        list.add("Papaya")

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
        listResultJob.adapter = adapter
        searchJob.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (list.contains(query)) {
                    adapter.filter.filter(query)
                } else {
                    Toast.makeText(this@SearchActivity, "Job Not Found", Toast.LENGTH_SHORT).show()
                }
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.getFilter().filter(newText)
                return false
            }
        })


        remove_history.setOnClickListener {
            list.clear()
            adapter.notifyDataSetChanged()
            Toast.makeText(this@SearchActivity, "History Removed", Toast.LENGTH_SHORT).show()
        }

    }
}