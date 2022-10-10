package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.Listener.JobDetail
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.adapter.Companyjobs_adapter
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.model.Data
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.model.ResponseCompanyJobs
import com.ciptakerjaarunika.kerjaloka.MainActivity
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.JobAPI
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentCompanyJobsBinding
import com.google.gson.Gson


class fragment_company_jobs : Fragment() {

    private lateinit var binding: FragmentCompanyJobsBinding
    private var listJob: List<ResponseCompanyJobs>? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCompanyJobsBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onResume() {
        super.onResume()
        UpdateUI()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        UpdateUI()
    }


    private fun assignAdapter(list: List<Data>): Companyjobs_adapter {
        return Companyjobs_adapter(list, object : JobDetail {
            override fun jobDetail(jobDetail: Data) {
                replaceFragment(jobDetail)
            }

            override fun shareJob(shareJob: Data) {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TITLE, shareJob.jobPosition)
                    putExtra(Intent.EXTRA_TEXT, shareJob.link)
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }
        })
    }
    fun UpdateUI(){

        binding.idFABAdd.setOnClickListener {
            val myIntent = Intent(view?.context, ManageJobActivity::class.java)
            startActivity(myIntent)
        }
        binding.backButton.setOnClickListener {
            val goToMainActivity = Intent(view?.context, MainActivity::class.java)
            startActivity(goToMainActivity)
        }

        JobAPI().getJob(context){
            if(it != null) {
                val recyclerView = view?.findViewById<RecyclerView>(R.id.recyle_company_jobs)
                recyclerView?.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = it.data.let { it1 -> assignAdapter(it1) }
                }
            }
        }
    }

    private fun replaceFragment(data: Data?) {
        val jobDetailFragment = fragment_company_job_active_page()
        val mBundle = Bundle()
        val jobData = Gson().toJson(data)
        mBundle.putString(fragment_company_job_active_page.EXTRA_DETAIL_JOB, jobData)
        jobDetailFragment.arguments = mBundle
        val mFragmentManager = parentFragmentManager
        mFragmentManager.beginTransaction()?.apply {
            replace(
                R.id.fragment_container,
                jobDetailFragment,
                fragment_company_job_active_page::class.java.simpleName
            )
            addToBackStack(null)
            commit()

        }
    }
}