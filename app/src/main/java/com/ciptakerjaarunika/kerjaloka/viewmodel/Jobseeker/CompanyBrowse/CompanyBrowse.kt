package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.CompanyBrowse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.anychart.ui.contextmenu.Item
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.CompanyBrowseAPI
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentCompanyWantToKnowBinding
import com.ciptakerjaarunika.kerjaloka.model.CompanyPage.company_browse_list
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.CompanyDetail.CompanyDetailFragment
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.CompanyScreen.Adapter.CompanyBrowseAdapter
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.CompanyScreen.OnFragmentClickListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.perf.ktx.performance
import com.google.firebase.perf.metrics.AddTrace

class CompanyBrowse : Fragment(), OnFragmentClickListener {
    private lateinit var binding: FragmentCompanyWantToKnowBinding
    private var listSearchJob: List<company_browse_list>? = listOf()
    private var isLoading: Boolean = true

    @AddTrace(name = "onCompanyBrowseTrace", enabled = true)
    class ItemCache {
        fun fetch(name: String): Item? {
            return null
        }
    }

    fun CompanyBrowseTrace() {
        val cache = ItemCache()
        val myTrace = Firebase.performance.newTrace("company_browse_trace")
        myTrace.start()
        val item = cache.fetch("item")
        if (item != null) {
            myTrace.incrementMetric("item_cache_hit", 1)
        } else {
            myTrace.incrementMetric("item_cache_miss", 1)
        }
        myTrace.stop()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CompanyBrowseTrace()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentCompanyWantToKnowBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
        getBrowserJobData()
    }

    private fun getBrowserJobData() {
        CompanyBrowseAPI().CompanyGetBrowserJob(context) {
            binding.spinner.visibility = View.GONE
            if (it != null) {
                isLoading = false
                listSearchJob = it.data
                binding.rvBrowseCompanyJob.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = CompanyBrowseAdapter(context, listSearchJob!!, this@CompanyBrowse)
                }
            }
        }
    }

    override fun onCompanyDetailPage(CompanyNo: Long) {
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_container, CompanyDetailFragment(CompanyNo))
        ft.addToBackStack("companyPage")
        ft.commit()
    }
}

open interface OnFragmentClickListener {
    fun onCompanyDetailPage(CompanyNo: Long)
}