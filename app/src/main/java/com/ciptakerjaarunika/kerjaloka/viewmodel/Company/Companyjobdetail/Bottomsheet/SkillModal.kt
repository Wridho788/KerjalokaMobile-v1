package com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Companyjobdetail.Bottomsheet

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.model.Data.SkillFilter
import com.ciptakerjaarunika.kerjaloka.model.Job.CompanyJobDetail.JobSkill
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Companyjobdetail.Bottomsheet.Adapter.SkillAdapter
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Companyjobdetail.ManageJobPage.iUpdateJobAdditionalInfo
import com.google.android.material.button.MaterialButton
import java.util.*

class SkillModal(
    var data: List<JobSkill>,
    private val dataList: List<SkillFilter>,
    private val updateData: iUpdateJobAdditionalInfo
) : SuperBottomSheetFragment(),
    iUpdateSkill {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val view = View.inflate(context, R.layout.global_modal_check_box, null)
        view.findViewById<TextView>(R.id.title).text = "Pilih Skill"
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycleEdit)

        var searchInput = view.findViewById<SearchView>(R.id.search_filter)
        searchInput.visibility = VISIBLE

        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = SkillAdapter(data, dataList, this@SkillModal)
        }

        searchInput.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextChange(newText: String?): Boolean {
                val keyword = newText.toString().lowercase(Locale.getDefault())
                if (keyword.isNullOrEmpty()) {
                    recyclerView.apply {
                        layoutManager = LinearLayoutManager(activity)
                        adapter = SkillAdapter(data, dataList, this@SkillModal)
                    }
                    recyclerView.adapter?.notifyDataSetChanged()
                } else {
                    var temp = dataList.filter { data ->
                        "${data.skillName}".lowercase(Locale.getDefault()).contains(keyword)
                    }
                    recyclerView.apply {
                        layoutManager = LinearLayoutManager(activity)
                        adapter = temp?.let { SkillAdapter(data, it, this@SkillModal) }
                    }
                    recyclerView.adapter?.notifyDataSetChanged()
                }
                return true
            }
        })

        view.findViewById<MaterialButton>(R.id.confirm_btn).setOnClickListener {
            updateData.updateSkill(data.distinct())
            this.dismiss()
        }
        return view
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
        return (displayMetrics.heightPixels * 0.8).toInt()
    }

    override fun updateSkill(value: List<JobSkill>) {
        data = value
    }
}

interface iUpdateSkill {
    fun updateSkill(value: List<JobSkill>)
}