package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.LamaranPage

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.JobAPI
import com.google.android.material.button.MaterialButton


class WithdrawJob(val JobNo : Long, val fragmentId : Int,val GotoFragment : Fragment): SuperBottomSheetFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.modal_withdraw_layout, container, false)
    }

//    override fun getCornerRadius() =

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<MaterialButton>(R.id.confirmWithdraw).setOnClickListener{
            JobAPI().WithdrawJob(context, JobNo){
                this.dismiss()
                if(it?.code == 210){
                    val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
                    ft.replace(fragmentId, GotoFragment, "LamaranPage")
                    ft.commit()
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

}