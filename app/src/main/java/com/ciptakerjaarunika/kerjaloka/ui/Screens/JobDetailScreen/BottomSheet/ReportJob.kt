package com.ciptakerjaarunika.kerjaloka.ui.Screens.JobDetailScreen.BottomSheet

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.ciptakerjaarunika.kerjaloka.R

class ReportJob : SuperBottomSheetFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.layout_report_job_modal, container, false)
        return view
    }

    override fun isSheetAlwaysExpanded(): Boolean {
        return true
    }
    @SuppressLint("Range")
    override fun getExpandedHeight(): Int {
        return 1700
    }

    override fun isSheetCancelableOnTouchOutside(): Boolean {
        return true
    }
}