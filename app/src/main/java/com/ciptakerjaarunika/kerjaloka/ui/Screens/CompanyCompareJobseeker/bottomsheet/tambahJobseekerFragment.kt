package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyCompareJobseeker.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyCompareJobseeker.bottomsheet.Adapter.AddJobseekerAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyCompareJobseeker.bottomsheet.Model.AddJobseekerModel


class tambahJobseekerFragment : SuperBottomSheetFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tambah_jobseeker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = ArrayList<AddJobseekerModel>()
        val list1 = AddJobseekerModel(
            1,
            "Jhon doe",
            "Medan",
            "IT",
            "Medan",
            "S1",
            "UPH"
        )
        val list2 = AddJobseekerModel(
            2,
            "Jhon doe",
            "Medan",
            "IT",
            "Medan",
            "S1",
            "UPH"
        )
        val list3 = AddJobseekerModel(
            3,
            "Jhon doe",
            "Medan",
            "IT",
            "Medan",
            "S1",
            "UPH"
        )
        val list4 = AddJobseekerModel(
            4,
            "Jhon doe",
            "Medan",
            "IT",
            "Medan",
            "S1",
            "UPH"
        )
        val list5 = AddJobseekerModel(
            5,
            "Jhon doe",
            "Medan",
            "IT",
            "Medan",
            "S1",
            "UPH"
        )

        list.add(list1)
        list.add(list2)
        list.add(list3)
        list.add(list4)
        list.add(list5)


        val rv_applicant_list = view.findViewById<RecyclerView>(R.id.list_jobseeker)
        rv_applicant_list.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = AddJobseekerAdapter(list)
        }

    }

}