package com.ciptakerjaarunika.kerjaloka.viewmodel.Company.CompanyApplicant.ApplicantDetail.Bottomsheet.MoreAction

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
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.CompanyApplicant.ApplicantDetail.Bottomsheet.MoreAction.Model.send_bookmark
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.CompanyApplicant.JobApplicant.Model.applicantModel
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.CompanyApplicant.JobApplicant.iJobApplicant

class MoreActionFragment(
    val jobseekerNo: Long,
    val jobNo: Long,
    var applicantModel: applicantModel,
    val iJobApplicant: iJobApplicant
) : SuperBottomSheetFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_more_action, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btn_pin = view.findViewById<TextView>(R.id.txt_pin)
        updateText()

        btn_pin.setOnClickListener {
            BookmarkAPI().BookmarkJob(
                context,
                send_bookmark(jobNo, jobseekerNo),
                jobNo,
                jobseekerNo,
                applicantModel.bookmarked
            ) {
                if (it != null) {
                    applicantModel.bookmarked = !applicantModel.bookmarked
                    iJobApplicant.getRefreshData()
                    updateText()

                    this.dismiss()
                    Log.d("bookmarked", it.toString())
                }
            }
        }


    }

    fun updateText() {
        val btn_pin = view?.findViewById<TextView>(R.id.txt_pin)

        if (applicantModel.bookmarked == true) {
            btn_pin?.text = "UNPIN"
        } else if (applicantModel.bookmarked == false) {
            btn_pin?.text = "PIN"
        }
    }

    override fun getCornerRadius() = 20f

    @SuppressLint("Range")
    override fun getExpandedHeight(): Int {
        val displayMetrics = DisplayMetrics()
        (context as Activity?)!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return (displayMetrics.heightPixels * 0.5).toInt()
    }
}