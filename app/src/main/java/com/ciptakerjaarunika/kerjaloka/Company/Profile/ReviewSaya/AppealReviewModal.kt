package com.ciptakerjaarunika.kerjaloka.Company.Profile.ReviewSaya

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.ciptakerjaarunika.kerjaloka.Company.Profile.ReviewSaya.Model.DataX
import com.ciptakerjaarunika.kerjaloka.Company.Profile.ReviewSaya.Model.Review
import com.ciptakerjaarunika.kerjaloka.R
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

class AppealReviewModal: SuperBottomSheetFragment() {
    private lateinit var activityResultLauncher : ActivityResultLauncher<Intent>
    private var document : MultipartBody.Part? = null
    private var file : File? = null
    var review: Review? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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
                val pathName = context?.let { it2 -> PathUtil().getPath(it2, fileUri) }

                val file = File(pathName?:"")
                this.file = file
                val requestFile: RequestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())

                btn_upload.text = file.name
                document = MultipartBody.Part.createFormData("document", file.name, requestFile)
            }
        }


        btn_upload.setOnClickListener {
            var intent = Intent(Intent.ACTION_GET_CONTENT);
            val mimeTypes = arrayOf(
                "application/pdf",
                "application/msword",
                "application/vnd.ms-powerpoint",
                "application/vnd.ms-excel",
                "text/plain"
            )
            intent.setType("*/*")
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                intent.type = if (mimeTypes.size === 1) mimeTypes[0] else "*/*"
//                if (mimeTypes.size > 0) {
//                    intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
//                }
//            } else {
//                var mimeTypesStr = ""
//                for (mimeType in mimeTypes) {
//                    mimeTypesStr += "$mimeType|"
//                }
//                intent.type = mimeTypesStr.substring(0, mimeTypesStr.length - 1)
//            }

            intent.addCategory(Intent.CATEGORY_OPENABLE);

            val requestIntent = Intent.createChooser(intent, "Choose a File");
            activityResultLauncher.launch(requestIntent)

        }

        if (arguments != null){
            val descFromBundle = arguments?.getString(EXTRA_APPEAL_REVIEW)
            review = Gson().fromJson(descFromBundle, Review::class.java)
            val RatingBy = review?.userNo
            val AppealMessage = txtAppeal.text.toString()
                btnSend.setOnClickListener{
                    if (RatingBy != null) {
                        file?.let { it1 ->
                            UserRatingAPI().AppealReview(RatingBy, AppealMessage,
                                it1, context){
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