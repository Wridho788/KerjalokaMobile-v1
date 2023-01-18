package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.CompanySearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.anychart.ui.contextmenu.Item
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.CompanySearchAPI
import com.ciptakerjaarunika.kerjaloka.databinding.ActivityCompanySearchBinding
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.CompanyDetail.CompanyDetailFragment
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.CompanySearch.Adapter.CompanySearchAdapter
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.CompanySearch.Bottomsheet.FilterCompany
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.CompanySearch.Model.searchCompanyRequest
import com.google.android.material.chip.Chip
import com.google.firebase.ktx.Firebase
import com.google.firebase.perf.ktx.performance
import com.google.firebase.perf.metrics.AddTrace

class CompanySearchActivity : Fragment(), iSearchCompany {
    private var searchModel: searchCompanyRequest =
        searchCompanyRequest(null, listOf(), listOf(), listOf())
    private var hasSearch = false
    private var isLoading: Boolean = true
    private var keyword: String? = ""
    private lateinit var binding: ActivityCompanySearchBinding

    @AddTrace(name="onCompanySearchActivityTrace", enabled = true)
    class ItemCache{
        fun fetch(name: String): Item? {
            return null
        }
    }

    fun CompanySearchTrace() {
        val cache = ItemCache()
        val myTrace = Firebase.performance.newTrace("company_search_trace")
        myTrace.start()
        val item = cache.fetch("item")
        if (item != null) {
            myTrace.incrementMetric("item_cache_hit", 1)
        } else {
            myTrace.incrementMetric("item_cache_miss", 1)
        }
        myTrace.stop()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityCompanySearchBinding.inflate(layoutInflater)
        CompanySearchTrace()
        binding.btnBack.setOnClickListener {
            fragmentManager?.popBackStack()
        }

        binding.spinnerResult.visibility = View.GONE

        if (SessionManager(context).latestCompanySearch == null) {
            SessionManager(context).latestCompanySearch = listOf()
        }
        binding.layoutTopSearchResults.visibility = GONE
        val list_latest_search_company = SessionManager(context).latestCompanySearch?.reversed()
        if (list_latest_search_company?.size != 0) {
            binding.latestResultGrup.removeAllViews()
            list_latest_search_company?.forEach { data ->
                val chip = Chip(context)
                chip.setChipBackgroundColorResource(R.color.danger_100)
                chip.apply {
                    textSize = 12f
                    text = data.toString()
                    isChipIconVisible = false
                    isCloseIconVisible = false
                    isClickable = true
                    isCheckable = false
                    binding.apply {
                        latestResultGrup.addView(chip as View)
                    }
                    setOnClickListener {
                        SearchJob(data.toString())
                        binding.searchBar.setQuery(data.toString(), true)
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
            }
        } else {
            binding.layoutLatestSearchResults.isVisible = true
            binding.layoutResultSearch.isVisible = false
            binding.layoutTopSearchResults.isVisible = true
        }

        binding.removeHistory.setOnClickListener {
            SessionManager(context).latestCompanySearch = listOf()
            binding.latestResultGrup.removeAllViews()
        }

        binding.btnFilter.setOnClickListener {
            val sheet = FilterCompany(searchModel, this)
            activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "") }
        }

        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query?.isNotEmpty() == true) {
                    searchModel.keyword = query
                    SearchJob(query)
                    searchCompany()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                keyword = newText
                if (newText?.length!! > 50) {
                    Toast.makeText(context, "Text character is more than 50", Toast.LENGTH_SHORT)
                        .show()
                }
                if (newText.isBlank()) {
                    binding.layoutResultSearch.isVisible = false
                    binding.layoutTopSearchResults.isVisible = false
                    binding.layoutResultSearch.isVisible = true
                    binding.notfoundLayout.isVisible = false
                } else {
                    binding.layoutLatestSearchResults.isVisible = true
                    binding.layoutTopSearchResults.isVisible = true
                    binding.layoutResultSearch.isVisible = false
                    binding.layoutCompanyResult.isVisible = false
                }

                return false
            }
        })

        return binding.root
    }

    fun SearchJob(keyword: String?) {
        if (keyword?.isNotEmpty() == true) {
            newChips(keyword)
//            searchCompany()
        }
        binding.resultSearchJob.text = keyword
        this.keyword = keyword
    }

    fun searchCompany() {
        hasSearch = true
        binding.layoutLatestSearchResults.visibility = GONE
        binding.layoutTopSearchResults.visibility = GONE
        binding.spinnerResult.visibility = VISIBLE
        CompanySearchAPI().SearchCompany(context, searchModel) {
            if (it != null) {
                isLoading = false
                binding.spinnerResult.visibility = GONE
                binding.layoutResultSearch.isVisible = true
                if (it.code == 210) {
                    if (it.data != null) {
                        if (it.data.size != 0) {
                            binding.layoutCompanyResult.isVisible = true
                            binding.searchCompanyJob.apply {
                                layoutManager = LinearLayoutManager(context)
                                adapter = CompanySearchAdapter(
                                    it.data,
                                    context,
                                    this@CompanySearchActivity
                                )
                            }
                        } else {
                            binding.notfoundLayout.isVisible = true
                            binding.message.text = it.message
                        }
                        binding.searchCompanyJob.adapter?.notifyDataSetChanged()
                    } else {
                        binding.notfoundLayout.isVisible = true
                        binding.layoutCompanyResult.isVisible = false
                        binding.resultSearchJob.text = it.message
                    }
                }
            } else {
                binding.notfoundLayout.isVisible = true
                binding.layoutCompanyResult.isVisible = false
                binding.layoutResultSearch.isVisible = true
            }
        }
        searchModel.keyword?.let { newChips(it) }
    }

    private fun newChips(keyword: String) {
        if (SessionManager(context).latestCompanySearch?.size == 0 || SessionManager(context).latestCompanySearch?.last() != keyword) {
            SessionManager(context).latestCompanySearch =
                SessionManager(context).latestCompanySearch?.plus(
                    keyword
                )
        }
        if (SessionManager(context).latestCompanySearch!!.size > 8) {
            SessionManager(context).latestCompanySearch =
                SessionManager(context).latestCompanySearch?.takeLast((8))
        }
        val latestSearch = SessionManager(context).latestCompanySearch?.reversed()
        if (latestSearch?.size != 0) {
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
                    if (latestResultGrup.size > 8) {
                        latestResultGrup.removeViewAt(0)
                    }
                }
            }
        }
    }

    override fun searchCompany(
        value: searchCompanyRequest
    ) {
        searchModel.size = value.size
        searchModel.industry = value.industry
        searchModel.location = value.location
    }

    override fun onCompanyDetailPage(CompanyNo: Long) {
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_container, CompanyDetailFragment(CompanyNo))
        ft.addToBackStack("companyPage")
        ft.commit()
    }
}

interface iSearchCompany {
    fun searchCompany(value: searchCompanyRequest)
    fun onCompanyDetailPage(CompanyNo: Long)
}