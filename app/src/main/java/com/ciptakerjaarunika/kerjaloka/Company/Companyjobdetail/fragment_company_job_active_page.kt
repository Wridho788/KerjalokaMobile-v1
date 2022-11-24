package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.adapter.JobSQListAdapter
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.adapter.JobTestListAdapter
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.model.DataActiveJob
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.JobAPI
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.google.android.material.button.MaterialButton
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*

class fragment_company_job_active_page : Fragment() {

    var jobData: DataActiveJob? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var tadapter: RecyclerView.Adapter<JobTestListAdapter.ViewHolder>? = null
    private var layoutManager1: RecyclerView.LayoutManager? = null
    private var sqadapter: RecyclerView.Adapter<JobSQListAdapter.ViewHolder>? = null

    override fun onResume() {
        super.onResume()
        JobAPI().getJob(context) {
            if (it != null) {
                if (jobData != null) {
                    jobData = it.data.find { data -> data.jobNo == jobData!!.jobNo }
                }
                UpdateUI()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        UpdateUI()
    }

    fun UpdateUI() {
        val jobTitle = view?.findViewById<TextView>(R.id.company_job_title)
        val jobInput = view?.findViewById<TextView>(R.id.company_job_input)
        val jobExpired = view?.findViewById<TextView>(R.id.company_job_expired)
        val jobAuth = view?.findViewById<TextView>(R.id.company_job_author)
        val jobtime = view?.findViewById<TextView>(R.id.company_job_time)
        val jobView = view?.findViewById<TextView>(R.id.company_job_viewed)
        val imgJob = view?.findViewById<ImageView>(R.id.img_job)
        val jobReq = view?.findViewById<TextView>(R.id.job_req)
        val jobSalary = view?.findViewById<TextView>(R.id.company_salary)
        val jobQual = view?.findViewById<TextView>(R.id.company_qualification)
        val jobMinex = view?.findViewById<TextView>(R.id.company_min_ex)
        val jobType = view?.findViewById<TextView>(R.id.company_type_job)
        val jobRole = view?.findViewById<TextView>(R.id.company_role)
        val jobLoc = view?.findViewById<TextView>(R.id.company_loc)
        val testRecycle = view?.findViewById<RecyclerView>(R.id.recycleTest)
        val sqRecycle = view?.findViewById<RecyclerView>(R.id.questionRecycle)
        val shareJob = view?.findViewById<MaterialButton>(R.id.btn_job_share)
        val draftJob = view?.findViewById<MaterialButton>(R.id.btn_job_draft)
        val publishJob = view?.findViewById<MaterialButton>(R.id.btn_job_publish)
        val btn_back = view?.findViewById<LinearLayout>(R.id.back_button)
        val btn_editJob = view?.findViewById<MaterialButton>(R.id.btn_edit_pekerjan)

        btn_back?.setOnClickListener {
            fragmentManager?.popBackStack()
        }

        if (imgJob != null) {
            Glide.with(view!!.context)
                .load(config().portAddress + "photo/Profile/" + SessionManager(context).user?.companyAdditional?.logo)
                .fitCenter()
                .into(imgJob)
        }



        btn_editJob?.setOnClickListener {
            val intentAddJob = Intent(context, ManageJobActivity::class.java)
            val bundle = Bundle()
            bundle.putString("jobNo", Gson().toJson(jobData?.jobNo))
            intentAddJob.putExtras(bundle)
            startActivity(intentAddJob)
        }

        if (arguments != null) {

            if (jobData == null) {
                val descFromBundle =
                    arguments?.getString(fragment_company_job_active_page.EXTRA_DETAIL_JOB)
                jobData = Gson().fromJson(descFromBundle, DataActiveJob::class.java)
            }
            Log.d("jobNo", jobData!!.jobNo.toString())
//            CompanyJobAPI().GetCompanyAnalytic(context, 8, jobData?.jobNo!!){
//                if (it != null) {
//                    Log.d("analytic", it.toString())
//                }
//            }
            jobTitle?.text = jobData?.jobPosition
            jobInput?.text = "Diubah pada : " + jobData?.createdOn
            jobExpired?.text = "Kadaluarsa : " + jobData?.expired
            jobAuth?.text = "Oleh : " + jobData?.createdBy

            var location = ""
            var listLocation: List<String> = listOf()

            jobData?.jobCity?.forEach {
                location += "&#8226; ${it}<br/>"
                listLocation = listOf(it)
            }
            Log.d("location", listLocation.toString())
            jobLoc?.text = Html.fromHtml(location)

            jobView?.text = "0 views"
            Log.d("jobdesc", jobData?.jobDescription.toString())
            if (jobData?.jobDescription != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    jobReq?.text = Html.fromHtml(
                        jobData?.jobDescription,
                        Html.FROM_HTML_MODE_COMPACT
                    )
                } else {
                    jobReq?.text = Html.fromHtml(jobData?.jobDescription)
                }
            } else {
                jobReq?.setText("")
            }
            if (jobData?.jobSalaryMin != null && jobData?.jobSalaryMin != null) {
                if (jobData?.jobSalaryMin == null) {
                    jobSalary?.text = "Rp. 0 -"
                }
                if (jobData?.jobSalaryMax == null) {
                    jobSalary?.text = "Rp. 0 -"
                }
                if (jobData?.jobSalaryMin != null || jobData?.jobSalaryMax != null) {
                    jobSalary?.text =
                        jobData?.jobSalaryMin.toString() + " - " + jobData?.jobSalaryMax.toString()
                } else {
                    jobSalary?.text = "-"
                }
            } else {
                jobSalary?.text = "-"
            }

            jobData?.jobTitle?.forEach {
                jobQual?.text = jobQual?.text.toString() + it.titleName + ", "
            }
            if (jobData?.jobMinExperience != null) {
                jobMinex?.text = jobData?.jobMinExperience.toString() + " tahun"
            } else {
                jobMinex?.text = "0 Tahun"
            }
            jobType?.text = jobData?.jobField?.fieldName
            jobRole?.text = jobData?.jobRole?.jobRoleName

            if (jobData?.createdOn != null) {
                val sdf = SimpleDateFormat("yyyy-MM-dd")
                sdf.timeZone = TimeZone.getTimeZone("GMT+7")
                val time: Long = sdf.parse(jobData?.createdOn.toString()).time
                val now = System.currentTimeMillis()
                val ago = DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS)
                jobtime?.text = ago
            }
            var testList = jobData?.jobTests
            layoutManager = LinearLayoutManager(activity)
            testRecycle?.layoutManager = layoutManager
            tadapter = testList?.let { JobTestListAdapter(it) }
            testRecycle?.adapter = tadapter

            var sqList = jobData?.jobShortQuestion
            layoutManager1 = LinearLayoutManager(activity)
            sqRecycle?.layoutManager = layoutManager1
            sqadapter = sqList?.let { JobSQListAdapter(it) }
            sqRecycle?.adapter = sqadapter

            val titleJob = jobData?.jobPosition
            val link = jobData?.link
            shareJob?.setOnClickListener {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TITLE, titleJob)
                    putExtra(Intent.EXTRA_TEXT, link)
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }

            if (jobData?.publish == true) {
                draftJob?.isVisible = true
            } else {
                publishJob?.isVisible = true
            }

            val jobNo = jobData?.jobNo
            Log.d("jobcity", jobData?.jobCity.toString())
            draftJob?.setOnClickListener {
                JobAPI().DraftJob(context, jobNo!!) {
                    if (it != null) {
                        if (it.code == 280) {
                            Toast.makeText(context, "Sukses Draft Job", Toast.LENGTH_SHORT).show()
                            publishJob?.isVisible = true
                            draftJob.isVisible = false
                        }
                    }
                }
            }
            publishJob?.setOnClickListener {
                JobAPI().PublishJob(context, jobNo!!) {
                    if (it != null) {
                        if (it.code == 280) {
                            Toast.makeText(context, "Sukses Publish Job", Toast.LENGTH_SHORT).show()
                            publishJob.isVisible = false
                            draftJob?.isVisible = true
                        }

                    }
                }
            }


        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_company_job_active_page, container, false)

        return view
    }

    companion object {
        var EXTRA_DETAIL_JOB = "extra_detailJob"
    }
}