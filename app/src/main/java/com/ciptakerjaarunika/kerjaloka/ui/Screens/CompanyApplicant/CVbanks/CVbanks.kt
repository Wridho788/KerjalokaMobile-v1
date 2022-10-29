package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.CVbanks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.companyApplicant.CompanyOfficerJobsApi
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentCVbanksBinding
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.CVbanks.Adapter.ListApplicantCVAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ListApplicant.Model.listApplicantJobModel

class CVbanks : Fragment() {
    private lateinit var binding: FragmentCVbanksBinding
    private var listJob: List<listApplicantJobModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_c_vbanks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rv_applicantCV = view.findViewById<RecyclerView>(R.id.rv_list_applicant_job)

        CompanyOfficerJobsApi().GetCVBanksList(context){
            if (it != null){
                listJob = it.data
                rv_applicantCV.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = ListApplicantCVAdapter(context, listJob)
                }
            }

        }
    }
}