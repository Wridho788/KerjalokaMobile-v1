package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentCompanyAddJobs3Binding

class fragment_company_add_jobs_3(val iAddJob: iAddJob) : Fragment() {
    private lateinit var binding: FragmentCompanyAddJobs3Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCompanyAddJobs3Binding.inflate(layoutInflater)
        val view = binding.root
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.btnKembaliCmpny.setOnClickListener {
            replaceFragment(fragment_company_add_jobs_2(this.iAddJob))
        }
        iAddJob.addJobPage3(binding.compnyInsertDes.text.toString())

        binding.btnSelanjutnyaCmpny.setOnClickListener {
            replaceFragment(fragment_company_add_jobs_4(this.iAddJob))
        }

        return view
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(id, fragment)
        fragmentTransaction?.commit()
    }
}