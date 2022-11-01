package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ListApplicant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.companyApplicant.CompanyOfficerJobsApi
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.CVbanks.CVbanks
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.JobApplicant.JobApplicantFragment
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ListApplicant.Adapter.ListApplicantAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ListApplicant.Model.listApplicantJobModel
import com.google.android.material.card.MaterialCardView

class CompanyListApplicantFragment : Fragment(), OnFragmentClickListener {
    private var listJob: List<listApplicantJobModel>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_company_list_applicant, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val Context = this

        val rv_applicantJob = view.findViewById<RecyclerView>(R.id.rv_list_applicant_job)
        val txt_total_cv_banks = view.findViewById<TextView>(R.id.totalCVbanksText)
        val btn_cv_banks = view.findViewById<MaterialCardView>(R.id.btn_cv_banks)

        btn_cv_banks.setOnClickListener {
            val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
            ft.replace(id, CVbanks(), "CompanyApplicant")
            ft.addToBackStack("CompanyApplicant")
            ft.commit()
        }

        CompanyOfficerJobsApi().CompanyOfficerJob(context) {
            if (it != null) {
                listJob = it.data
                rv_applicantJob.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter =
                        ListApplicantAdapter(context, listJob, this@CompanyListApplicantFragment)
                }
            }
        }

        CompanyOfficerJobsApi().GetCVBanks(context) {
            if (it != null) {
                txt_total_cv_banks.text = it.data.toString()
            }
        }


    }

    override fun goToListJobApplicant(JobNo: Long) {
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.replace(id, JobApplicantFragment(JobNo), "CompanyApplicant")
        ft.addToBackStack("CompanyApplicant")
        ft.commit()
    }
}

interface OnFragmentClickListener {
    fun goToListJobApplicant(JobNo: Long)
}
