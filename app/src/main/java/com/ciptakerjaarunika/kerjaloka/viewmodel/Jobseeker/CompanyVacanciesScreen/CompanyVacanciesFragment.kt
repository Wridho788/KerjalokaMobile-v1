package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.CompanyVacanciesScreen

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
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentCompanyVacanciesBinding
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.CompanyDetail.CompanyDetailFragment
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.CompanyScreen.Adapter.CompanyVacanciesAdapter
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.CompanyScreen.OnFragmentClickListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.perf.ktx.performance
import com.google.firebase.perf.metrics.AddTrace

class CompanyVacanciesFragment : Fragment(), OnFragmentClickListener {
    private lateinit var binding: FragmentCompanyVacanciesBinding

    @AddTrace(name = "onCompanyVacanciesTrace", enabled = true)
    class ItemCache {
        fun fetch(name: String): Item? {
            return null
        }
    }

    fun CompanyVacanciesTrace() {
        val cache = ItemCache()
        val myTrace = Firebase.performance.newTrace("company_vacancies_trace")
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
        binding = FragmentCompanyVacanciesBinding.inflate(layoutInflater)
        CompanyVacanciesTrace()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentCompanyVacanciesBinding.inflate(layoutInflater)
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
        CompanyBrowseAPI().CompanyActiveHire(context) {
            binding.spinner.visibility = View.GONE
            if (it != null) {
                binding.rvVacanciesCompanyJob.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter =
                        CompanyVacanciesAdapter(context, it.data, this@CompanyVacanciesFragment)
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