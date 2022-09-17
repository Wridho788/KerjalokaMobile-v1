package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail.Bottomsheet.MoreAction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.ciptakerjaarunika.kerjaloka.R

class MoreActionFragment : SuperBottomSheetFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_more_action, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btn_banding = view.findViewById<TextView>(R.id.txt_banding)
        val btn_pin = view.findViewById<TextView>(R.id.txt_pin)

        btn_banding.setOnClickListener {
            this.dismiss()
//            val ft: FragmentTransaction = childFragmentManager.beginTransaction()
//            ft.replace(id,CompanyCompareJobseekerFragment(), "CompanyCompareJobseeker")
//            ft.addToBackStack("CompanyCompareJobseeker")
//            ft.commit()

        }

        btn_pin.setOnClickListener {
            Toast.makeText(activity, "pin", Toast.LENGTH_SHORT).show()
        }
    }

}