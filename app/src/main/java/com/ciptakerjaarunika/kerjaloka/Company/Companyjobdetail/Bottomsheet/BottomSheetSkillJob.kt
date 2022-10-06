package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.Bottomsheet

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.Bottomsheet.Adapter.SkillAdapter
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.iUpdatePage2
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.companyAddJob.Skills
import com.ciptakerjaarunika.kerjaloka.model.Data.SkillFilter
import com.google.android.material.button.MaterialButton

class BottomSheetSkillJob(val iUpdatePage2: iUpdatePage2) : SuperBottomSheetFragment(), iChooseSkill {
    private var list: List<SkillFilter>? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(
            R.layout.layout_bottomsheet_edit_job, container, false)

        view.layoutParams = RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )

        val title = view.findViewById<TextView>(R.id.title_location)
        val rv_skill = view.findViewById<RecyclerView>(R.id.list_location_view)

        title.text = "Skill"
        Skills().GetSkill(context){
            res -> list
            rv_skill.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = SkillAdapter(res, this@BottomSheetSkillJob, iUpdatePage2)
            }
        }
        return view
    }

    override fun getCornerRadius() = 20f

    @SuppressLint("Range")
    override fun getExpandedHeight(): Int {
        val displayMetrics = DisplayMetrics()
        (context as Activity?)!!.windowManager
            .defaultDisplay
            .getMetrics(displayMetrics)
        return (displayMetrics.heightPixels * 0.8).toInt();
    }
    override fun close(list: List<SkillFilter>) {
        val btn_confirm = view?.findViewById<MaterialButton>(R.id.btn_konfirmasi)
        btn_confirm?.setOnClickListener {
            this.dismiss()
        }
    }
}

interface iChooseSkill{
    fun close(list: List<SkillFilter>)

}
