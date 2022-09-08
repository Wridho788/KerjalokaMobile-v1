package com.ciptakerjaarunika.kerjaloka.ui.Screens.SearchScreen

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.Search_Api
import com.ciptakerjaarunika.kerjaloka.databinding.ActivitySearchBinding
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobDetailScreen.JobDetailFragment
import com.ciptakerjaarunika.kerjaloka.ui.Screens.SearchScreen.Adapter.SearchCompanyAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.SearchScreen.Adapter.SearchJobAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.SearchScreen.Model.general_search_model
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class SearchActivity : AppCompatActivity(), onFragmentTransactionList,
    onFragmentTransactionListCompany {
    private var list: general_search_model? = null

    var list_Latest_search= ArrayList<String>()
    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val searchBar = findViewById<SearchView>(R.id.search_bar)
        val recyclerView = findViewById<RecyclerView>(R.id.searchResult)
        val recyclerView2 = findViewById<RecyclerView>(R.id.searchCompany)
        val btn_see_more_job = findViewById<MaterialCardView>(R.id.see_more_job)
        val btn_see_more_company = findViewById<MaterialCardView>(R.id.see_more_company)
        val thisActivity = this
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        val latest_result_grup = findViewById<ChipGroup>(R.id.latest_result_grup)
        val latest_top_grup = findViewById<ChipGroup>(R.id.chipGroupTopSearch)

        (thisActivity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (thisActivity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)

        val btn_back = findViewById<ImageView>(R.id.btn_back)
        btn_back.setOnClickListener {
            thisActivity.onBackPressed()
        }

        list_Latest_search = getArrayList("SearchJob")
        if (list_Latest_search.isNotEmpty()) {
            var int = 0
            list_Latest_search.forEach {
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
                    isCheckable = false
//                    binding.apply {
//                        chipGroupTopSearch.addView(chipTop as View)
//                    }
                }
                int++
            }
        } else {
            binding.layoutLatestSearchResults.isVisible = true
            binding.layoutResultSearch.isVisible = false
            binding.layoutTopSearchResults.isVisible = true
        }
        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            val context: Context = thisActivity
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query?.isNotEmpty() == true) {
                    Search_Api().getGeneralSearchAsync(context, query) {
                        Log.d("response Search Api", it.toString())
                        if (it != null) {
                            list = it.data

                            if (list?.jobList?.size == 0) {
                                btn_see_more_job.visibility = GONE
                            } else btn_see_more_job.visibility = VISIBLE

                            if (list?.companyList?.size == 0) {
                                btn_see_more_company.visibility = GONE
                            } else btn_see_more_company.visibility = VISIBLE

                            Log.d("response sukses", it.data.toString())
                            recyclerView.apply {
                                layoutManager = LinearLayoutManager(context)
                                adapter = SearchJobAdapter(list!!.jobList, context, thisActivity)
                            }
                            recyclerView2.apply {
                                layoutManager = LinearLayoutManager(context)
                                adapter =
                                    SearchCompanyAdapter(list!!.companyList, context, thisActivity)
                            }
                        }
                    }
                    newChips(query)
                    latest_result_grup.setOnClickListener{
                        Toast.makeText(thisActivity,"search" + query, Toast.LENGTH_SHORT).show()
                    }
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

        binding.btnRemoveLatestSearch.setOnClickListener {
            removeArrayList(list_Latest_search, "SearchJob")
            binding.latestResultGrup.removeAllViews()
        }

    }

    private fun newChips(keyword: String) {
        binding.latestResultGrup.isVisible = true
        list_Latest_search.add(keyword)
        saveArrayList(list_Latest_search, "SearchJob")
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
                }
                Log.d("keyword", keyword)
            }
        }

    }

    fun saveArrayList(list: ArrayList<String>, keyword: String?) {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor: SharedPreferences.Editor = prefs.edit()
        val gson = Gson()
        val json: String = gson.toJson(list)
        editor.putString(keyword, json)
        editor.apply()
    }

    @SuppressLint("CommitPrefEdits")
    fun removeArrayList(list: ArrayList<String>, key: String?) {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor: SharedPreferences.Editor = prefs.edit()
        val gson = Gson()
        val json: String = gson.toJson(list)
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

    fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val ft = fragmentManager.beginTransaction()
        ft.replace(R.id.fragment_job_detail, fragment)
        ft.commit()
    }
//    override fun onFragmentClick(companyNo: Long, jobNo: Long) {
//
//    }

    override fun onFragmentTransactionListenerClick(companyNo: Long, jobNo: Long) {
    replaceFragment(JobDetailFragment(JobNo = jobNo, CompanyNo = companyNo))


    }

    override fun onFragmentCompanyDetailsClick(companyNo: Long) {
        Toast.makeText(baseContext, "companyNo $companyNo", Toast.LENGTH_SHORT).show()
    }


}

interface onFragmentTransactionList {
    fun onFragmentTransactionListenerClick(companyNo: Long, jobNo: Long)
}

interface onFragmentTransactionListCompany {
    fun onFragmentCompanyDetailsClick(companyNo: Long)
}