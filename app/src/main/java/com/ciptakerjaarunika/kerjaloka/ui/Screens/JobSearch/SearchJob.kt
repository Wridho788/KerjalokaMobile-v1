package com.ciptakerjaarunika.kerjaloka.ui.Screens.JobSearch

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.databinding.ActivitySearchJobBinding
import com.ciptakerjaarunika.kerjaloka.ui.HomePage.Model.rJobModel
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobSearch.Adapter.SearchJobAdapter
import com.google.android.material.chip.Chip
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class  SearchJob : AppCompatActivity() {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<SearchJobAdapter.ViewHolder>? = null

    var list = ArrayList<SearchModel>()
    var list2 = ArrayList<String>()
    private lateinit var binding: ActivitySearchJobBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchJobBinding.inflate(layoutInflater)
        setContentView(binding.root)
        list2 = getArrayList("SearchJob")
        if(list2.isNotEmpty()){
            var int = 0
            list2.forEach {
                val chip = Chip(this)
                chip.setChipBackgroundColorResource(R.color.danger_100)
                chip.apply {
                    textSize = 12f
                    text = it
                    id = int
                    isChipIconVisible = false
                    isCloseIconVisible = false
                    isClickable = true
                    isCheckable = false
                    binding.apply {
                        chipGroup.addView(chip as View)
                    }
                }
                val chipTop = Chip(this)
                chipTop.setChipBackgroundColorResource(R.color.danger_100)
                chipTop.apply {
                    textSize = 12f
                    text = it
                    id = int
                    isChipIconVisible = false
                    isCloseIconVisible = false
                    isClickable = true
                    isCheckable = false
                    binding.apply {
                        chipGroupTopSearch.addView(chipTop as View)
                    }
                }
                int++
            }
        }
        else{
            binding.historyChips.isVisible=false
        }
        binding.searchJob.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query?.isNotEmpty() == true) {
                    newChips(query)
                }
                binding.searchResult.isVisible=true
                binding.history.isVisible=false
                binding.query.text=query
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText!!.isBlank()){
                    binding.searchResult.isVisible=false
                    binding.history.isVisible=true
                }
                return true
            }
        })

        binding.removeHistory.setOnClickListener(){
            removeArrayList(list2,"SearchJob")
            binding.chipGroup.removeAllViews()
        }

        val list = ArrayList<rJobModel>()
        val rJob1 = rJobModel(
            1,
            1,
            "Software Engineer",
            "Medan",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png",
            "Jakarta",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png",
            "Kerjaloka",
            "satu jam lalu"
        )
        val rJob2 = rJobModel(
            1,
            1,
            "Software Engineer",
            "Medan",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png",
            "Jakarta",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png",
            "Kerjaloka",
            "satu jam lalu"
        )
        val rJob3 = rJobModel(
            1,
            1,
            "Software Engineer",
            "Medan",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png",
            "Jakarta",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png",
            "Kerjaloka",
            "satu jam lalu"
        )
        val rJob4 = rJobModel(
            1,
            1,
            "Software Engineer",
            "Medan",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png",
            "Jakarta",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png",
            "Kerjaloka",
            "satu jam lalu"
        )
        val rJob5 = rJobModel(
            1,
            1,
            "Software Engineer",
            "Medan",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png",
            "Jakarta",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png",
            "Kerjaloka",
            "satu jam lalu"
        )

        list.add(rJob1)
        list.add(rJob2)
        list.add(rJob3)
        list.add(rJob4)
        list.add(rJob5)
        layoutManager = LinearLayoutManager(this)
        binding.jobs.layoutManager = layoutManager
        adapter = SearchJobAdapter(list)
        binding.jobs.adapter = adapter
    }

    private fun newChips(name: String) {
        binding.historyChips.isVisible=true
        list2.add(name)
        saveArrayList(list2, "SearchJob")
        val chip = Chip(this)
        chip.setChipBackgroundColorResource(R.color.danger_100)
        chip.apply {
            textSize=12f
            text=name
            isChipIconVisible=false
            isCloseIconVisible=false
            isClickable=true
            isCheckable=false
            binding.apply {
                if (chipGroup.size > 7){
                    chipGroup.removeViewAt(0)
                    chipGroup.addView(chip as View)
                }
                else {
                    chipGroup.addView(chip as View)
                }
            }
        }
    }

    fun saveArrayList(list: ArrayList<String>, key: String?) {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor: SharedPreferences.Editor = prefs.edit()
        val gson = Gson()
        val json: String = gson.toJson(list)
        editor.putString(key, json)
        editor.apply()
    }

    fun getArrayList(key: String?): ArrayList<String> {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val gson = Gson()
        val json: String? = prefs.getString(key, null)
        val type: Type = object : TypeToken<ArrayList<String?>?>() {}.getType()
        var listnull = ArrayList<String>()
        if(json == null)
        {
            return listnull
        }
        return gson.fromJson(json, type)
    }

    fun removeArrayList(list: ArrayList<String>, key: String?) {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor: SharedPreferences.Editor = prefs.edit()
        val gson = Gson()
        val json: String = gson.toJson(list)
        editor.remove(key)
        editor.apply()
    }
}