package com.ciptakerjaarunika.kerjaloka.ui.search_job

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.size
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.databinding.ActivitySearchJobBinding
import com.google.android.material.chip.Chip
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class SearchJob : AppCompatActivity() {

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
                    isCloseIconVisible = true
                    isClickable = true
                    isCheckable = false
                    closeIconSize = 30f
                    binding.apply {
                        chipGroup.addView(chip as View)
                        chip.setOnCloseIconClickListener {
                            chipGroup.removeView(chip as View)
                        }
                    }
                }
                int++
            }
        }
        binding.searchJob.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query?.isNotEmpty() == true) {
                    newChips(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun newChips(name: String) {
        list2.add(name)
        saveArrayList(list2, "SearchJob")
        val chip = Chip(this)
        chip.setChipBackgroundColorResource(R.color.danger_100)
        chip.apply {
            textSize=12f
            text=name
            isChipIconVisible=false
            isCloseIconVisible=true
            isClickable=true
            isCheckable=false
            closeIconSize=30f
            binding.apply {
                if (chipGroup.size > 7){
                    chipGroup.removeViewAt(0)
                    chipGroup.addView(chip as View)
                }
                else {
                    chipGroup.addView(chip as View)
                }
                chip.setOnCloseIconClickListener {
                    chipGroup.removeView(chip as View)

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