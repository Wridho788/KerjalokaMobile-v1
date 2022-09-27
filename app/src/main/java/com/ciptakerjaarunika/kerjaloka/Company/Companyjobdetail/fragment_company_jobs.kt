package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.Listener.JobDetail
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.adapter.Companyjobs_adapter
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.model.Data
import com.ciptakerjaarunika.kerjaloka.Company.Profile.Adapter.CompReviewHistoryAdapter
import com.ciptakerjaarunika.kerjaloka.Company.Profile.Listener.ShowModal
import com.ciptakerjaarunika.kerjaloka.Company.Profile.ReviewSaya.EditMyReview
import com.ciptakerjaarunika.kerjaloka.Company.Profile.myReview
import com.ciptakerjaarunika.kerjaloka.Company.Profile.ratingData

import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.JobAPI
import com.ciptakerjaarunika.kerjaloka.ui.Global.GlobalDeleteModal
import com.ciptakerjaarunika.kerjaloka.ui.Global.otpVerification
import com.google.gson.Gson
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay


class fragment_company_jobs : Fragment() {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<Companyjobs_adapter.ViewHolder>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_company_jobs, container, false)
        val addjob = view.findViewById<ImageView>(R.id.idFABAdd)

        addjob.setOnClickListener{
            val myIntent = Intent(view.context, AddJobActivity::class.java)
            startActivity(myIntent)

        }
        var list = ArrayList<Data>()

        JobAPI().getJob(context){
            list = it?.data as ArrayList<Data>
            val recyclerView = view.findViewById<RecyclerView>(R.id.recyle_company_jobs)
            layoutManager = LinearLayoutManager(activity)
            recyclerView.layoutManager = layoutManager
            adapter = it?.data?.let { it1 -> assignAdapter(it1) }
            recyclerView.adapter = adapter
        }
        return view
    }

    companion object {

    }
    private fun replaceFragment(fragment: Fragment) {

        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.fragment_container, fragment)
        fragmentTransaction?.commit()
    }

    internal fun assignAdapter(list: List<Data>): Companyjobs_adapter {
        return Companyjobs_adapter(list, object : JobDetail {
            override fun jobDetail(jobDetail: Data) {
                replaceFragment(jobDetail)
            }
        })
    }

    private fun replaceFragment(data: Data?) {
        val jobDetailFragment = fragment_company_job_active_page()
        val mBundle = Bundle()
        val jobData = Gson().toJson(data)
        mBundle.putString(fragment_company_job_active_page.EXTRA_DETAIL_JOB, jobData)
        jobDetailFragment.arguments = mBundle
        val mFragmentManager = parentFragmentManager
        mFragmentManager?.beginTransaction()?.apply {
            replace(
                R.id.fragment_container,
                jobDetailFragment,
                fragment_company_job_active_page::class.java.simpleName
            )
            addToBackStack(null)
            commit()

        }
    }
}