package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyReview.Bottomsheet

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.RatingBar.OnRatingBarChangeListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.SendReviewAPI
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyReview.Model.conRatingList
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyReview.Model.proRatingList
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyReview.Model.send_Request
import com.google.android.material.chip.Chip


class SendReview(val CompanyNo: Long, val fragmentId: Int, val GotoFragment: Fragment) :
    SuperBottomSheetFragment() {

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
        val chipProRating1 = view.findViewById<Chip>(R.id.chip_gajitunjangan)
        val chipProRating2 = view.findViewById<Chip>(R.id.chip_tingkatStress)
        val chipProRating3 = view.findViewById<Chip>(R.id.chip_jumlahpekerjaan)
        val chipProRating4 = view.findViewById<Chip>(R.id.chip_manajemen)
        val chipProRating5 = view.findViewById<Chip>(R.id.chip_lingkunganKerja)
        val chipProRating6 = view.findViewById<Chip>(R.id.chip_FlexibilitasWaktu)
        val chipProRating7 = view.findViewById<Chip>(R.id.chip_PengembanganKarir)

        val chipConRating1 = view.findViewById<Chip>(R.id.chip_congajitunjangan)
        val chipConRating2 = view.findViewById<Chip>(R.id.chip_contingkatStress)
        val chipConRating3 = view.findViewById<Chip>(R.id.chip_conjumlahpekerjaan)
        val chipConRating4 = view.findViewById<Chip>(R.id.chip_conmanajemen)
        val chipConRating5 = view.findViewById<Chip>(R.id.chip_conlingkunganKerja)
        val chipConRating6 = view.findViewById<Chip>(R.id.chip_conFlexibilitasWaktu)
        val chipConRating7 = view.findViewById<Chip>(R.id.chip_conPengembanganKarir)

        val chipIds: Set<Int> = HashSet()

        val chip1Id = 1
        val chip2Id = 2
        val chip3Id = 3
        val chip4Id = 4
        val chip5Id = 5
        val chip6Id = 6
        val chip7Id = 7

        chipProRating1.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                Log.d("true", "chipProRating1")
            } else {
                for (i in chipIds) {
                    if (i == chip1Id) {
//                        chipIds.remove(i)
                        Log.d("chips", chip1Id.toString())
                    }
                }
            }
        }

        ratingBar.onRatingBarChangeListener =
            OnRatingBarChangeListener { ratingBar, nilai, b -> ratingBar.rating }


        val proRatingList = ArrayList<proRatingList>()

        val category1 = proRatingList(
            1
        )
        val conRatingList = ArrayList<conRatingList>()
        val conRating1 = conRatingList(1)
        conRatingList.add(conRating1)
        proRatingList.add(category1)
        btn_send_review.setOnClickListener {
            SendReviewAPI().SendReviewCompanyPost(
                context,
                send_Request(
                    CompanyNo,
                    textReview.text.toString(),
                    ratingBar.rating.toLong(),
                    proRatingList,
                    conRatingList
                )
            ) {
                if (it != null) {
                    this.dismiss()
                    val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
                    ft.replace(fragmentId, GotoFragment, "jobseekerReviewFragment")
                    ft.commit()
                }
            }
        }
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