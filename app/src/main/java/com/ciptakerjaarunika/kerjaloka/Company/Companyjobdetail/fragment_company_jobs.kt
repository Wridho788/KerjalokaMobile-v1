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
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentCompanyJobsBinding


class fragment_company_jobs : Fragment() {

    private lateinit var binding: FragmentCompanyJobsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCompanyJobsBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val addjob = view.findViewById<ImageView>(R.id.idFABAdd)

        addjob.setOnClickListener{
            val myIntent = Intent(view.context, AddJobActivity::class.java)
            startActivity(myIntent)
        }
        var list = ArrayList<Data>()

        JobAPI().getJob(context){
            list = it?.data as ArrayList<Data>
            val recyclerView = view.findViewById<RecyclerView>(R.id.recyle_company_jobs)
            recyclerView.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = it?.data?.let { it1 -> assignAdapter(it1) }
            }
        }
    }

    companion object {

//        val list = ArrayList<ResponseJobs>()
//        val citylist = ArrayList<JobCity>()
//        val city1 = JobCity(
//            id=0,
//            cityname = "Kota Medan"
//        )
//        citylist.add(city1)
//        val job1 = ResponseJobs(
//            createdBy ="reyhan@kerjaloka.com",
//            createdOn ="2022-08-03T10:56:24",
//            expired ="2022-09-02T00:00:00",
//            jobAdditionalDescription =null,
//            jobCity =citylist,
//            jobDescription ="<ul><li>Crosscheck cashflow, GL Accounting, and balance sheet</li><li>Financial overview per month</li><li>Manage Petty Cash</li><li>Prepare required document of daily banking transaction</li><li>Monitoring &amp; report export proceeds and import payment through SiMoDIS</li><li>Reconcile all of bank account every day</li><li>Update payment in SAP</li></ul><p><br></p>",
//            jobExperienceLevel =null,
//            jobField =null,
//            jobMinExperience =12,
//            jobNo ="4120220803105624",
//            jobPosition ="Testing Baru",
//            jobRole =null,
//            jobSalaryMax =null,
//            jobSalaryMin =1223333,
//            jobShortQuestion =null,
//            jobSkills =null,
//            jobTests =null,
//            jobTitle =null,
//            jobType =null,
//            link ="https://advance.kerjaloka.com/job/TESTING/4120220803105624",
//            packageName =null,
//            publish =false,
//            takedown =false
//        )
//        list.add(job1)
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

            override fun shareJob(shareJob: Data) {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TITLE, shareJob.jobPosition)
                    putExtra(Intent.EXTRA_TEXT, shareJob.link)
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
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