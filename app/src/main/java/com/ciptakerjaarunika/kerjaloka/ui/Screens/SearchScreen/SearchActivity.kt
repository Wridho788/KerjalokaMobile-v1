package com.ciptakerjaarunika.kerjaloka.ui.Screens.SearchScreen

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
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
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyDetail.CompanyDetailFragment
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

class SearchActivity : Fragment(), onFragmentTransactionList,
    onFragmentTransactionListCompany {
    private var list: general_search_model? = null

    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(SessionManager(context).latestGeneralSearch == null){
            SessionManager(context).latestGeneralSearch = listOf()
        }

        binding = ActivitySearchBinding.inflate(layoutInflater)

        binding.btnBack.setOnClickListener {
            activity?.onBackPressed()
        }
        var listSearch = SessionManager(context).latestGeneralSearch?.reversed()
        if (listSearch?.isNotEmpty() == true) {
            var int = 0
            listSearch.forEach {
                val chip = Chip(context)
                chip.setChipBackgroundColorResource(R.color.danger_100)
                chip.apply {
                    textSize = 12f
                    text = it.toString()
                    id = int
                    isChipIconVisible = false
                    isCloseIconVisible = false
                    isClickable = true
                    isCheckable = false
                    binding.apply {
                        latestResultGrup.addView(chip as View)
                    }
                }
                val chipTop = Chip(context)
                chipTop.setChipBackgroundColorResource(R.color.danger_100)
                chipTop.apply {
                    textSize = 12f
                    text = it.toString()
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
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query?.isNotEmpty() == true) {
                    Search_Api().getGeneralSearchAsync(context, query) {
                        Log.d("response Search Api", it.toString())
                        if (it != null) {
                            list = it.data

                            if (list?.jobList?.size!! < 5) {
                                binding.seeMoreJob.visibility = GONE
                            } else binding.seeMoreJob.visibility = VISIBLE

                            if (list?.companyList?.size!! < 5) {
                                binding.seeMoreCompany.visibility = GONE
                            } else binding.seeMoreCompany.visibility = VISIBLE

                            binding.recycleJob.apply {
                                layoutManager = LinearLayoutManager(context)
                                adapter = SearchJobAdapter(list!!.jobList, context, this@SearchActivity)
                            }
                            binding.recycleCompany.apply {
                                layoutManager = LinearLayoutManager(context)
                                adapter = SearchCompanyAdapter(list!!.companyList, context, this@SearchActivity)
                            }
                        }
                    }
                    newChips(query)
                    binding.latestResultGrup.setOnClickListener{
                        Toast.makeText(context,"search" + query, Toast.LENGTH_SHORT).show()
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
            SessionManager(context).latestGeneralSearch = listOf()
            binding.latestResultGrup.removeAllViews()
        }

        return binding.root
    }


    private fun newChips(keyword: String) {
        binding.latestResultGrup.isVisible = true
        if(SessionManager(context).latestGeneralSearch?.size == 0 ||  SessionManager(context).latestGeneralSearch?.last() != keyword) {
            SessionManager(context).latestGeneralSearch = SessionManager(context).latestGeneralSearch?.plus(
                keyword
            )
        }
        if(SessionManager(context).latestGeneralSearch!!.size > 10){
            SessionManager(context).latestGeneralSearch = SessionManager(context).latestGeneralSearch?.takeLast((10))
        }
        val latestSearch = SessionManager(context).latestGeneralSearch?.reversed()
        val chip = Chip(context)

        if(latestSearch?.size != 0){
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

    }

    fun saveArrayList(list: ArrayList<String>, keyword: String?) {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor: SharedPreferences.Editor = prefs.edit()
        val gson = Gson()
        val json: String = gson.toJson(list)
        editor.putString(keyword, json)
        editor.apply()
    }

    @SuppressLint("CommitPrefEdits")
    fun removeArrayList(list: ArrayList<String>, key: String?) {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor: SharedPreferences.Editor = prefs.edit()
        val gson = Gson()
        val json: String = gson.toJson(list)
    }

    fun getArrayList(key: String?): ArrayList<String> {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
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
        val fragmentManager = parentFragmentManager
        val ft = fragmentManager.beginTransaction()
        ft.replace(id , fragment)
        ft.addToBackStack("")
        ft.commit()
    }

    override fun onFragmentTransactionListenerClick(companyNo: Long, jobNo: Long) {
    replaceFragment(JobDetailFragment(JobNo = jobNo, CompanyNo = companyNo))

    }

    override fun onFragmentCompanyDetailsClick(companyNo: Long) {
        replaceFragment(CompanyDetailFragment(companyNo))
    }
}

interface onFragmentTransactionList {
    fun onFragmentTransactionListenerClick(companyNo: Long, jobNo: Long)
}

interface onFragmentTransactionListCompany {
    fun onFragmentCompanyDetailsClick(companyNo: Long)
}