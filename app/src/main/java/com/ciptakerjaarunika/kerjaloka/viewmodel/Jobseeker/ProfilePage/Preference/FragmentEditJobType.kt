package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.Preference

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ciptakerjaarunika.kerjaloka.`interface`.iRefreshData
import com.ciptakerjaarunika.kerjaloka.api.DataAPI
import com.ciptakerjaarunika.kerjaloka.api.ManageProfileAPI
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentEditJobtypeLayoutBinding
import com.ciptakerjaarunika.kerjaloka.model.Data.JobType
import com.ciptakerjaarunika.kerjaloka.model.Data.JobTypeFilter
import com.ciptakerjaarunika.kerjaloka.model.Profile.JobseekerJobTypes
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.Adapter.JobTypeAdapter
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.profilepage

class FragmentEditJobType(var dataList: List<JobType>?, val iRefreshData: iRefreshData) :
    Fragment(), iEditJobType {
    private lateinit var binding: FragmentEditJobtypeLayoutBinding
    private var jobTypes: List<JobTypeFilter> = listOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditJobtypeLayoutBinding.inflate((layoutInflater))
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backBtn.setOnClickListener {
            back()
        }
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            back()
        }


        getData()

        binding.saveBtn.setOnClickListener {
            val requestData = jobTypes.filter { item -> item.checked == true }.map { item ->
                JobseekerJobTypes(
                    SessionManager(context).user!!.userNo,
                    item.jobTypeNo
                )
            }
            Log.d("requestData", requestData.toString())

            ManageProfileAPI().JobseekerEditJobTypes(requestData, context) {
                if (it != null) {
                    Toast.makeText(activity, "Berhasil mengubah data", Toast.LENGTH_SHORT).show()
                    back()
                } else {
                    Toast.makeText(
                        activity,
                        "Terjadi kesalahan yang tidak diketahui",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    fun getData(){
        DataAPI().GetJobTypes(context) { data ->
            if (data != null) {
                jobTypes = data.map { item ->
                    JobTypeFilter(item.jobTypeName, item.jobTypeNo,
                        dataList?.any { temp -> temp.jobTypeNo == item.jobTypeNo } == true)
                }

                binding.recycleview.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = JobTypeAdapter(context, jobTypes, this@FragmentEditJobType)
                }
            }
        }
    }

    private fun back() {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(id, profilepage(2), "Profile Page")
        fragmentTransaction.commit()
        fragmentTransaction.detach(this)
        fragmentTransaction.attach(this)
        fragmentManager?.popBackStack()

        iRefreshData.refresh()
    }

    override fun refreshRecyCleview() {
        binding.recycleview.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = JobTypeAdapter(context, jobTypes, this@FragmentEditJobType)
        }
        binding.recycleview.adapter?.notifyDataSetChanged()
    }
}

interface iEditJobType {
    fun refreshRecyCleview()
}