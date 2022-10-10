package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.Bottomsheet

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.Bottomsheet.Adapter.ExpLevelAdapter
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.ManageJobPage.iUpdateJobAdditionalInfo
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.model.Data.ExperienceLevelFilter
import com.ciptakerjaarunika.kerjaloka.model.Job.CompanyJobDetail.JobExperienceLevel

class ExperienceLevelModal(var data : Int?, val dataList: List<ExperienceLevelFilter>,private val updateData: iUpdateJobAdditionalInfo ) : SuperBottomSheetFragment(), iUpdateExperienceLevel{

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val view = View.inflate(context, R.layout.global_modal_edit, null)
        view.findViewById<TextView>(R.id.judul_bottom_sheet).text = "Pilih Posisi Pekerjaans"
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycleEdit)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = ExpLevelAdapter(data, dataList, this@ExperienceLevelModal)
        }
        return view
    }

    override fun isSheetAlwaysExpanded(): Boolean {
        return true
    }

    override fun updateExperienceLevel(value: JobExperienceLevel) {
        updateData.updateExperienceLevel(value)
        this.dismiss()
    }

    @SuppressLint("Range")
    override fun getExpandedHeight() = ViewGroup.LayoutParams.WRAP_CONTENT
}
interface iUpdateExperienceLevel{
    fun updateExperienceLevel(value : JobExperienceLevel)
}