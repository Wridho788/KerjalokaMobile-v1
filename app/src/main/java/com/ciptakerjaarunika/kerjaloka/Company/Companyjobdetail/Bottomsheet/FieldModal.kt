package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.Bottomsheet

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.Bottomsheet.Adapter.FieldAdapter
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.Bottomsheet.Adapter.TitleAdapter
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.ManageJobPage.iUpdateJobAdditionalInfo
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.DataAPI
import com.ciptakerjaarunika.kerjaloka.model.Data.FieldFilter
import com.ciptakerjaarunika.kerjaloka.model.Data.JobTypeFilter
import com.ciptakerjaarunika.kerjaloka.model.Job.CompanyJobDetail.JobField
import com.ciptakerjaarunika.kerjaloka.ui.HomePage.Model.jobField
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter.EditExp_TypeJob
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter.JobTypeAdapter
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ManageCV.iManageExp


class FieldModal(val value: Int?, val data : List<FieldFilter>, val updateData : iUpdateJobAdditionalInfo): SuperBottomSheetFragment(), iUpdateField {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = View.inflate(context, R.layout.global_modal_edit, null)
        val title = view.findViewById<TextView>(R.id.judul_bottom_sheet)
        title.text = "Pilih Tipe Bidang Pekerjaan"

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycleEdit)
                recyclerView.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = FieldAdapter(value, data, this@FieldModal)
                }

        var searchInput = view.findViewById<SearchView>(R.id.search_filter)
        searchInput.visibility = View.VISIBLE

        searchInput.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val keyword = newText.toString().toLowerCase()
                if (keyword.isNullOrEmpty()) {
                    recyclerView.apply {
                        layoutManager = LinearLayoutManager(activity)
                        adapter = FieldAdapter(value, data, this@FieldModal)
                    }
                    recyclerView.adapter?.notifyDataSetChanged()
                } else {
                    var temp = data?.filter { item ->
                        "${item.fieldName}".toLowerCase().contains(keyword)
                    }
                    recyclerView.apply {
                        layoutManager = LinearLayoutManager(activity)
                        adapter = temp?.let { FieldAdapter(value, it, this@FieldModal) }
                    }
                    recyclerView.adapter?.notifyDataSetChanged()
                }
                return true;
            }
        })
    }


    override fun isSheetAlwaysExpanded(): Boolean {
        return true
    }

    @SuppressLint("Range")
    override fun getExpandedHeight(): Int {
        val displayMetrics = DisplayMetrics()
        (context as Activity?)!!.windowManager
            .defaultDisplay
            .getMetrics(displayMetrics)
        return (displayMetrics.heightPixels * 0.8).toInt();
    }

    override fun updateField(value: JobField) {
        updateData.updateField(value)
        this.dismiss()
    }
}
interface iUpdateField{
    fun updateField(value : JobField)
}