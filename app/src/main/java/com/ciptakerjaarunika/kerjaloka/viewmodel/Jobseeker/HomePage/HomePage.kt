package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.HomePage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anychart.ui.contextmenu.Item
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.InterviewAPI
import com.ciptakerjaarunika.kerjaloka.api.JobAPI
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentHomeBinding
import com.ciptakerjaarunika.kerjaloka.enum.Role
import com.ciptakerjaarunika.kerjaloka.model.Job.SearchJobModel
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Companyjobdetail.fragment_company_jobs
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.CompanyScreen.CompanyPage
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.HomePage.Adapter.RecommendationJobAdapter
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.InterviewPage.InterviewPage
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.JobDetailScreen.JobDetailFragment
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.JobPage.JobPage
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.JobRecommendation.JobRecommendationFragment
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.NotificationPage.Notification
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.SearchScreen.SearchActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.firebase.ktx.Firebase
import com.google.firebase.perf.ktx.performance
import com.google.firebase.perf.metrics.AddTrace
import com.instabug.apm.APM

class HomePage : Fragment(), OnFragmentClickListener {
    private lateinit var binding: FragmentHomeBinding
    private var listJob: List<SearchJobModel>? = null

    @AddTrace(name="onHomePageJobseekerTrace", enabled = true)
    class ItemCache{
        fun fetch(name: String): Item? {
            return null
        }
    }

    fun HomepageTrace() {
        val cache = ItemCache()
        val myTrace = Firebase.performance.newTrace("home_page_trace")
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
        HomepageTrace()
        binding = FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = binding.root
        APM.setFragmentSpansEnabled(true)
        if (SessionManager(context).user != null) {
            view.findViewById<TextView>(R.id.greeting_txt).text =
                SessionManager(context).user?.userFullname!!.split(" ")[0]
        }
        binding.swipeToRefresh.setColorSchemeColors(R.color.danger_500)
        binding.swipeToRefresh.setOnRefreshListener {
            GetInterviewList()
            GetJobRecommendation()
            binding.swipeToRefresh.isRefreshing = false
        }
        val btn_search = view.findViewById<LinearLayout>(R.id.btn_search)
        val btn_notif = view.findViewById<MaterialButton>(R.id.notif_btn)
        val btn_job = view.findViewById<MaterialCardView>(R.id.btn_job)
        val btn_company = view.findViewById<MaterialCardView>(R.id.btn_company)
        var btn_see_all_recommendation_job =
            view.findViewById<TextView>(R.id.btn_see_all_recommendation_jobs)

        btn_search.setOnClickListener {
            val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
            ft.replace(id, SearchActivity(), "")
            ft.addToBackStack("")
            ft.commit()
        }

        binding.myInterviewSection.visibility = View.GONE
        GetInterviewList()
        GetJobRecommendation()

        if (SessionManager(context).user != null) {
            btn_notif.visibility = VISIBLE
            btn_notif.setOnClickListener {
                val intent = Intent(activity, Notification::class.java)
                startActivity(intent)
            }
        }

        if (SessionManager(context).user == null || SessionManager(context).user?.roleNo == Role.Jobseekers.value) {
            btn_job.setOnClickListener {
                onJobPage()
            }
        } else {
            btn_job.setOnClickListener {
                onCompanyJobPage()
            }
        }

        btn_company.setOnClickListener {
            onCompanyPage()
        }
        btn_see_all_recommendation_job.setOnClickListener {
            val fragmentTransaction = fragmentManager!!.beginTransaction()
            fragmentTransaction.addToBackStack("Job Page")
            fragmentTransaction.replace(R.id.fragment_container, JobRecommendationFragment())
            fragmentTransaction.commit()
        }
        return view
    }

    fun GetInterviewList() {
        InterviewAPI().GetInterviewList(context) {
            binding.myInterviewSection.visibility = View.GONE
            if (it != null) {
                Log.d("Interview jobseeker", it.toString())
                if (it.code == 210 && it.data.isNotEmpty()) {
                    binding.myInterviewSection.visibility = VISIBLE
                    binding.interviewSection.btnSeeAllInterview.setOnClickListener {
                        onInterviewsPage()
                    }
                    it.data.forEach { interview ->
                        binding.interviewSection.titleJobInterview.text =
                            interview.jobPosition.toString()
                        binding.interviewSection.companyName.text = interview.companyName.toString()
                    }
                }
            }
        }
    }

    fun GetJobRecommendation() {
        val Context = this

        JobAPI().getJobRecommendation(false, context) {
            binding.spinnerRecommendation.visibility = VISIBLE
            if (it != null) {
                binding.spinnerRecommendation.visibility = View.GONE
                binding.layoutRecommendation.visibility = VISIBLE

                listJob = it.data
                binding.recyclerViewRecommendationJobs.apply {
                    layoutManager = LinearLayoutManager(activity)
                    binding.recyclerViewRecommendationJobs.layoutManager = layoutManager
                    adapter = RecommendationJobAdapter(context, listJob, Context)
                }
            }
        }
    }


    override fun onFragmentClick(JobNo: Long, CompanyNo: Long) {
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.addToBackStack("")
        ft.replace(id, JobDetailFragment(JobNo, CompanyNo), "")
        ft.commit()
    }

    override fun onCompanyPage() {
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.addToBackStack("")
        ft.replace(id, CompanyPage(), "")
        ft.commit()
    }

    override fun onInterviewsPage() {
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.addToBackStack("")
        ft.replace(id, InterviewPage(), "")
        ft.commit()
    }

    override fun onJobPage() {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.addToBackStack("")
        fragmentTransaction.replace(id, JobPage(), "")
        fragmentTransaction.commit()
    }

    override fun onCompanyJobPage() {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.addToBackStack("")
        fragmentTransaction.replace(id, fragment_company_jobs(), "")
        fragmentTransaction.commit()
    }

    override fun bookmarkJob(list: List<SearchJobModel>) {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.recycler_view_recommendation_jobs)
        listJob = list

        recyclerView?.apply {
            layoutManager = LinearLayoutManager(activity)
            recyclerView.layoutManager = layoutManager
            adapter = RecommendationJobAdapter(context, listJob, this@HomePage)
        }
        recyclerView?.adapter?.notifyDataSetChanged()
    }

}

interface OnFragmentClickListener {
    fun onFragmentClick(JobNo: Long, CompanyNo: Long)
    fun onCompanyPage()
    fun onJobPage()
    fun onCompanyJobPage()
    fun bookmarkJob(list: List<SearchJobModel>)
    fun onInterviewsPage()
}
