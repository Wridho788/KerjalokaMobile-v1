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
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.Bottomsheet.Adapter.FieldsAdapter
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.companyAddJob.JobFields
import com.ciptakerjaarunika.kerjaloka.model.Data.Field

class BottomSheetFieldsJob : SuperBottomSheetFragment(), iChooseFields {
    private var list: List<Field>? = null
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
        val rv_field = view.findViewById<RecyclerView>(R.id.list_location_view)

        title.text = "Bidang Pekerjaan"
        JobFields().GetJobFields(context){
            res -> list
            rv_field.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = FieldsAdapter(res, this@BottomSheetFieldsJob)
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
    override fun close(fieldName: String) {
        Log.d(fieldName, "experienceLevelName")
        this.dismiss()

    }
}

interface iChooseFields{
    fun close(fieldName: String)
}
