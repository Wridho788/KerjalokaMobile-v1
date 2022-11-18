package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.Bottomsheet

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.iConfirmPage
import com.ciptakerjaarunika.kerjaloka.R

class BottomSheetConfirm(val iConfirmPage: iConfirmPage): SuperBottomSheetFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_confirm_publish, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btn_delete = view.findViewById<TextView>(R.id.txt_hapus)
        val btn_save_draft = view.findViewById<TextView>(R.id.txt_save_draft)
        val btn_cancel = view.findViewById<TextView>(R.id.txt_cancel)

        btn_delete.text = "Hapus"
        btn_cancel.text = "Batal"
        btn_save_draft.text = "Simpan ke Draft"
        btn_cancel.setOnClickListener {
            this.dismiss()
        }

        btn_delete.setOnClickListener {
            iConfirmPage.deletePage()
        }

        btn_save_draft.setOnClickListener {
        iConfirmPage.draftJob()
        }
    }

    override fun isSheetAlwaysExpanded(): Boolean {
        return true
    }

    @SuppressLint("Range")
    override fun getExpandedHeight(): Int {
        return ViewGroup.LayoutParams.WRAP_CONTENT
    }
}