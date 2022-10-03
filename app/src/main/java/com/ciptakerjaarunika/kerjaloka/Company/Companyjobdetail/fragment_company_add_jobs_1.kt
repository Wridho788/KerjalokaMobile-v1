package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
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
    ): ConstraintLayout {
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
        binding.editPositionJob.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (!binding.editPositionJob.text.toString()
                        .isNullOrEmpty() && !binding.editPositionJob.text.toString()
                        .isNullOrBlank() && binding.editPositionJob.text.toString() != ""
                ) {
                    iAddJob.addPosition(binding.editPositionJob.text.toString())
                }
            }
        })
        binding.editSalaryJob.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (!binding.editSalaryJob.text.toString()
                        .isNullOrEmpty() && !binding.editSalaryJob.text.toString()
                        .isNullOrBlank() && binding.editSalaryJob.text.toString() != ""
                ) {
                    iAddJob.addSalary(binding.editSalaryJob.text.toString())
                }

            }
        })
        if (!getLocation!!.isEmpty() &&
            !getLocation!!.isNullOrEmpty() &&
            getTypeJobs!! == null) {
            iAddJob.addJobPage1(getLocation!!, 0)
        } else Log.d("getlocation", getLocation!!.toString())

        binding.btnSelanjutnyaCmpny.setOnClickListener {
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

    override fun updatePage1(locations: List<LocationFilter>?) {
        locations!!.map { location ->
            binding.compnyLokasi.text = location.city + "," + location.province
        }
        getLocation = locations
    }

    override fun updatePageType(typeNo: Int?, type: String?) {
        binding.compnyJobType.text = type
        getTypeJobs = typeNo
    }
}

interface iUpdatePage1 {
    fun updatePage1(locations: List<LocationFilter>? = null)
    fun updatePageType(typeNo: Int?, type: String?)
}
