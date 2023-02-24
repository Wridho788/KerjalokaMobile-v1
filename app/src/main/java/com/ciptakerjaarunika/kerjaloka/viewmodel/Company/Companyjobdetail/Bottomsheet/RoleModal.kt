package com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Companyjobdetail.Bottomsheet

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.model.Data.Roles
import com.ciptakerjaarunika.kerjaloka.model.Job.CompanyJobDetail.*
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Companyjobdetail.Bottomsheet.Adapter.RoleAdapter
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Companyjobdetail.ManageJobPage.iUpdateJobAdditionalInfo

class RoleModal(
    var data: JobRole?,
    private val dataList: List<Roles>,
    private val updateData: iUpdateJobAdditionalInfo
) : SuperBottomSheetFragment(),
    iUpdateJobRole {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val view = View.inflate(context, R.layout.global_modal_edit, null)
        view.findViewById<TextView>(R.id.judul_bottom_sheet).text = "Pilih Posisi Pekerjaans"
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycleEdit)

        var searchInput = view.findViewById<SearchView>(R.id.search_filter)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = RoleAdapter(data?.jobRoleNo, dataList, this@RoleModal)
        }
        return view
    }

    override fun isSheetAlwaysExpanded(): Boolean {
        return true
    }

    @SuppressLint("Range")
    override fun getExpandedHeight() = ViewGroup.LayoutParams.WRAP_CONTENT

    override fun updateJobRole(value: JobRole) {
        updateData.updateRole(value)
        this.dismiss()
    }
}

interface iUpdateJobRole {
    fun updateJobRole(value: JobRole)
}