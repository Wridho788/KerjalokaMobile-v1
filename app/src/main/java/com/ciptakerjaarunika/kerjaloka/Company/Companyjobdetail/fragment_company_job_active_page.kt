package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.model.JobCity
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.model.ResponseJobs
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.model.analytic
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.model.item
import com.ciptakerjaarunika.kerjaloka.R

class fragment_company_job_active_page : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_company_job_active_page, container, false)

        val jobTitle = view.findViewById<TextView>(R.id.company_job_title)
        val jobInput = view.findViewById<TextView>(R.id.company_job_input)
        val jobExpired = view.findViewById<TextView>(R.id.company_job_expired)
        val jobAuth = view.findViewById<TextView>(R.id.company_job_author)
        val jobtime = view.findViewById<TextView>(R.id.company_job_time)
        val jobView = view.findViewById<TextView>(R.id.company_job_viewed)
        val jobReq = view.findViewById<TextView>(R.id.job_req)
        val jobSalary = view.findViewById<TextView>(R.id.company_salary)
        val jobQual = view.findViewById<TextView>(R.id.company_qualification)
        val jobMined = view.findViewById<TextView>(R.id.company_min_ed)
        val jobType = view.findViewById<TextView>(R.id.company_type_job)
        val jobRole = view.findViewById<TextView>(R.id.company_role)
        val jobLoc = view.findViewById<TextView>(R.id.company_loc)



        val listitem = ArrayList<item>()
        val list = ArrayList<ResponseJobs>()
        val citylist = ArrayList<JobCity>()
        val city1 = JobCity(
            id=0,
            cityname = "Kota Medan"
        )
        citylist.add(city1)
        val job1 = ResponseJobs(
            createdBy ="reyhan@kerjaloka.com",
            createdOn ="2022-08-03T10:56:24",
            expired ="2022-09-02T00:00:00",
            jobAdditionalDescription =null,
            jobCity =citylist,
            jobDescription ="<ul><li>Crosscheck cashflow, GL Accounting, and balance sheet</li><li>Financial overview per month</li><li>Manage Petty Cash</li><li>Prepare required document of daily banking transaction</li><li>Monitoring &amp; report export proceeds and import payment through SiMoDIS</li><li>Reconcile all of bank account every day</li><li>Update payment in SAP</li></ul><p><br></p>",
            jobExperienceLevel =null,
            jobField =null,
            jobMinExperience =12,
            jobNo ="4120220803105624",
            jobPosition ="Testing Baru",
            jobRole =null,
            jobSalaryMax =null,
            jobSalaryMin =1223333,
            jobShortQuestion =null,
            jobSkills =null,
            jobTests =null,
            jobTitle =null,
            jobType =null,
            link ="https://advance.kerjaloka.com/job/TESTING/4120220803105624",
            packageName =null,
            publish =false,
            takedown =false
        )
        list.add(job1)

        val listanalytic = ArrayList<analytic>()
        val anl1 = analytic(
            clickCount= 21,
        totalDuration = 0,
        averageDuration = 0
        )
        listanalytic.add(anl1)

        jobTitle.text=list[0].jobPosition
        jobInput.text=list[0].createdOn
        jobExpired.text=list[0].expired
        jobAuth.text=list[0].createdBy
        jobLoc.text=list[0].jobCity[0].cityname
        jobtime.text=list[0].createdOn
        jobView.text=listanalytic[0].clickCount.toString()
        jobReq.text=list[0].jobDescription
        jobSalary.text=list[0].jobSalaryMin.toString()+" - "+list[0].jobSalaryMax.toString()
        jobQual.text=list[0].jobTitle.toString()
        jobMined.text=list[0].jobTitle.toString()
        jobType.text=list[0].jobField?.fieldName
        jobRole.text=list[0].jobRole?.jobRoleName
        return view
    }

    companion object {

    }
}