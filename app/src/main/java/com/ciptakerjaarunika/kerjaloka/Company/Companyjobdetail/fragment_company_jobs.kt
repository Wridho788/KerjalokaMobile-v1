package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.adapter.Companyjobs_adapter
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.model.ResponseJobs
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.companyAddJob.CompanyJobAPI
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentCompanyJobsBinding


class fragment_company_jobs : Fragment() {

    private lateinit var binding: FragmentCompanyJobsBinding
    private var listJob: List<ResponseJobs> ? = null
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

        addjob.setOnClickListener {
            val myIntent = Intent(view.context, AddJobActivity::class.java)
            startActivity(myIntent)
        }

        CompanyJobAPI().getCompanyJobOfficer(context){
            if (it != null) {
                listJob = it.data
                Log.d("response", listJob.toString())
                binding.recyleCompanyJobs.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = Companyjobs_adapter(listJob!!)
                }

            }
        }
//      binding.recyleCompanyJobs.apply {
//
//      }
//        recyclerView.apply {
//            layoutManager = LinearLayoutManager(activity)
//            adapter = Companyjobs_adapter(list)
//
//        }

    }
}