package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.LamaranPage

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.JobAPI
import com.google.android.material.button.MaterialButton

class ReportJob(val JobNo: Long) : SuperBottomSheetFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.modal_report_layout, container, false)
    }

//    override fun getCornerRadius() = requireContext().resources.getDimension(R.dimen.demo_sheet_rounded_corner)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<MaterialButton>(R.id.reportJobBtn).setOnClickListener{
            val message = view.findViewById<EditText>(R.id.reportMessage)?.text.toString()
            if(!message.isNullOrEmpty()) {
                JobAPI().ReportJob(JobNo, message, context) {
                    if(it!= null) {
                        Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                        if (it?.code == 210) {
                            this.dismiss()
                        }
                    }
                }
            }
            else{
                Toast.makeText(activity, "Pesan tidak boleh kosong", Toast.LENGTH_SHORT).show()
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