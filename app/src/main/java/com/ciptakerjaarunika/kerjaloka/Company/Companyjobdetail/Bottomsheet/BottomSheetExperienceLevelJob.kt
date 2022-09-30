package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.Bottomsheet

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.Bottomsheet.Adapter.ExperiencesLevelAdapter
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.companyAddJob.ExperieceLevels
import com.ciptakerjaarunika.kerjaloka.model.Data.ExperienceLevelFilter

class BottomSheetExperienceLevelJob : SuperBottomSheetFragment(), iChooseExperienceLevel {
    private var list: List<ExperienceLevelFilter>? = null
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
        val rv_experience = view.findViewById<RecyclerView>(R.id.list_location_view)

        title.text = "Tingkat Pengalaman"
        ExperieceLevels().GetExperienceLevel(context){
            res -> list
            rv_experience.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = ExperiencesLevelAdapter(res, this@BottomSheetExperienceLevelJob)
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
    override fun close(experienceLevelName: String) {
        Log.d(experienceLevelName, "experienceLevelName")
        this.dismiss()

    }
}

interface iChooseExperienceLevel{
    fun close(experienceLevelName: String)
}
