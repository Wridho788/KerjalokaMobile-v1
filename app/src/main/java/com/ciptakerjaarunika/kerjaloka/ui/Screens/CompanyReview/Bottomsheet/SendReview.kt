package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyReview.Bottomsheet

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.ciptakerjaarunika.kerjaloka.R

class SendReview : SuperBottomSheetFragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.layout_send_review, container, false)

        return view
    }
    @SuppressLint("Range")
    override fun getExpandedHeight(): Int {
        return 2000
    }

    override fun isSheetCancelableOnTouchOutside(): Boolean {
        return true
    }

}