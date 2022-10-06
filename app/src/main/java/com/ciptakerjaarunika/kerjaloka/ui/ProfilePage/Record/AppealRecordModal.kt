package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Record

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.ciptakerjaarunika.kerjaloka.Company.Profile.ReviewSaya.Model.DataX
import com.ciptakerjaarunika.kerjaloka.Company.Profile.ReviewSaya.Model.Review
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.ManageProfileAPI
import com.ciptakerjaarunika.kerjaloka.api.UserRatingAPI
import com.google.android.material.button.MaterialButton
import com.google.gson.Gson
import com.ciptakerjaarunika.kerjaloka.utils.PathUtil
import com.qingmei2.rximagepicker_extension.utils.PathUtils
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class AppealRecordModal(val recordNo: Int): SuperBottomSheetFragment() {
    private lateinit var activityResultLauncher : ActivityResultLauncher<Intent>

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