package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail.Bottomsheet.MoreAction

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.companyApplicant.BookmarkAPI
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail.Bottomsheet.MoreAction.Model.send_bookmark
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.JobApplicant.iJobApplicant

class MoreActionFragment(val jobseekerNo: Long, val jobNo: Long, val bookmark: Boolean, val iJobApplicant: iJobApplicant) :
    SuperBottomSheetFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_more_action, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val btn_banding = view.findViewById<TextView>(R.id.txt_banding)
        val btn_pin = view.findViewById<TextView>(R.id.txt_pin)
        iJobApplicant.getRefreshData()
        if (bookmark == true) {
            btn_pin.text = "UNPIN"

        } else if (bookmark == false) {
            btn_pin.text = "PIN"
        }

        btn_pin.setOnClickListener {
            BookmarkAPI().BookmarkJob(context, send_bookmark(jobNo, jobseekerNo), jobNo, jobseekerNo, bookmark){
                if (it != null) {
                    this.dismiss()
                    Log.d("bookmarked", it.toString())
                }
            }
        }


    }

    override fun getCornerRadius() = 20f

    @SuppressLint("Range")
    override fun getExpandedHeight(): Int {
        val displayMetrics = DisplayMetrics()
        (context as Activity?)!!.windowManager
            .defaultDisplay
            .getMetrics(displayMetrics)
        return (displayMetrics.heightPixels * 0.5).toInt()
    }
}