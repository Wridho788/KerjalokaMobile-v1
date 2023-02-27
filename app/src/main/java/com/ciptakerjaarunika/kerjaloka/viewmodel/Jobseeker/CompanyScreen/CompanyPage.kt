package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.CompanyScreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anychart.ui.contextmenu.Item
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.CompanyBrowseAPI
import com.ciptakerjaarunika.kerjaloka.api.CompanyFollowedAPI
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentCompanyPageBinding
import com.ciptakerjaarunika.kerjaloka.model.CompanyPage.company_browse_list
import com.ciptakerjaarunika.kerjaloka.model.CompanyPage.company_followed_list
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.CompanyBrowse.CompanyBrowse
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.CompanyDetail.CompanyDetailFragment
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.CompanyScreen.Adapter.CompanyBrowseAdapter
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.CompanyScreen.Adapter.CompanyFollowedAdapter
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.CompanyScreen.Adapter.CompanyVacanciesAdapter
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.CompanySearch.CompanySearchActivity
import com.google.firebase.ktx.Firebase
import com.google.firebase.perf.ktx.performance
import com.google.firebase.perf.metrics.AddTrace

class CompanyPage : Fragment(), OnFragmentClickListener {

    private lateinit var binding: FragmentCompanyPageBinding
    private var isLoading: Boolean = true
    private var isFollowed: Boolean = true
    private val Context = this
    private var listFollowedJob: List<company_followed_list>? = null
    private var listSearchJob: List<company_browse_list>? = null
    private var keyword: String? = ""
    private var hasSearch: Boolean = false

    @AddTrace(name = "onCompanyPage", enabled = true)
    class ItemCache {
        fun fetch(name: String): Item? {
            return null
        }
    }

    private fun companyPageTrace() {
        val cache = ItemCache()
        val myTrace = Firebase.performance.newTrace("company_review_trace")
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
        companyPageTrace()
    }

    private fun getFollowedJobData() {
        CompanyFollowedAPI().CompanyGetFollowedJob(context) {
            view?.findViewById<LinearLayout>(R.id.spinnerFollowed)?.visibility = GONE

            if (it != null) {
                isLoading = false
                listFollowedJob = it.data
                val recyclerViewFollowedCompany =
                    view?.findViewById<RecyclerView>(R.id.rv_followed_company)
                val btn_see_more_job = view?.findViewById<LinearLayout>(R.id.btn_see_more_followed)

                if (it != null && it.data.size > 5) {
                    btn_see_more_job?.visibility = VISIBLE
                    btn_see_more_job?.setOnClickListener {
                        Toast.makeText(context, "Follow company", Toast.LENGTH_SHORT).show()
                    }
                }
                recyclerViewFollowedCompany?.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = CompanyFollowedAdapter(
                        context, listFollowedJob!!.take(5), this@CompanyPage
                    )
                }

            }
        }
    }

    private fun getBrowserJobData() {
        CompanyBrowseAPI().CompanyGetBrowserJob(context) {
            view?.findViewById<LinearLayout>(R.id.spinnerBrowse)?.visibility = GONE
            if (it != null) {
                isLoading = false
                listSearchJob = it.data
                val recyclerViewCompanyBrowse =
                    view?.findViewById<RecyclerView>(R.id.rv_browse_company)
                val btn_see_more = view?.findViewById<LinearLayout>(R.id.btn_see_more_browse)
                if (it != null && it.data.size > 5) {
                    btn_see_more?.visibility = VISIBLE
                    btn_see_more?.setOnClickListener {
                        changeFragment(CompanyBrowse())
                    }
                }
                recyclerViewCompanyBrowse?.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter =
                        CompanyBrowseAdapter(context, listSearchJob!!.take(5), this@CompanyPage)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCompanyPageBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("CutPasteId")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backButton.setOnClickListener {
            fragmentManager?.popBackStack()
        }

        var user = SessionManager(context).user
        val layout_followed_company = view.findViewById<LinearLayout>(R.id.layout_followed_company)
        val layout_search_company = view.findViewById<LinearLayout>(R.id.search_company_btn)
        val recyclerViewVacanciesCompany =
            view.findViewById<RecyclerView>(R.id.rv_vacancies_company)

        if (user == null) {
            layout_followed_company.visibility = GONE
        }
        isFollowed = user != null && user.roleNo == 4
        if (isFollowed) {
            getFollowedJobData()
        }
        getBrowserJobData()

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)


        layout_search_company.setOnClickListener {
            val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
            ft.replace(R.id.fragment_container, CompanySearchActivity())
            ft.addToBackStack("companyPage")
            ft.commit()
        }

        CompanyBrowseAPI().CompanyActiveHire(context) {
            view.findViewById<LinearLayout>(R.id.spinnerVacancies)?.visibility = GONE
            if (it != null) {
                val recyclerView = view.findViewById<RecyclerView>(R.id.rv_vacancies_company)
                val btn_see_more = view.findViewById<LinearLayout>(R.id.btn_see_more)
                if (it != null && it.data.size > 5) {
                    btn_see_more?.visibility = VISIBLE

                }
                recyclerView?.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = CompanyVacanciesAdapter(context, it.data.take(5), this@CompanyPage)
                }
            }
        }

    }

    fun changeFragment(Goto: Fragment) {
        val fragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.addToBackStack("Company Page")
        fragmentTransaction.replace(R.id.fragment_container, Goto)
        fragmentTransaction.commit()
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