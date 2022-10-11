package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.CompanySearchAPI
import com.ciptakerjaarunika.kerjaloka.databinding.ActivityCompanySearchBinding
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Adapter.CompanySearchAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Bottomsheet.FilterCompany
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Model.searchCompanyRequest
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Model.search_company_model
import com.google.android.material.chip.Chip

class CompanySearchActivity : Fragment(), iSearchCompany {
    private var searchModel : searchCompanyRequest = searchCompanyRequest(null, listOf(), listOf(), listOf())
    private var hasSearch = false;

    private lateinit var binding: ActivityCompanySearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityCompanySearchBinding.inflate(layoutInflater)
        binding.btnBack.setOnClickListener {
            fragmentManager?.popBackStack()
        }

        if(SessionManager(context).latestCompanySearch == null){
            SessionManager(context).latestCompanySearch = listOf()
        }
        binding.layoutTopSearchResults.visibility = GONE
        val list_latest_search_company = SessionManager(context).latestCompanySearch?.reversed()
        if (list_latest_search_company?.size != 0) {
            var int = 0
            list_latest_search_company?.forEach {
                val chip = Chip(context)
                chip.setChipBackgroundColorResource(R.color.danger_100)
                chip.apply {
                    textSize = 12f
                    text = it.toString()
                    id = int
                    isChipIconVisible = false
                    isCloseIconVisible = false
                    isClickable = true
                    isCheckable = true
                    binding.apply {
                        latestResultGrup.addView(chip as View)
                    }
                }
//                val chipTop = Chip(context)
//                chipTop.setChipBackgroundColorResource(R.color.danger_100)
//                chipTop.apply {
//                    textSize = 12f
//                    text = it.toString()
//                    id = int
//                    isChipIconVisible = false
//                    isCloseIconVisible = false
//                    isClickable = true
//                    isCheckable = true
//                    binding.apply {
//                        chipGroupTopSearch.addView(chipTop as View)
//                    }
//                }
                int++
            }
        } else {
            binding.layoutLatestSearchResults.isVisible = true
            binding.layoutResultSearch.isVisible = false
            binding.layoutTopSearchResults.isVisible = true
        }

        binding.btnRemoveLatestSearch.setOnClickListener{
            SessionManager(context).latestCompanySearch = listOf()
            binding.latestResultGrup.removeAllViews()
        }

        binding.btnFilter.setOnClickListener{
            val sheet = FilterCompany(searchModel, this)
            activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "") }
        }

        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query?.isNotEmpty() == true) {
                    searchModel.keyword = query;
                    searchCompany()
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

        return binding.root
    }

    fun searchCompany(){
        hasSearch = true;
        binding.layoutLatestSearchResults.visibility = GONE
        binding.layoutTopSearchResults.visibility = GONE
        binding.layoutResultSearch.isVisible = true
        CompanySearchAPI().SearchCompany(context, searchModel) {
            if (it != null) {
                binding.searchCompanyJob.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = CompanySearchAdapter(it.data!!, context)
                }
                binding.searchCompanyJob.adapter?.notifyDataSetChanged()
            }
        }
        searchModel.keyword?.let { newChips(it) }
    }
    private fun newChips(keyword: String) {
        if(SessionManager(context).latestCompanySearch?.size == 0 ||  SessionManager(context).latestCompanySearch?.last() != keyword) {
            SessionManager(context).latestCompanySearch = SessionManager(context).latestCompanySearch?.plus(
                keyword
            )
        }
        if(SessionManager(context).latestCompanySearch!!.size > 10){
            SessionManager(context).latestCompanySearch = SessionManager(context).latestCompanySearch?.takeLast((10))
        }
        val latestSearch = SessionManager(context).latestCompanySearch?.reversed()
        if(latestSearch?.size != 0){
            binding.latestResultGrup.visibility = View.VISIBLE
            binding.latestResultGrup.removeAllViews()
            val chip = Chip(context)
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
    }
    override fun searchCompany(
        value : searchCompanyRequest
    ) {
        searchModel.size = value.size
        searchModel.industry = value.industry
        searchModel.location = value.location
        searchCompany()
    }
}

interface iSearchCompany{
    fun searchCompany(value : searchCompanyRequest)
}