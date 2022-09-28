package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail

import android.content.Intent
import android.os.Bundle
import android.text.format.DateUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.adapter.Companyjobs_adapter
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.adapter.JobSQListAdapter
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.adapter.JobTestListAdapter
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.model.Data
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.model.JobTitle
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.JobAPI
import com.ciptakerjaarunika.kerjaloka.ui.Global.otpVerification
import com.google.android.material.button.MaterialButton
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class fragment_company_job_active_page : Fragment() {

    var jobData: Data? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var tadapter: RecyclerView.Adapter<JobTestListAdapter.ViewHolder>? = null
    private var layoutManager1: RecyclerView.LayoutManager? = null
    private var sqadapter: RecyclerView.Adapter<JobSQListAdapter.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val jobTitle = view.findViewById<TextView>(R.id.company_job_title)
        val jobInput = view.findViewById<TextView>(R.id.company_job_input)
        val jobExpired = view.findViewById<TextView>(R.id.company_job_expired)
        val jobAuth = view.findViewById<TextView>(R.id.company_job_author)
        val jobtime = view.findViewById<TextView>(R.id.company_job_time)
        val jobView = view.findViewById<TextView>(R.id.company_job_viewed)
        val jobReq = view.findViewById<TextView>(R.id.job_req)
        val jobSalary = view.findViewById<TextView>(R.id.company_salary)
        val jobQual = view.findViewById<TextView>(R.id.company_qualification)
        val jobMinex = view.findViewById<TextView>(R.id.company_min_ex)
        val jobType = view.findViewById<TextView>(R.id.company_type_job)
        val jobRole = view.findViewById<TextView>(R.id.company_role)
        val jobLoc = view.findViewById<TextView>(R.id.company_loc)
        val testRecycle = view.findViewById<RecyclerView>(R.id.recycleTest)
        val sqRecycle = view.findViewById<RecyclerView>(R.id.questionRecycle)
        val shareJob = view.findViewById<MaterialButton>(R.id.btn_job_share)
        val draftJob = view.findViewById<MaterialButton>(R.id.btn_job_draft)
        val publishJob = view.findViewById<MaterialButton>(R.id.btn_job_publish)

        if (arguments != null){
            val descFromBundle = arguments?.getString(fragment_company_job_active_page.EXTRA_DETAIL_JOB)
            jobData = Gson().fromJson(descFromBundle, Data::class.java)
            jobTitle.text=jobData?.jobPosition
            jobInput.text="Diubah pada : "+jobData?.createdOn
            jobExpired.text="Kadaluarsa : "+jobData?.expired
            jobAuth.text="Oleh : " + jobData?.createdBy
            jobLoc.text=jobData?.jobCity?.toString()
//            jobView.text=listanalytic[0].clickCount.toString()
            jobReq.text=jobData?.jobDescription
            if (jobData?.jobSalaryMin!=null || jobData?.jobSalaryMax!=null){
                jobSalary.text=jobData?.jobSalaryMin.toString()+" - "+jobData?.jobSalaryMax.toString()
            }
            else{
                jobSalary.text="-"
            }
            jobData?.jobTitle?.forEach {
                jobQual.text= jobQual.text.toString()+it.titleName+", "
            }
            jobMinex.text=jobData?.jobMinExperience.toString()
            jobType.text=jobData?.jobField?.fieldName
            jobRole.text=jobData?.jobRole?.jobRoleName

            val sdf = SimpleDateFormat("yyyy-MM-dd")
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+7"))
            val time: Long = sdf.parse(jobData?.createdOn.toString()).getTime()
            val now = System.currentTimeMillis()
            val ago = DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS)
            jobtime.text=ago
            var testList = jobData?.jobTests
            layoutManager = LinearLayoutManager(activity)
            testRecycle.layoutManager = layoutManager
            tadapter = testList?.let { JobTestListAdapter(it) }
            testRecycle.adapter = tadapter

            var sqList = jobData?.jobShortQuestion
            layoutManager1 = LinearLayoutManager(activity)
            sqRecycle.layoutManager = layoutManager1
            sqadapter = sqList?.let { JobSQListAdapter(it) }
            sqRecycle.adapter = sqadapter

            val titleJob = jobData?.jobPosition
            val link = jobData?.link
            shareJob.setOnClickListener {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TITLE, titleJob)
                    putExtra(Intent.EXTRA_TEXT, link)
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }

            if (jobData?.publish==true){
                draftJob.isVisible=true
            }
            else{
                publishJob.isVisible=true
            }

            val jobNo = jobData?.jobNo
            draftJob.setOnClickListener{
                jobNo?.let { it1 -> JobAPI().DraftJob(context, it1){} }
            }
            publishJob.setOnClickListener{
                jobNo?.let { it1 -> JobAPI().PublishJob(context, it1){} }
            }

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_company_job_active_page, container, false)
        return view
    }

    companion object {
        var EXTRA_DETAIL_JOB = "extra_detailJob"
    }
}