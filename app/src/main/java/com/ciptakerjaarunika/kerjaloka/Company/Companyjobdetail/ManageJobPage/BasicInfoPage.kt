package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.ManageJobPage

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.Bottomsheet.Adapter.SelectedLocationAdapter
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.Bottomsheet.LocationModal
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.iBasicInfoPage
import com.ciptakerjaarunika.kerjaloka.api.DataAPI
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentJobBasicInfoBinding
import com.ciptakerjaarunika.kerjaloka.model.Data.JobType
import com.ciptakerjaarunika.kerjaloka.model.Data.JobTypeFilter
import com.ciptakerjaarunika.kerjaloka.model.Job.CompanyJobDetail.CompanyJobDetail
import com.ciptakerjaarunika.kerjaloka.model.Job.CompanyJobDetail.JobLocation
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ManageCV.iManageExp
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ModalEdit.EditExpTypeJob

class BasicInfoPage(var data: CompanyJobDetail, val updateData: iBasicInfoPage) : Fragment(),
    iUpdateJobBasicInfo, iManageExp {

    private lateinit var binding: FragmentJobBasicInfoBinding
    private var jobTypes: List<JobTypeFilter> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentJobBasicInfoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (data.jobPosition != null) binding.jobPositionTxt.setText(data.jobPosition)

        binding.recycleLocation.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = SelectedLocationAdapter(data.jobLocation, this@BasicInfoPage)
        }
        if (data.jobSalaryMax != null) binding.salaryMaxTxt.setText(data.jobSalaryMax.toString())
        else if (data.jobSalaryMin != null) binding.salaryMinTxt.setText(data.jobSalaryMin.toString())

        DataAPI().GetLocations(context) { res ->
            if (res != null) {
                binding.selectLocationBtn.setOnClickListener {
                    val sheet = LocationModal(data.jobLocation, res, this)
                    activity?.let { it1 ->
                        sheet.show(
                            it1.supportFragmentManager,
                            "DemoBottomSheetFragment"
                        )
                    }
                }
            }
        }
        DataAPI().GetJobTypes(context) { jobtypes ->
            if (jobtypes != null) {
                jobTypes = jobtypes
                if (data.jobType != null) {
                    updateJobType(data.jobType!!.jobTypeNo)
                }
                binding.selectJobTypeBtn.setOnClickListener {
                    val sheet = EditExpTypeJob(data.jobType?.jobTypeNo, jobtypes, this)
                    activity?.let { it1 ->
                        sheet.show(
                            it1.supportFragmentManager,
                            "DemoBottomSheetFragment"
                        )
                    }
                }
            }
        }


        binding.jobPositionTxt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                updateData.updateJobPosition(binding.jobPositionTxt.text.toString())
            }
        })

        binding.salaryMinTxt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                if (binding.salaryMinTxt.text.toString().isNullOrEmpty()
                    || binding.salaryMinTxt.text.toString().toInt() < 0
                ) {
                    updateData.updateJobSalary(null, null)
                } else
                    updateData.updateJobSalary(binding.salaryMinTxt.text.toString().toInt(), binding.salaryMaxTxt.text.toString().toInt())
            }
        })
        binding.salaryMaxTxt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                if (binding.salaryMaxTxt.text.toString().isNullOrEmpty()
                    || binding.salaryMaxTxt.text.toString().toInt() < 0
                ) {
                    updateData.updateJobSalary(null,null)
                } else
                    updateData.updateJobSalary(binding.salaryMinTxt.text.toString().toInt(), binding.salaryMaxTxt.text.toString().toInt())
            }
        })

    }

    override fun updateLocation(value: List<JobLocation>) {
        data.jobLocation = value
        binding.recycleLocation.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = SelectedLocationAdapter(data.jobLocation, this@BasicInfoPage)
        }
        binding.recycleLocation.adapter?.notifyDataSetChanged()
        updateData.updateJobLocation(value)
    }

    override fun updateMonth(value: Int, type: String) {
        TODO("Not yet implemented")
    }

    override fun updateYear(value: Int, type: String) {
        TODO("Not yet implemented")
    }

    override fun updateJobType(value: Int) {
        val currentValue = jobTypes.find { item -> item.jobTypeNo == value }
        data.jobType = JobType(currentValue!!.jobTypeName, currentValue.jobTypeNo)

        binding.jobTypeTxt.text = data.jobType!!.jobTypeName
        updateData.updateJobType(data.jobType!!)
    }
}

interface iUpdateJobBasicInfo {
    fun updateLocation(value: List<JobLocation>)
}
