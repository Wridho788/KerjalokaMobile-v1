package com.ciptakerjaarunika.kerjaloka.Company.Profile.ReviewSaya

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.Company.Profile.Adapter.CompReviewAdapter
import com.ciptakerjaarunika.kerjaloka.Company.Profile.Adapter.CompReviewHistoryAdapter
import com.ciptakerjaarunika.kerjaloka.Company.Profile.Listener.AppealModal
import com.ciptakerjaarunika.kerjaloka.Company.Profile.conRat
import com.ciptakerjaarunika.kerjaloka.Company.Profile.proRat
import com.ciptakerjaarunika.kerjaloka.Company.Profile.review
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.Global.GlobalDeleteModal
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ModalEdit.EditResident
import com.google.android.material.button.MaterialButton

private var layoutManager: RecyclerView.LayoutManager? = null
private var adapterRec: RecyclerView.Adapter<CompReviewAdapter.ViewHolder>? = null


class CompMyReview : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_comp_my_review, container, false)

        val btn_revHistory = view.findViewById<MaterialButton>(R.id.btn_riwayat_review)

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

        btn_revHistory.setOnClickListener{
            replaceFragment(ReviewHistory())
        }

        val recyclerCompReview = view.findViewById<RecyclerView>(R.id.revList)
        layoutManager = LinearLayoutManager(activity)
        recyclerCompReview.layoutManager = layoutManager
        adapterRec = assignAdapter(ReviewList)
        recyclerCompReview.adapter = adapterRec


        return view
    }

    companion object {

    }
    private fun replaceFragment(fragment: Fragment){

        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.fragment_container, fragment)
        fragmentTransaction?.commit()
    }

    internal fun assignAdapter(list: List<review>): CompReviewAdapter {
        return CompReviewAdapter(requireContext(), list, object : AppealModal {
            override fun appealModal(pack: review) {
                val sheet = AppealReviewModal()
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