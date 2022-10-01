package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.Bottomsheet.BottomSheetEditJob
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.Bottomsheet.BottomSheetTypeJob
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentCompanyAddJobs1Binding

class fragment_company_add_jobs_1 : Fragment(), iUpdatePage1 {
    private lateinit var binding: FragmentCompanyAddJobs1Binding
    var getLocation: String? = null
    var getTypeJobs: Int? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCompanyAddJobs1Binding.inflate(layoutInflater)
        val view = binding.root

        binding.backButton.setOnClickListener {
            activity?.onBackPressed()
        }
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)

//        binding.btnPostingPekerjaan.setOnClickListener {
//            Toast.makeText(context, "Posting Pekerjaan", Toast.LENGTH_SHORT).show()
//        }

        binding.btnChooseLocation.setOnClickListener {
            locationModal()
        }
        binding.btnChooseJobType.setOnClickListener {
            jobTypeModal()
        }

        if (getLocation != null) {
            Log.d("location list", getLocation?.toList().toString())

        }
        if (getTypeJobs != null) {
            Log.d("type list", getTypeJobs.toString())
        }
        binding.btnPostingPekerjaan.setOnClickListener {}

        binding.btnSelanjutnyaCmpny.setOnClickListener {
            Toast.makeText(context, "${getLocation}, ${getTypeJobs}Posting Pekerjaan", Toast.LENGTH_SHORT).show()
            replaceFragment(
                fragment_company_add_jobs_2()
            )
        }

        return view
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(id, fragment)
        fragmentTransaction?.commit()
    }

    fun locationModal() {
        val sheet = BottomSheetEditJob(this)
        activity?.let { it -> sheet.show(it.supportFragmentManager, "location") }
    }

    fun jobTypeModal() {
        val sheet = BottomSheetTypeJob(this)
        activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "DemoBottomSheetFragment") }
    }

    override fun updatePage1(location: String) {
        binding.compnyLokasi.text = location
        getLocation = location
    }

    override fun updatePageType(typeNo: Int, type: String) {
        binding.compnyJobType.text = type
        getTypeJobs = typeNo
    }
}

interface iUpdatePage1 {
    fun updatePage1(location: String)
    fun updatePageType(typeNo: Int, type: String)
}
