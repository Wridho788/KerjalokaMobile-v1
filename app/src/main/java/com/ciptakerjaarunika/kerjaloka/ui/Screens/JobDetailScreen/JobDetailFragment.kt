package com.ciptakerjaarunika.kerjaloka.ui.Screens.JobDetailScreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.JobAPI
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.databinding.ActivityMainBinding
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobDetailScreen.Adapter.RelatedJobAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobDetailScreen.Adapter.RelatedOtherJobAdapter
import com.google.android.material.appbar.MaterialToolbar

class JobDetailFragment(private val JobNo: Long, private val CompanyNo: Long) : Fragment(),
    OnFragmentClickListener {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var layoutManager2: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RelatedJobAdapter.ViewHolder>? = null
    private var adapter2: RecyclerView.Adapter<RelatedOtherJobAdapter.ViewHolder>? = null
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setContentView(root: ConstraintLayout) {
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_job_detail, container, false)
        val btn_applyJob = view.findViewById<View>(R.id.apply_job_button)
        val report_job = view.findViewById<View>(R.id.report_job)

        val company_logo = view.findViewById<ImageView>(R.id.logo_company)
        var job_position = view.findViewById<TextView>(R.id.jobTitle)
        val company_name = view.findViewById<TextView>(R.id.jobCompany)
        val job_location = view.findViewById<TextView>(R.id.jobLocation)
        val job_qualications = view.findViewById<TextView>(R.id.jobQualification)
        val job_experience = view.findViewById<TextView>(R.id.jobExperience)
        val job_field = view.findViewById<TextView>(R.id.jobField)
        val job_role = view.findViewById<TextView>(R.id.jobRole)


        val Context = this
        val fetch = config().portAddress + "/job/" + CompanyNo + "/" + JobNo + "/visitor"
        Log.d("fetch", fetch.toString())
        JobAPI().getJobDetailAsync(CompanyNo, JobNo) {
            Log.d("Response Job Detail", it.toString())
            if (it != null) {
                Glide.with(this)
                    .load(config().portAddress + "/photo/Profile/" + it.data.company.logo)
                    .fitCenter().into(company_logo)
                job_position.text = it.data.jobPosition
                job_location.text = it.data.jobLocation[0].label
                company_name.text = it.data.company.companyName
                job_qualications.text =  it.data.jobTitle.joinToString { data-> data.titleName +" " }
                if (it.data.jobMinExperience == 0) {
                    job_experience.text = it.data.jobMinExperience.toString()
                } else {
                    job_experience.text = it.data.jobMinExperience.toString() + " Tahun"
                }
                job_field.text = it.data.jobField.fieldName
                job_role.text = it.data.jobRole.jobRoleName
            }
        }

        btn_applyJob.setOnClickListener {
            Toast.makeText(context, "Apply Job", Toast.LENGTH_SHORT).show()
        }

        report_job.setOnClickListener {
            Toast.makeText(context, "Report Job", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar) as MaterialToolbar
        val toolbarBookmark = view.findViewById<ImageView>(R.id.toolbar_bookmark) as ImageView
        val toolbarShare = view.findViewById<ImageView>(R.id.toolbar_share) as ImageView
//        val Context = this
        val recyclerView =
            view.findViewById<RecyclerView>(R.id.recycler_view_recommendation_jobs) as RecyclerView
        val recyclerView2 =
            view.findViewById<RecyclerView>(R.id.recycler_view_related_jobs) as RecyclerView

        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        layoutManager2 = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager
        recyclerView2.layoutManager = layoutManager2
        adapter = RelatedJobAdapter()
        adapter2 = RelatedOtherJobAdapter()

        recyclerView.adapter = adapter
        recyclerView2.adapter = adapter2


        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
        toolbarBookmark.setOnClickListener {
            Toast.makeText(context, "Bookmark", Toast.LENGTH_SHORT).show()
        }
        toolbarShare.setOnClickListener {
            Toast.makeText(context, "Share", Toast.LENGTH_SHORT).show()
        }

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)

    }

    override fun onFragmentClick() {
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.replace(id, JobDetailFragment(JobNo, CompanyNo), "jobDetailFragment")
        ft.addToBackStack("jobDetailFragment")
        ft.commit()
    }

    companion object {

//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            JobDetailFragment(JobNo).apply {
//
//            }
    }
}

interface OnFragmentClickListener {
    fun onFragmentClick()
}