package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.ModalEdit

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
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.model.Data.SkillFilter
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.Adapter.ChooseSkillAdapter
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.ManageCV.iEditKemampuan
import java.util.*


class ChooseSkill(
    val value: SkillFilter?,
    val skills: List<SkillFilter>,
    val iEditKemampuan: iEditKemampuan
) : SuperBottomSheetFragment(), iChooseSkill {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = View.inflate(context, R.layout.global_modal_edit, null)
        val title = view.findViewById<TextView>(R.id.judul_bottom_sheet)
        title.text = "Pilih Skill"
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycleEdit)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = ChooseSkillAdapter(value, skills, iEditKemampuan, this@ChooseSkill)
        }

        var searchInput = view.findViewById<SearchView>(R.id.search_filter)
        searchInput.visibility = View.VISIBLE
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
                        adapter =
                            ChooseSkillAdapter(value, skills, iEditKemampuan, this@ChooseSkill)
                    }
                    recyclerView.adapter?.notifyDataSetChanged()
                } else {
                    var temp = skills.filter { data ->
                        data.skillName.lowercase(Locale.getDefault()).contains(keyword)
                    }
                    recyclerView.apply {
                        layoutManager = LinearLayoutManager(activity)
                        adapter =
                            ChooseSkillAdapter(value, temp!!, iEditKemampuan, this@ChooseSkill)
                    }
                    recyclerView.adapter?.notifyDataSetChanged()
                }
                return true
            }
        })

    }

    override fun isSheetAlwaysExpanded(): Boolean {
        return true
    }

    @SuppressLint("Range")
    override fun getExpandedHeight(): Int {
        val displayMetrics = DisplayMetrics()
        (context as Activity?)!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return (displayMetrics.heightPixels * 0.8).toInt()
    }

    override fun close() {
        this.dismiss()
    }


}

interface iChooseSkill {
    fun close()
}