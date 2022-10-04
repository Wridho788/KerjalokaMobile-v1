package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.adapter.Companyjobs_adapter
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.model.ResponseCompanyJobs
import com.ciptakerjaarunika.kerjaloka.MainActivity
import com.ciptakerjaarunika.kerjaloka.api.companyAddJob.CompanyJobAPI
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentCompanyJobsBinding


class fragment_company_jobs : Fragment() {

    private lateinit var binding: FragmentCompanyJobsBinding
    private var listJob: List<ResponseCompanyJobs> ? = null
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

        binding.idFABAdd.setOnClickListener {
            val myIntent = Intent(view.context, AddJobActivity::class.java)
            startActivity(myIntent)
        }

        binding.backButton.setOnClickListener {
            val goToMainActivity = Intent(view.context, MainActivity::class.java)
            startActivity(goToMainActivity)
        }

        CompanyJobAPI().getCompanyJobOfficer(context){
            if (it != null) {
                listJob = it.data
                binding.recyleCompanyJobs.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = Companyjobs_adapter(listJob!!)
                }

            }
        }
    }
}