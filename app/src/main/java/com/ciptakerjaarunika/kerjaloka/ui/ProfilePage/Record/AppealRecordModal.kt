package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Record

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
import com.ciptakerjaarunika.kerjaloka.Company.Profile.ReviewSaya.Model.Review
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.ManageProfileAPI
import com.google.android.material.button.MaterialButton

class AppealRecordModal(val recordNo: Int): SuperBottomSheetFragment() {
    var review: Review? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val message_txt = view.findViewById<EditText>(R.id.message_txt)
        val btnSend = view.findViewById<MaterialButton>(R.id.btnSend)

        btnSend.setOnClickListener{
            if(message_txt.text.toString().isNullOrEmpty()){
                Toast.makeText(context, "Alasan melakukan Appeal tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
            else {
                ManageProfileAPI().SendAppealRecord(recordNo, message_txt.text.toString(), context) {
                    if (it != null) {
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                        if (it.code == 210) {
                            this.dismiss()
                        }
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.appeal_record_modal, container, false)

        return view
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