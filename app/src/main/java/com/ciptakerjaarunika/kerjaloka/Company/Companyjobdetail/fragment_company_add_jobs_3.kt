package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentCompanyAddJobs3Binding

class fragment_company_add_jobs_3 : Fragment() {
    private lateinit var binding: FragmentCompanyAddJobs3Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCompanyAddJobs3Binding.inflate(layoutInflater)
        val view = binding.root

        binding.backButton.setOnClickListener {
            replaceFragment(fragment_company_add_jobs_2())
        }

        binding.btnPostingPekerjaan.setOnClickListener {
            Toast.makeText(context, "Posting Pekerjaan", Toast.LENGTH_SHORT).show()
        }

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.btnKembaliCmpny.setOnClickListener {
            replaceFragment(fragment_company_add_jobs_2())
        }

        binding.btnSelanjutnyaCmpny.setOnClickListener {
            replaceFragment(fragment_company_add_jobs_4())
        }

        binding.compnyInsertDes.text.toString()

        return view
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(id, fragment)
        fragmentTransaction?.commit()
    }
}