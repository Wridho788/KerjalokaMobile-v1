package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter.ReviewAdapter
import conRat
import proRat
import review

private var layoutManager: RecyclerView.LayoutManager? = null
private var adapterRec: RecyclerView.Adapter<ReviewAdapter.ViewHolder>? = null


class fragment_my_review_page : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_review_page, container, false)

//        val proChip = view.findViewById<ChipGroup>(R.id.chipGroup_kelebihan)
//        val conChip = view.findViewById<ChipGroup>(R.id.chipGroup_kekurangan)

        val conratList = ArrayList<conRat>()
        val rat1 = conRat(
            id = 1,
            con = "Adi la kinte"
        )
        val rat2 = conRat(
            id = 2,
            con = "Letto Paya"
        )
        conratList.add(rat1)
        conratList.add(rat2)

        val proratList = ArrayList<proRat>()
        val pro1 = proRat(
            id = 1,
            con = "Adi la kinte"
        )
        val pro2 = proRat(
            id = 2,
            con = "Letto Paya"
        )
        proratList.add(pro1)
        proratList.add(pro2)

        val ReviewList = ArrayList<review>()
        val rev1 = review(
            approvedByUserNo = 0,
            approvedOn = "2022-07-18T09:27:36",
            canAppeal = true,
            comment = "null",
            conRating = conratList,
            ownerInfo = "null",
            proRating = proratList,
            raterPhoto = "202110271410221246.jpg",
            rating = 4,
            ratingAt = "2022-07-18T09:27:20",
            userFullName = "TESTING",
            userNo = 20211027141022,
            userRatingNo = 3,
            userRole = 2
        )

        ReviewList.add(rev1)

//        if(conratList.isNotEmpty()){
//            conratList.forEach {
//                val chip = Chip(context)
//                chip.setChipBackgroundColorResource(R.color.danger_100)
//                chip.apply {
//                    textSize = 12f
//                    text = it.con
//                    isChipIconVisible = false
//                    isCloseIconVisible = false
//                    isClickable = true
//                    isCheckable = false
//                    view.apply {
//                        conChip.addView(chip as View)
//                    }
//                }
//            }
//        }



        val recyclerViewLang = view.findViewById<RecyclerView>(R.id.revList)
        layoutManager = LinearLayoutManager(activity)
        recyclerViewLang.layoutManager = layoutManager
        adapterRec = ReviewAdapter(ReviewList)
        recyclerViewLang.adapter = adapterRec
        return view
    }

    companion object {

    }
}