package com.ciptakerjaarunika.kerjaloka.ui.Screens.JobseekerReview.Bottomsheet

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RatingBar
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.ciptakerjaarunika.kerjaloka.R

class SendReview : SuperBottomSheetFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.layout_send_review, container, false)
        val ratingBar = view.findViewById<RatingBar>(R.id.RatingModal)
        val btn_send_review = view.findViewById<LinearLayout>(R.id.btn_send_review)
        val textReview = view.findViewById<EditText>(R.id.insertreview)
//        val ratingBar = view.findViewById<RatingBar>(R.id.RatingModal)
//        val btn_send_review = view.findViewById<LinearLayout>(R.id.btn_send_review)
//        val textReview = view.findViewById<EditText>(R.id.insertreview)
//        ratingBar.onRatingBarChangeListener =
//            OnRatingBarChangeListener { ratingBar, nilai, b -> ratingBar.rating }
//
//        val proRatingList = ArrayList<proRatingList>()
//        val category1 = proRatingList(
//            1
//        )
//        val conRatingList = ArrayList<conRatingList>()
//        val conRating1 = conRatingList(1)
//        conRatingList.add(conRating1)
//        proRatingList.add(category1)
//        btn_send_review.setOnClickListener {
//            SendReviewAPI().SendReviewPost(
//                context,
//                send_Request(
//                    CompanyNo,
//                    textReview.text.toString(),
//                    ratingBar.rating.toLong(),
//                    proRatingList,
//                    conRatingList
//                )
//            ) {
//                if (it != null) {
//                    this.dismiss()
//                    val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
//                    ft.replace(fragmentId, GotoFragment, "jobseekerReviewFragment")
//                    ft.commit()
//                }
//            }
//        }
        return view
    }

    @SuppressLint("Range")
    override fun getExpandedHeight(): Int {
        val displayMetrics = DisplayMetrics()
        (context as Activity?)!!.windowManager
            .defaultDisplay
            .getMetrics(displayMetrics)
        return (displayMetrics.heightPixels * 0.8).toInt();
    }

    override fun isSheetAlwaysExpanded(): Boolean {
        return true
    }
    override fun isSheetCancelableOnTouchOutside(): Boolean {
        return true
    }

}