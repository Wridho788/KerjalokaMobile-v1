package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.Bottomsheet.BottomSheetEditJob
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.Bottomsheet.BottomSheetTypeJob
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentCompanyAddJobs1Binding
import com.ciptakerjaarunika.kerjaloka.model.Data.LocationFilter

class fragment_company_add_jobs_1(val iAddJob: iAddJob) : Fragment(), iUpdatePage1 {
    private lateinit var binding: FragmentCompanyAddJobs1Binding
    var getLocation: List<LocationFilter>? = listOf()
    var getTypeJobs: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCompanyAddJobs1Binding.inflate(layoutInflater)
        val view = binding.root

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.btnChooseLocation.setOnClickListener {
            locationModal()
        }
        binding.btnChooseJobType.setOnClickListener {
            jobTypeModal()
        }

        binding.btnSelanjutnyaCmpny.setOnClickListener {
            iAddJob.addJobPage1(
                binding.editPositionJob.text.toString(),
                getLocation!!,
                getTypeJobs!!,
                binding.editSalaryJob.text.toString()
            )
                replaceFragment(
                    fragment_company_add_jobs_2(this.iAddJob)
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

    override fun updatePage1(locations: List<LocationFilter>) {
        locations.map { location ->
            binding.compnyLokasi.text = location.city + "," + location.province
        }
        getLocation = locations
    }

    override fun updatePageType(typeNo: Int, type: String) {
        binding.compnyJobType.text = type
        getTypeJobs = typeNo
    }
}

interface iUpdatePage1 {
    fun updatePage1(locatins: List<LocationFilter>)
    fun updatePageType(typeNo: Int, type: String)
}
