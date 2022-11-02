package com.ciptakerjaarunika.kerjaloka.ui.Screens.JobSearch

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isNotEmpty
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.JobAPI
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentSearchJobBinding
import com.ciptakerjaarunika.kerjaloka.model.Job.SearchJobModel
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobDetailScreen.JobDetailFragment
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobPage.Adapter.JobAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobPage.IJobPage
import com.google.android.material.chip.Chip


class SearchJob : Fragment(), IJobPage, iSearchJob {
    private var page = 0
    private var takeData = 25
    private var isSearching = false
    private var listData: List<SearchJobModel> = listOf()

    private var locationSelected: List<Int> = listOf()
    private var skillSelected: List<Int> = listOf()
    private var jobTypeSelected: List<Int> = listOf()
    private var experienceLevelSelected: List<Int> = listOf()
    private var salaryMin: Int? = null
    private var salaryMax: Int? = null
    private var keyword: String? = ""
    private var hasSearch: Boolean = false


    var list = ArrayList<SearchModel>()
    private lateinit var binding: FragmentSearchJobBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchJobBinding.inflate(layoutInflater)
        binding.backBtn.setOnClickListener {
            fragmentManager?.popBackStack()
        }
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.topSearchContainer.visibility = GONE
        binding.latestSearchContainer.visibility = GONE
        if (binding.searchJob.isNotEmpty()) {
            Log.d("text", binding.searchJob.query.toString())
        }
        if (SessionManager(context).latestSearchJob == null) {
            SessionManager(context).latestSearchJob = listOf()
        }
        binding.btnFilter.setOnClickListener {
            val sheet = FilterJobModal(
                this@SearchJob,
                locationSelected,
                jobTypeSelected,
                skillSelected,
                experienceLevelSelected,
                this.salaryMin,
                this.salaryMax
            )
            activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "ReportJob") }
        }
        val latestSearch = SessionManager(context).latestSearchJob?.reversed()
        if (latestSearch?.size != 0) {
            binding.latestSearchContainer.visibility = VISIBLE
            binding.chipGroup.removeAllViews()

            latestSearch?.forEach { data ->
                val chip = Chip(context)
                chip.setChipBackgroundColorResource(R.color.danger_100)
                chip.apply {
                    textSize = 12f
                    text = data.toString()
                    isChipIconVisible = false
                    isCloseIconVisible = false
                    isClickable = true
                    setOnClickListener {
                        SearchJob(data.toString())
                        binding.searchJob.setQuery(data.toString(), true)
                    }
                    isCheckable = false
                    binding.apply {
                        chipGroup.addView(chip as View)
                    }
                }
            }
        }


        JobAPI().GetTopSearch(context) { res ->
            if (res != null) {
                if (res.data.size != 0) {
                    binding.topSearchContainer.visibility = VISIBLE
                    binding.chipGroupTopSearch.removeAllViews()
                    res.data.forEach { data ->
                        val chipTop = Chip(context)
                        chipTop.setChipBackgroundColorResource(R.color.danger_100)
                        chipTop.apply {
                            textSize = 12f
                            text = data.keyword
                            isChipIconVisible = false
                            isCloseIconVisible = false
                            isClickable = true
                            setOnClickListener {
                                SearchJob(data.keyword.toString())
                                binding.searchJob.setQuery(data.keyword.toString(), true)
                            }
                            isCheckable = false
                            binding.apply {
                                chipGroupTopSearch.addView(chipTop as View)
                            }
                        }
                    }
                }
            }
        }

        binding.searchJob.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                SearchJob(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                keyword = newText

                if (newText!!.isBlank() && !hasSearch) {
                    binding.searchResult.visibility = GONE
                    binding.history.visibility = VISIBLE

                    JobAPI().GetTopSearch(context) { res ->
                        if (res != null) {
                            if (res.data.size != 0) {
                                binding.topSearchContainer.visibility = VISIBLE
                                binding.chipGroupTopSearch.removeAllViews()
                                res.data.forEach { data ->
                                    val chipTop = Chip(context)
                                    chipTop.setChipBackgroundColorResource(R.color.danger_100)
                                    chipTop.apply {
                                        textSize = 12f
                                        text = data.keyword
                                        isChipIconVisible = false
                                        isCloseIconVisible = false
                                        isClickable = true
                                        setOnClickListener { SearchJob(data.keyword) }
                                        isCheckable = false
                                        binding.apply {
                                            chipGroupTopSearch.addView(chipTop as View)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                return true
            }
        })

        binding.removeHistory.setOnClickListener {
            SessionManager(context).latestSearchJob = listOf()
            binding.chipGroup.removeAllViews()
        }


    }

    fun SearchJob(keyword: String?) {
        if (keyword?.isNotEmpty() == true) {
            newChips(keyword)
        }
        binding.query.text = keyword
        this.keyword = keyword

        SearchJobs()
    }

    private fun newChips(name: String) {
        if (SessionManager(context).latestSearchJob?.size == 0 || SessionManager(context).latestSearchJob?.last() != name) {
            SessionManager(context).latestSearchJob = SessionManager(context).latestSearchJob?.plus(
                name
            )
        }
        if (SessionManager(context).latestSearchJob!!.size > 10) {
            SessionManager(context).latestSearchJob =
                SessionManager(context).latestSearchJob?.takeLast((10))
        }
        val latestSearch = SessionManager(context).latestSearchJob?.reversed()
        if (latestSearch?.size != 0) {
            binding.latestSearchContainer.visibility = VISIBLE
            binding.chipGroup.removeAllViews()

            latestSearch?.forEach { data ->
                val chip = Chip(context)
                chip.setChipBackgroundColorResource(R.color.danger_100)
                chip.apply {
                    textSize = 12f
                    text = data.toString()
                    isChipIconVisible = false
                    isCloseIconVisible = false
                    isClickable = true
                    setOnClickListener { SearchJob(data.toString()) }
                    isCheckable = false
                    binding.apply {
                        chipGroup.addView(chip as View)
                    }
                }
            }
        }
    }

    override fun RefreshData() {

    }

    override fun GoToJobDetail(JobNo: Long, CompanyNo: Long?) {
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.replace(id, JobDetailFragment(JobNo, CompanyNo), "JobDetailFragment")
        ft.addToBackStack("Job Page")
        ft.commit()
    }

    override fun BookmarkJob(ListNo: Int, JobNo: Long, Index: Int) {
        JobAPI().BookmarkJob(JobNo, !listData[Index].bookmarked, context) {
            if (it != null) {
                if (it.code == 210) {
                    listData[Index].bookmarked = !listData[Index].bookmarked
                    binding.recycleJobs.adapter?.notifyDataSetChanged()
                } else {
                    Toast.makeText(context, it.Message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun nextPage() {
        if (!isSearching) {
            isSearching = true
            var size = listData.size <= page * takeData
            if (size == true) {
                JobAPI().SearchJob(
                    JobAPI.searchJobRequest(
                        keyword!!,
                        locationSelected,
                        jobTypeSelected,
                        skillSelected,
                        experienceLevelSelected,
                        salaryMin,
                        salaryMax,
                        page
                    ), context
                ) { res ->
                    if (res != null) {
                        if (res.data.isNotEmpty()) {
                            listData += res.data
                            page += 1

                            val appContext = this
                            binding.recycleJobs.apply {
                                layoutManager = LinearLayoutManager(context)
                                adapter =
                                    JobAdapter(1, listData, context, appContext, this@SearchJob)
                            }
                            binding.recycleJobs.adapter?.notifyDataSetChanged()
                            binding.recycleJobs.adapter?.itemCount?.minus(28)
                                ?.let { binding.recycleJobs.scrollToPosition(it) }
                        }
                    }
                    isSearching = false
                }
            }
        }
    }

    override fun SearchJobs() {
        this.hasSearch = true
        binding.searchResult.visibility = VISIBLE
        binding.history.visibility = GONE
        page = 0

        JobAPI().SearchJob(
            JobAPI.searchJobRequest(
                keyword!!,
                locationSelected,
                jobTypeSelected,
                skillSelected,
                experienceLevelSelected,
                salaryMin,
                salaryMax,
                page
            ), context
        ) { res ->
            if (res != null) {
                listData = res.data
                Log.d("response search", listData.toString())
                val appContext = this
                binding.recycleJobs.visibility = VISIBLE
                binding.recycleJobs.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = JobAdapter(1, listData, context, appContext, this@SearchJob)
                }
                binding.recycleJobs.adapter?.notifyDataSetChanged()
            }
        }
    }

    override fun updateLocationSelected(data: List<Int>) {
        this.locationSelected = data
    }

    override fun updateJobTypeSelected(data: List<Int>) {
        this.jobTypeSelected = data
    }

    override fun updateSkillSelected(data: List<Int>) {
        this.skillSelected = data
    }

    override fun updateExperienceSelected(data: List<Int>) {
        this.experienceLevelSelected = data
    }

    override fun updateSalaryMin(data: Int?) {
        this.salaryMin = data
    }

    override fun updateSalaryMax(data: Int?) {
        this.salaryMax = data
    }
}

interface iSearchJob {
    fun SearchJobs()
    fun updateLocationSelected(data: List<Int>)
    fun updateJobTypeSelected(data: List<Int>)
    fun updateSkillSelected(data: List<Int>)
    fun updateExperienceSelected(data: List<Int>)
    fun updateSalaryMin(data: Int?)
    fun updateSalaryMax(data: Int?)
    fun nextPage()
}