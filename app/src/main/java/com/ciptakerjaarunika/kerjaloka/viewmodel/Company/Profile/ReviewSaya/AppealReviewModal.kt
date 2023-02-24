package com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Profile.ReviewSaya

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.UserRatingAPI
import com.ciptakerjaarunika.kerjaloka.utils.PathUtil
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Profile.ReviewSaya.Model.Review
import com.google.android.material.button.MaterialButton
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class AppealReviewModal : SuperBottomSheetFragment() {
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private var document: MultipartBody.Part? = null
    private var fileSupport: File? = null
    var review: Review? = null
    var AppealMessage = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btn_upload = view.findViewById<MaterialButton>(R.id.uploadAppealFile)
        val txtAppeal = view.findViewById<EditText>(R.id.appealReview)
        val btnSend = view.findViewById<MaterialButton>(R.id.btnSend)

        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK && it.data != null) {
                val data = it.data
                val fileUri: Uri = data!!.data!!
                val pathName = context?.let { it2 -> PathUtil().GetFilePath(fileUri, it2) }

                val file = File(pathName ?: "")
                this.fileSupport = file
                val requestFile: RequestBody =
                    file.asRequestBody("multipart/form-data".toMediaTypeOrNull())

                btn_upload.text = file.name
                document = MultipartBody.Part.createFormData("document", file.name, requestFile)
            }
        }


        btn_upload.setOnClickListener {
            var intent = Intent(Intent.ACTION_GET_CONTENT)
            val mimeTypes = arrayOf(
                "application/pdf",
                "application/msword",
                "application/vnd.ms-powerpoint",
                "application/vnd.ms-excel",
                "text/plain"
            )
            intent.type = "*/*"
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            val requestIntent = Intent.createChooser(intent, "Choose a File")
            activityResultLauncher.launch(requestIntent)
        }
        txtAppeal.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            @SuppressLint("NotifyDataSetChanged")
            override fun afterTextChanged(s: Editable) {
                if (!txtAppeal.text.toString().isNullOrEmpty()) {
                    AppealMessage = txtAppeal.text.toString()
                }
            }
        })

        if (arguments != null) {
            val descFromBundle = arguments?.getString(EXTRA_APPEAL_REVIEW)
            review = Gson().fromJson(descFromBundle, Review::class.java)
            val RatingBy = review?.userNo
            btnSend.setOnClickListener {
                if (RatingBy == null) {
                    Toast.makeText(
                        context,
                        "Terjadi kesalahan yang tidak diketahui",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (AppealMessage.isEmpty()) {
                    Toast.makeText(context, "Appeal message tidak boleh kosong", Toast.LENGTH_SHORT)
                        .show()
                } else if (fileSupport == null) {
                    Toast.makeText(context, "Silahkan unggah dokumen appeal", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    UserRatingAPI().SendAppeal(RatingBy, AppealMessage, fileSupport, context) {
                        if (it != null) {
                            Toast.makeText(
                                context,
                                "Permintaan Appeal Telah Berhasil, Silahkan Menunggu Approval dari Kerjaloka. Terima Kasih",
                                Toast.LENGTH_SHORT
                            ).show()
                            if (it.code == 210) {
                                this.dismiss()
                            }
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
        val view = inflater.inflate(R.layout.fragment_appeal_review_modal, container, false)
        return view
    }

    companion object {
        var EXTRA_APPEAL_REVIEW = "extra_appealReview"
    }

    override fun isSheetAlwaysExpanded(): Boolean {
        return true
    }

    @SuppressLint("Range")
    override fun getExpandedHeight(): Int {
        return ViewGroup.LayoutParams.WRAP_CONTENT
    }

}