package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.CompanySearchAPI
import com.ciptakerjaarunika.kerjaloka.databinding.ActivityCompanySearchBinding
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Adapter.CompanySearchAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Bottomsheet.FilterCompany
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Model.search_company_model
import com.google.android.material.chip.Chip
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class CompanySearchActivity : AppCompatActivity() {
    private var list: List<search_company_model>?= null

    var list_latest_search_company = ArrayList<String>()

    private lateinit var binding: ActivityCompanySearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompanySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val thisActivity = this
        (thisActivity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (thisActivity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.btnBack.setOnClickListener {
            thisActivity.onBackPressed()
        }

        list_latest_search_company = getArrayList("SearchCompanyJob")
        if (list_latest_search_company.isNotEmpty()) {
            var int = 0
            list_latest_search_company.forEach {
                val chip = Chip(this)
                chip.setChipBackgroundColorResource(R.color.danger_100)
                chip.apply {
                    textSize = 12f
                    text = it
                    id = int
                    isChipIconVisible = false
                    isCloseIconVisible = false
                    isClickable = true
                    isCheckable = true
                    binding.apply {
                        latestResultGrup.addView(chip as View)
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
                    isCheckable = true
                    binding.apply {
                        chipGroupTopSearch.addView(chipTop as View)
                    }
                }
                int++
            }
        } else {
            binding.layoutLatestSearchResults.isVisible = true
            binding.layoutResultSearch.isVisible = false
            binding.layoutTopSearchResults.isVisible = true
        }

        binding.btnRemoveLatestSearch.setOnClickListener{
            removeArrayList(list_latest_search_company, "SearchCompanyJob")
            binding.latestResultGrup.removeAllViews()
        }

        binding.btnFilter.setOnClickListener{
//            Toast.makeText(this, "Filter", Toast.LENGTH_SHORT).show()
            val sheet = FilterCompany()
            thisActivity.let { it1 -> sheet.show(it1.supportFragmentManager, "FilterCompany") }
        }

        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            val context: Context = thisActivity
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query?.isNotEmpty() == true) {
                    CompanySearchAPI().CompanyGetSearchCompany(context, query) {
                        Log.d("response search company", it.toString())
                        if (it != null) {
                            list = it.data
                            Log.d("response sukses", it.data.toString())
                            binding.searchCompanyJob.apply {
                                layoutManager = LinearLayoutManager(context)
                                adapter = CompanySearchAdapter(list!!, context)
                            }
                        }
                    }
                    newChips(query)

                }
                binding.layoutTopSearchResults.isVisible = false
                binding.layoutResultSearch.isVisible = true
                binding.layoutLatestSearchResults.isVisible = false
                binding.resultSearchJob.text = query
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText!!.isEmpty()) {
                    binding.layoutLatestSearchResults.isVisible = false
                    binding.layoutTopSearchResults.isVisible = false
                    binding.layoutResultSearch.isVisible = true
                } else {
                    binding.layoutLatestSearchResults.isVisible = true
                    binding.layoutTopSearchResults.isVisible = true
                    binding.layoutResultSearch.isVisible = false

                }

                return true
            }
        })
    }
    private fun newChips(keyword: String) {
        binding.latestResultGrup.isVisible = true
        list_latest_search_company.add(keyword)
        saveArrayList(list_latest_search_company, "SearchJob")
        val chip = Chip(this)
        chip.setChipBackgroundColorResource(R.color.danger_100)
        chip.apply {
            textSize = 12f
            text = keyword
            isChipIconVisible = false
            isCloseIconVisible = false
            isClickable = true
            isCheckable = false
            binding.apply {
                if (latestResultGrup.size > 7) {
                    latestResultGrup.removeViewAt(0)
                    latestResultGrup.addView(chip as View)
                } else {
                    latestResultGrup.addView(chip as View)
                }
            }
        }

    }

    fun getArrayList(key: String?): ArrayList<String> {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val gson = Gson()
        val json: String? = prefs.getString(key, null)
        val type: Type = object : TypeToken<ArrayList<String?>?>() {}.type
        var listnull = ArrayList<String>()
        if (json == null) {
            return listnull
        }
        return gson.fromJson(json, type)
    }
    fun saveArrayList(list: ArrayList<String>, keyword: String?) {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor: SharedPreferences.Editor = prefs.edit()
        val gson = Gson()
        val json: String = gson.toJson(list)
        editor.putString(keyword, json)
        editor.apply()
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