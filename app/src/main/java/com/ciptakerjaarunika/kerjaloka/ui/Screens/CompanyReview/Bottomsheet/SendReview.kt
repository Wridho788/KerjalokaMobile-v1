package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyReview.Bottomsheet

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.RatingBar.OnRatingBarChangeListener
import androidx.fragment.app.FragmentTransaction
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.SendReviewAPI
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyReview.CompanyReviewFragment
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyReview.Model.conRatingList
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyReview.Model.proRatingList
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyReview.Model.send_Request

class SendReview(private val CompanyNo: Long) : SuperBottomSheetFragment(), OnFragmentClickListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.layout_send_review, container, false)
        val ratingBar = view.findViewById<RatingBar>(R.id.RatingModal)
        val btn_send_review = view.findViewById<LinearLayout>(R.id.btn_send_review)
        val textReview = view.findViewById<EditText>(R.id.insertreview)
        ratingBar.onRatingBarChangeListener =
            OnRatingBarChangeListener { ratingBar, nilai, b -> ratingBar.rating}

        val proRatingList = ArrayList<proRatingList>()
        val category1 = proRatingList(
            1
        )
        val conRatingList = ArrayList<conRatingList>()
        val conRating1 = conRatingList(1)
        conRatingList.add(conRating1)
        proRatingList.add(category1)
        Log.d("rating list", proRatingList.toString())
        btn_send_review.setOnClickListener {
            SendReviewAPI().SendReviewPost(context, send_Request(CompanyNo,textReview.text.toString(), ratingBar.rating.toLong(), proRatingList, conRatingList )){
                if (it != null){
                    Log.d("Send Response", it.toString())
//                    onCompanyReview()
                }
            }
        }
        return view
    }

    @SuppressLint("Range")
    override fun getExpandedHeight(): Int {
        return 2000
    }

    override fun isSheetCancelableOnTouchOutside(): Boolean {
        return true
    }

    override fun onCompanyReview(){
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.replace(id, CompanyReviewFragment(), "CompanyReviewFragment")
        ft.addToBackStack("CompanyReviewFragment")
        ft.commit()
    }
}

interface OnFragmentClickListener {
    fun onCompanyReview()
}