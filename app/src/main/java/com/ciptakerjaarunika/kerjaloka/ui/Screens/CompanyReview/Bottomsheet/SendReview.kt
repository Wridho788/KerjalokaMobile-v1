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
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyReview.Model.proRating
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyReview.Model.proRatingList
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyReview.Model.send_Request
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup


class SendReview(val CompanyNo: Long, val fragmentId: Int, val GotoFragment: Fragment) :
    SuperBottomSheetFragment() {
    private var list: List<proRatingList> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.layout_send_review, container, false)
        val btn_send_review = view.findViewById<LinearLayout>(R.id.btn_send_review)

        val proRatingGrup = view.findViewById<ChipGroup>(R.id.chipGroupProRating)
        val conRatingGrup = view.findViewById<ChipGroup>(R.id.chipGroupConRating)

        val rating = arrayOf(
            proRating("Gaji dan Tunjangan", 1),
            proRating("Tingkat Stress", 2),
            proRating("Jumlah Pekerjaan", 3),
            proRating("Manajemen", 4),
            proRating("Lingkungan Kerja", 5),
            proRating("Flexibilitas Waktu", 6),
            proRating("Pengembangan Waktu", 7),
        )


        rating.forEach {
            val chip = Chip(context)
            chip.setChipBackgroundColorResource(R.color.white)
            chip.chipStrokeWidth = 3f
            chip.apply {
                textSize = 12f
                text = it.categoryName
                id = it.categoryNo.toInt()
                isChipIconVisible = false
                isCloseIconVisible = false
                isClickable = true
                isCheckable = false
                proRatingGrup.addView(chip as View)
                chip.setOnClickListener {
                    chip.setChipBackgroundColorResource(R.color.danger_100)
                    chip.setChipStrokeColorResource(R.color.danger_500)
                    chip.chipStrokeWidth = 7f
                    Log.d("click ${id} = ${text}", text.toString())

                }
            }
            val chipConRating = Chip(context)
            chipConRating.setChipBackgroundColorResource(R.color.white)
            chipConRating.isCheckable = false
            chipConRating.chipStrokeWidth = 3f
            chipConRating.apply {
                textSize = 12f
                text = it.categoryName
                id = it.categoryNo.toInt()
                isChipIconVisible = false
                isCloseIconVisible = false
                isClickable = true
                isCheckable = false
                conRatingGrup.addView(chipConRating as View)
                chipConRating.setOnClickListener {
                    chip.setChipBackgroundColorResource(R.color.danger_100)
                    chip.setChipStrokeColorResource(R.color.danger_500)
                    chip.chipStrokeWidth = 7f
                    Log.d("click ${id} = ${text}", text.toString())
                }
            }
        }


        val conRatingList = ArrayList<conRatingList>()
        val conRating1 = conRatingList(1)
        conRatingList.add(conRating1)
        btn_send_review.setOnClickListener {
            sendReview()
        }
        return view
    }

    @SuppressLint("Range")
    override fun getExpandedHeight(): Int {
        val displayMetrics = DisplayMetrics()
        (context as Activity?)!!.windowManager
            .defaultDisplay
            .getMetrics(displayMetrics)
        return (displayMetrics.heightPixels * 0.8).toInt()
    }


    override fun isSheetAlwaysExpanded(): Boolean {
        return true
    }

    override fun isSheetCancelableOnTouchOutside(): Boolean {
        return true
    }


    fun sendReview() {
        val textReview = view?.findViewById<EditText>(R.id.insertreview)
        val ratingBar = view?.findViewById<RatingBar>(R.id.RatingModal)
        ratingBar?.onRatingBarChangeListener =
            OnRatingBarChangeListener { ratingBar, nilai, b -> ratingBar.rating }
        val proRatingList = ArrayList<proRatingList>()
        val category1 = proRatingList(
            1
        )
        proRatingList.add(category1)
        val conRatingList = ArrayList<conRatingList>()
        val categoryc1 = conRatingList(
            1
        )
        conRatingList.add(categoryc1)
        SendReviewAPI().SendReviewCompanyPost(
            context,
            send_Request(
                CompanyNo,
                textReview?.text.toString(),
                ratingBar?.rating!!.toLong(),
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

}
