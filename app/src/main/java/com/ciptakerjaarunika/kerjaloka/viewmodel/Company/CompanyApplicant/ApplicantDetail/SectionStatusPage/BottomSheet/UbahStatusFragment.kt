package com.ciptakerjaarunika.kerjaloka.viewmodel.Company.CompanyApplicant.ApplicantDetail.SectionStatusPage.BottomSheet

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.enum.ApplicanStatusType
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.CompanyApplicant.ApplicantDetail.SectionStatusPage.iStatusPage

class UbahStatusFragment(val iStatusPage: iStatusPage) : SuperBottomSheetFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ubah_status, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val statusTerpilih = view.findViewById<TextView>(R.id.txt_shortlist)
        val statusTest = view.findViewById<TextView>(R.id.txt_test)
        val statusInterview = view.findViewById<TextView>(R.id.txt_interview)
        val statusDiterima = view.findViewById<TextView>(R.id.txt_accepted)
        val statusDitolak = view.findViewById<TextView>(R.id.txt_rejected)
        val statusCVbank = view.findViewById<TextView>(R.id.txt_cv_bank)

        statusTerpilih.text = "Terpilih"
        statusTest.text = "Dalam Test"
        statusInterview.text = "Interview"
        statusDiterima.text = "Diterima"
        statusDitolak.text = "Ditolak"
        statusCVbank.text = "CV Bank"

        statusTerpilih.setOnClickListener {
            this.dismiss()
            iStatusPage.changeStatus(ApplicanStatusType.ShortList.value)
        }
        statusTest.setOnClickListener {
            this.dismiss()
            iStatusPage.changeStatus(ApplicanStatusType.Test.value)
        }
        statusInterview.setOnClickListener {
            this.dismiss()
            iStatusPage.changeStatus(ApplicanStatusType.Interview.value)
        }
        statusDiterima.setOnClickListener {
            this.dismiss()
            iStatusPage.changeStatus(ApplicanStatusType.Accepted.value)
        }
        statusDitolak.setOnClickListener {
            this.dismiss()
            iStatusPage.changeStatus(ApplicanStatusType.Rejected.value)
        }
        statusCVbank.setOnClickListener {
            this.dismiss()
            iStatusPage.changeStatus(ApplicanStatusType.CVBank.value)
        }


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


}