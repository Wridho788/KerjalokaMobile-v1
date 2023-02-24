package com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Companyjobdetail.Bottomsheet

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
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.companyAddJob.Majors
import com.ciptakerjaarunika.kerjaloka.model.Data.Title
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Companyjobdetail.Bottomsheet.Adapter.MajorAdapter
import com.google.android.material.button.MaterialButton

class BottomSheetMajorJob : SuperBottomSheetFragment(),
    iChooseMajor {
    private var list: List<Title>? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(
            R.layout.layout_bottomsheet_edit_job, container, false
        )

        view.layoutParams = RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )

        val title = view.findViewById<TextView>(R.id.title_location)
        val rv_majors = view.findViewById<RecyclerView>(R.id.list_location_view)

        title.text = "Pendidikan"
        Majors().GetMajors(context) { res ->
            list
            rv_majors.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = MajorAdapter(res, this@BottomSheetMajorJob)
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
        return (displayMetrics.heightPixels * 0.8).toInt()
    }

    override fun close() {
        val btn_confirm = view?.findViewById<MaterialButton>(R.id.btn_konfirmasi)
        btn_confirm?.setOnClickListener {
            this.dismiss()
        }

    }
}

interface iChooseMajor {
    fun close()
}
