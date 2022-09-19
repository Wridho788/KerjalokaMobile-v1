package com.ciptakerjaarunika.kerjaloka.ui.Screens.JobDetailScreen.BottomSheet

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.JobAPI
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobDetailScreen.IJobDetail
import com.google.android.material.button.MaterialButton

class ApplyJob(val JobNo : Long, val iJobDetail: IJobDetail) : SuperBottomSheetFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.layout_apply_job_modal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<MaterialButton>(R.id.confirmWithdraw).setOnClickListener{
            JobAPI().WithdrawJob(context, JobNo){
                this.dismiss()
                if(it?.code == 210){
                    iJobDetail.RefreshData()
//                    val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
//                    ft.replace(fragmentId, GotoFragment, "LamaranPage")
//                    ft.commit()
                }
            }
        }
    }

    override fun isSheetAlwaysExpanded(): Boolean {
        return true
    }
    @SuppressLint("Range")
    override fun getExpandedHeight(): Int {
        val displayMetrics = DisplayMetrics()
        (context as Activity?)!!.windowManager
            .defaultDisplay
            .getMetrics(displayMetrics)
        return (displayMetrics.heightPixels * 0.8).toInt();
    }

    override fun isSheetCancelableOnTouchOutside(): Boolean {
        return true
    }
}