package com.ciptakerjaarunika.kerjaloka.ui.Screens.SearchScreen

import android.os.Bundle
import android.util.Log
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.Search_Api
import com.ciptakerjaarunika.kerjaloka.databinding.ActivitySearchBinding
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyDetail.CompanyDetailFragment
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobDetailScreen.JobDetailFragment
import com.ciptakerjaarunika.kerjaloka.ui.Screens.SearchScreen.Adapter.SearchCompanyAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.SearchScreen.Adapter.SearchJobAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.SearchScreen.Model.general_search_model
import com.google.android.material.chip.Chip

class SearchActivity : Fragment(), onFragmentTransactionList,
    onFragmentTransactionListCompany {
    private var list: general_search_model? = null
    private var keyword: String? = ""
    private lateinit var binding: ActivitySearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (SessionManager(context).latestGeneralSearch == null) {
            SessionManager(context).latestGeneralSearch = listOf()
        }

        binding = ActivitySearchBinding.inflate(layoutInflater)

        binding.btnBack.setOnClickListener {
            activity?.onBackPressed()
        }
        var listSearch = SessionManager(context).latestGeneralSearch?.reversed()
        if (listSearch?.size != 0) {
            listSearch?.forEach { data ->
                val chip = Chip(context)
                chip.setChipBackgroundColorResource(R.color.danger_100)
                chip.apply {
                    textSize = 12f
                    text = data.toString()
                    isChipIconVisible = false
                    isCloseIconVisible = false
                    isClickable = true
                    isCheckable = false
                    setOnClickListener {
                      Log.d("keyword", data.toString())
                        SearchJob(data.toString())
                        binding.searchBar.setQuery(data.toString(), true)
                    }
                    binding.apply {
                            latestResultGrup.addView(chip as View)
                    }
                }
                val chipTop = Chip(context)
                chipTop.setChipBackgroundColorResource(R.color.danger_100)
                chipTop.apply {
                    textSize = 12f
                    text = data.toString()
                    isChipIconVisible = false
                    isCloseIconVisible = false
                    isClickable = true
                    isCheckable = false
//                    binding.apply {
//                        chipGroupTopSearch.addView(chipTop as View)
//                    }
                }
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
                                adapter =
                                    SearchJobAdapter(list!!.jobList, context, this@SearchActivity)
                            }
                            binding.recycleCompany.apply {
                                layoutManager = LinearLayoutManager(context)
                                adapter = SearchCompanyAdapter(
                                    list!!.companyList,
                                    context,
                                    this@SearchActivity
                                )
                            }
                        }
                    }
                    newChips(query)
                    binding.latestResultGrup.setOnClickListener {
                        Toast.makeText(context, "search" + query, Toast.LENGTH_SHORT).show()
                    }
                }
                binding.layoutTopSearchResults.isVisible = false
                binding.layoutResultSearch.isVisible = true
                binding.layoutLatestSearchResults.isVisible = true
                binding.resultSearchJob.text = query
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                keyword = newText
                if (newText?.length!! > 50) {
                    Toast.makeText(context, "Text character is more than 50", Toast.LENGTH_SHORT)
                        .show()
                }

                if (newText.isEmpty()) {
                    binding.layoutLatestSearchResults.isVisible = false
                    binding.layoutTopSearchResults.isVisible = false
                    binding.layoutResultSearch.isVisible = true
                } else {
                    binding.layoutLatestSearchResults.isVisible = true
                    binding.layoutTopSearchResults.isVisible = true
                    binding.layoutResultSearch.isVisible = false
                }
                return false
            }
        })

        binding.btnRemoveLatestSearch.setOnClickListener {
            SessionManager(context).latestGeneralSearch = listOf()
            binding.latestResultGrup.removeAllViews()
        }

        return binding.root
    }

    fun SearchJob(keyword: String?) {
        if (keyword?.isNotEmpty() == true) {
            newChips(keyword)
        }
    }

    private fun newChips(keyword: String) {
        binding.latestResultGrup.isVisible = true
        if (SessionManager(context).latestGeneralSearch?.size == 0 || SessionManager(context).latestGeneralSearch?.last() != keyword) {
            SessionManager(context).latestGeneralSearch =
                SessionManager(context).latestGeneralSearch?.plus(
                    keyword
                )
        }
        if (SessionManager(context).latestGeneralSearch!!.size > 8) {
            SessionManager(context).latestGeneralSearch =
                SessionManager(context).latestGeneralSearch?.takeLast((8))
        }
        val latestSearch = SessionManager(context).latestGeneralSearch?.reversed()
        val chip = Chip(context)

        if (latestSearch?.size != 0) {
            chip.setChipBackgroundColorResource(R.color.danger_100)
            chip.apply {
                textSize = 12f
                text = keyword
                isChipIconVisible = false
                isCloseIconVisible = false
                isClickable = true
                isCheckable = false
                setOnClickListener { SearchJob(keyword) }
                binding.apply {
                    if (latestResultGrup.size > 7) {
                        latestResultGrup.removeViewAt(0)
                    }
                    chip.setOnClickListener {
                        Log.d("keyword", keyword)
                    }
                }
            }
        }

    }

    fun replaceFragment(fragment: Fragment) {
        val fragmentManager = parentFragmentManager
        val ft = fragmentManager.beginTransaction()
        ft.replace(id, fragment)
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