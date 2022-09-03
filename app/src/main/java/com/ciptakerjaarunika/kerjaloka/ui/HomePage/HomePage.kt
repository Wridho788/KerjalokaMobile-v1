package com.ciptakerjaarunika.kerjaloka.ui.HomePage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.JobAPI
import com.ciptakerjaarunika.kerjaloka.databinding.ActivityMainBinding
import com.ciptakerjaarunika.kerjaloka.ui.HomePage.Adapter.RecommendationJobAdapter
import com.ciptakerjaarunika.kerjaloka.ui.HomePage.Model.rJobModel
import com.ciptakerjaarunika.kerjaloka.ui.JobPage.Job_Page
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyScreen.CompanyPage
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobDetailScreen.JobDetailFragment
import com.ciptakerjaarunika.kerjaloka.ui.Screens.SearchScreen.SearchActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

class HomePage : Fragment(), OnFragmentClickListener {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecommendationJobAdapter.ViewHolder>? = null
    private lateinit var binding: ActivityMainBinding
    private var listJob : List<rJobModel>?=null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    private fun setContentView(root: ConstraintLayout) {
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val btn_search = view.findViewById<LinearLayout>(R.id.btn_search) as LinearLayout
        val btn_notif = view.findViewById<MaterialButton>(R.id.notif_btn) as MaterialButton
        val btn_job = view.findViewById<MaterialCardView>(R.id.btn_job) as MaterialCardView
        val btn_company = view.findViewById<MaterialCardView>(R.id.btn_company) as MaterialCardView
        var btn_see_all_recommendation_job = view.findViewById<TextView>(R.id.btn_see_all_recommendation_jobs) as TextView;

        btn_search.setOnClickListener {
            // code here to handle intent to search activity
            // create intent to search activity
            val intent = Intent(activity, SearchActivity::class.java)
            // start activity
            startActivity(intent)

//            Toast.makeText(activity, "Go to Search Activity", Toast.LENGTH_SHORT).show()
        }
        btn_notif.setOnClickListener {
            Toast.makeText(activity, "Go to Notification Activity", Toast.LENGTH_SHORT).show()
        }
        val Context =this;
        btn_job.setOnClickListener {
            onReplaceFragment(Context)
        }
        btn_company.setOnClickListener {
            onCompanyPage()
        }
        btn_see_all_recommendation_job.setOnClickListener {
            Toast.makeText(activity, "see all!", Toast.LENGTH_SHORT).show()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Response API", "Testing")
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_recommendation_jobs)

        val Context = this;
        JobAPI().getJobHomeAsync(context) {
            Log.d("Response API", it.toString())
            if (it != null) {
                listJob = it.data
                recyclerView.apply {
                    layoutManager = LinearLayoutManager(activity)
                    recyclerView.layoutManager = layoutManager
                    adapter = RecommendationJobAdapter(listJob,Context)
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomePage().apply {
            }
    }

    override fun onFragmentClick(JobNo: Long, CompanyNo: Long) {
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.replace(id, JobDetailFragment(JobNo, CompanyNo), "jobDetailFragment")
        ft.addToBackStack(null)
        ft.commit()
    }

    override fun onCompanyPage() {
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.replace(id, CompanyPage(), "companyFragment")
        ft.addToBackStack(null)
        ft.commit()
    }

    override fun onReplaceFragment(onFragmentClickListener: OnFragmentClickListener) {
        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(id, Job_Page(onFragmentClickListener), "JobFragment")
        fragmentTransaction?.commit()

    }

}

interface OnFragmentClickListener {
    fun onFragmentClick(JobNo:Long, CompanyNo:Long)
    fun onCompanyPage()
    fun onReplaceFragment(onFragmentClickListener: OnFragmentClickListener)
}
