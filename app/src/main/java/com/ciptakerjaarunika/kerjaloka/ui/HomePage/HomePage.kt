package com.ciptakerjaarunika.kerjaloka.ui.HomePage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.fragment_company_jobs
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.JobAPI
import com.ciptakerjaarunika.kerjaloka.databinding.ActivityMainBinding
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentHomeBinding
import com.ciptakerjaarunika.kerjaloka.enum.Role
import com.ciptakerjaarunika.kerjaloka.model.Job.SearchJobModel
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.HomePage.Adapter.RecommendationJobAdapter
import com.ciptakerjaarunika.kerjaloka.ui.HomePage.Model.rJobModel
import com.ciptakerjaarunika.kerjaloka.ui.NotificationPage.Notification
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyScreen.CompanyPage
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobDetailScreen.JobDetailFragment
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobPage.JobPage
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobRecommendation.JobRecommendationFragment
import com.ciptakerjaarunika.kerjaloka.ui.Screens.SearchScreen.SearchActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

class HomePage : Fragment(), OnFragmentClickListener {
    private lateinit var binding: FragmentHomeBinding
    private var listJob: List<SearchJobModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentHomeBinding.inflate(layoutInflater)
    }

    private fun setContentView(root: ConstraintLayout) {
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = binding.root
        if(SessionManager(context).user != null) {
            view.findViewById<TextView>(R.id.greeting_txt).text = SessionManager(context).user?.userFullname!!.split(" ")[0]
        }
        val btn_search = view.findViewById<LinearLayout>(R.id.btn_search)
        val btn_notif = view.findViewById<MaterialButton>(R.id.notif_btn)
        val btn_job = view.findViewById<MaterialCardView>(R.id.btn_job)
        val btn_company = view.findViewById<MaterialCardView>(R.id.btn_company)
        var btn_see_all_recommendation_job =
            view.findViewById<TextView>(R.id.btn_see_all_recommendation_jobs)

        btn_search.setOnClickListener {
            val intent_search = Intent(activity, SearchActivity::class.java)
            startActivity(intent_search)
        }
        if(SessionManager(context).user != null){
            btn_notif.visibility = VISIBLE
            btn_notif.setOnClickListener {
                val intent = Intent(activity, Notification::class.java)
                startActivity(intent)
            }
        }


        if (SessionManager(context).user == null || SessionManager(context).user?.roleNo == Role.Jobseekers.value){
            btn_job.setOnClickListener {
                onJobPage()
            }
        }  else {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_recommendation_jobs)

        val Context = this
        JobAPI().getJobRecommendation(false,context) {
            if (it != null) {
                listJob = it.data
                recyclerView.apply {
                    layoutManager = LinearLayoutManager(activity)
                    recyclerView.layoutManager = layoutManager
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

    override fun bookmarkJob(list : List<SearchJobModel>) {
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
    fun bookmarkJob(list : List<SearchJobModel>)
}
