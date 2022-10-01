package com.ciptakerjaarunika.kerjaloka.Company.Profile.ReviewSaya

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.ciptakerjaarunika.kerjaloka.Company.Profile.ReviewSaya.Model.DataX
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.UsersAPI
import com.google.android.material.button.MaterialButton
import com.google.gson.Gson


class DeleteReviewModal : SuperBottomSheetFragment() {
    var review: DataX? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnDelete = view.findViewById<MaterialButton>(R.id.btn_delete)
        val btnCancel = view.findViewById<MaterialButton>(R.id.btn_cancel)

        if (arguments!=null){
            val descFromBundle = arguments?.getString(EXTRA_DELETE_REVIEW)
            review = Gson().fromJson(descFromBundle, DataX::class.java)
            btnDelete.setOnClickListener{
                review?.userRatingNo?.let { it1 -> UsersAPI().DeleteSendedReview(it1, context){} }
            }
            btnCancel.setOnClickListener{
                this.dismiss()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.fragment_global_delete_modal, container, false)
    }

    companion object {
        var EXTRA_DELETE_REVIEW = "extra_deleteReview"
    }
}