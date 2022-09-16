package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail.SectionStatusPage.BottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail.SectionStatusPage.BottomSheet.Adapter.StatusAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail.SectionStatusPage.BottomSheet.Model.statusModel

class UbahStatusFragment : SuperBottomSheetFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ubah_status, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = ArrayList<statusModel>()
        val list1 = statusModel(
            1, "Dalam Tes"
        )
        val list2 = statusModel(
            2, "interview"
        )
        list.add(list1)
        list.add(list2)

        val rv_status_list = view.findViewById<RecyclerView>(R.id.list_status_change)
        rv_status_list.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = StatusAdapter(list)
        }
    }
    override fun getCornerRadius() = 16f

}