package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.ciptakerjaarunika.kerjaloka.R
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
//            val myIntent = Intent(view.context, AddJobActivity::class.java)
//            startActivity(myIntent)
            replaceFragment(fragment_company_add_jobs_1())
        }
        //        val recyclerView = view.findViewById<RecyclerView>(R.id.recyle_company_jobs)

//        layoutManager = LinearLayoutManager(activity)
//        recyclerView.layoutManager = layoutManager
//        adapter = Companyjobs_adapter(list)
//        recyclerView.adapter = adapter

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
}