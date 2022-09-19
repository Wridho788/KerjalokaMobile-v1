package com.ciptakerjaarunika.kerjaloka.Company.Profile.ReviewSaya

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.Company.Profile.Adapter.CompReviewHistoryAdapter
import com.ciptakerjaarunika.kerjaloka.Company.Profile.Listener.ShowModal
import com.ciptakerjaarunika.kerjaloka.Company.Profile.ProfilePage
import com.ciptakerjaarunika.kerjaloka.Company.Profile.conRat
import com.ciptakerjaarunika.kerjaloka.Company.Profile.proRat
import com.ciptakerjaarunika.kerjaloka.Company.Profile.review
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.Global.GlobalDeleteModal
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyScreen.CompanyPage
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyScreen.OnFragmentClickListener
import com.google.android.material.appbar.MaterialToolbar

private var layoutManager: RecyclerView.LayoutManager? = null
private var adapterRec: RecyclerView.Adapter<CompReviewHistoryAdapter.ViewHolder>? = null

class ReviewHistory : Fragment(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.fragment_review_history, container, false)
        val btn_back = view.findViewById<ImageButton>(R.id.btn_back)

        val conratList = ArrayList<conRat>()
        val rat1 = conRat(
            id = 1,
            con = "Manajemen"
        )
        val rat2 = conRat(
            id = 2,
            con = "Lingkungan Pekerjaan"
        )
        conratList.add(rat1)
        conratList.add(rat2)

        val proratList = ArrayList<proRat>()
        val pro1 = proRat(
            id = 1,
            con = "Gaji dan Tunjangan"
        )
        val pro2 = proRat(
            id = 2,
            con = "Tingkat Stress"
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
            userFullName = "Kevin Hot Marojahan",
            userNo = 20211102115301,
            userRatingNo = 1,
            userRole = 2
        )
        ReviewList.add(rev1)

        val recyclerCompReview = view.findViewById<RecyclerView>(R.id.recycleRevHistory)
        layoutManager = LinearLayoutManager(activity)
        recyclerCompReview.layoutManager = layoutManager
        adapterRec = assignAdapter(ReviewList)
        recyclerCompReview.adapter = adapterRec

        btn_back.setOnClickListener{
            replaceFragment(ProfilePage())
        }

        return view
    }

    companion object {

    }
    private fun replaceFragment(fragment: Fragment){

        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = parentFragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.fragment_container, fragment)
        fragmentTransaction?.addToBackStack(null)
        fragmentTransaction?.commit()
    }

    internal fun assignAdapter(list: List<review>): CompReviewHistoryAdapter {
        return CompReviewHistoryAdapter(requireContext(), list, object : ShowModal {
            override fun showDetail(pack: review) {
                val sheet = EditMyReview()
                activity?.let { it1 ->
                    sheet.show(
                        it1.supportFragmentManager,
                        "DemoBottomSheetFragment"
                    )
                }
            }

            override fun showDelete(review: review) {
                val sheet = GlobalDeleteModal()
                activity?.let { it1 ->
                    sheet.show(
                        it1.supportFragmentManager,
                        "DemoBottomSheetFragment"
                    )
                }
            }
        })
    }

}