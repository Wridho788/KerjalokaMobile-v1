package com.ciptakerjaarunika.kerjaloka.viewmodel.Company.CompanyApplicant.CVbanks

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.companyApplicant.CompanyListApplicantAPI
import com.ciptakerjaarunika.kerjaloka.api.companyApplicant.CompanyOfficerJobsApi
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentCVbanksBinding
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.CompanyApplicant.ApplicantDetail.ApplicantDetailFragment
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.CompanyApplicant.CVbanks.Adapter.ApplicantCVBankAdapter
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.CompanyApplicant.JobApplicant.Model.applicantModel

class CVbanks : Fragment(), iCvBankInterface {
    private lateinit var binding: FragmentCVbanksBinding
    private var listJob: List<applicantModel>? = null
    private var list: List<applicantModel>? = null
    var jobno: Long = 0
    private var loading = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCVbanksBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvListApplicant = view.findViewById<RecyclerView>(R.id.rv_list_applicant)
        val toolbar = view.findViewById<ImageView>(R.id.btn_back_job)
        toolbar.setOnClickListener {
            fragmentManager?.popBackStack()
        }
        CompanyOfficerJobsApi().GetCVBanksList(context) {
            if (it != null) {
                loading -= 1
                LoadingDone()
                listJob = it.data
                Log.d("list", listJob.toString())
                rvListApplicant.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = ApplicantCVBankAdapter(context, listJob, this@CVbanks)
                }
            }

        }
    }

    fun LoadingDone() {
        if (loading == 0) {
            binding.spinner.visibility = View.GONE
            binding.contentContainer.visibility = View.VISIBLE
        }
    }

    override fun goToJobApplicant(jobNo: Long, jobseekerNo: Long) {
        CompanyListApplicantAPI().GetListApplicantPost(context, jobNo.toString()) {
            if (it != null) {
                list = it.data
                var temp  = list!!.find { data -> data.applicant.jobseekerNo == jobseekerNo }
                var applicantObj : applicantModel = temp!!
                val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
                ft.replace(id, ApplicantDetailFragment(applicantObj, null, this@CVbanks), "CompanyApplicant")
                ft.addToBackStack("CompanyApplicant")
                ft.commit()
            }
        }


    }
}

interface iCvBankInterface {
    fun goToJobApplicant(jobNo: Long, jobseekerNo: Long)
}