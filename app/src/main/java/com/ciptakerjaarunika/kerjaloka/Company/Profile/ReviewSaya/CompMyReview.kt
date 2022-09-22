package com.ciptakerjaarunika.kerjaloka.Company.Profile.ReviewSaya

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.Company.Profile.Adapter.CompReviewAdapter
import com.ciptakerjaarunika.kerjaloka.Company.Profile.Listener.AppealModal
import com.ciptakerjaarunika.kerjaloka.Company.Profile.conRat
import com.ciptakerjaarunika.kerjaloka.Company.Profile.data
import com.ciptakerjaarunika.kerjaloka.Company.Profile.proRat
import com.ciptakerjaarunika.kerjaloka.Company.Profile.review
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.CompanyReviewAPI
import com.ciptakerjaarunika.kerjaloka.ui.Global.GlobalDeleteModal
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ModalEdit.EditResident
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyReview.Adapter.CompanyReviewAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyReview.Model.company_reviews
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyReview.Model.reviewList
import com.google.android.material.button.MaterialButton

private var layoutManager: RecyclerView.LayoutManager? = null
private var adapterRec: RecyclerView.Adapter<CompReviewAdapter.ViewHolder>? = null


class CompMyReview(val data: data?, private val CompanyNo: Long? = null): Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_comp_my_review, container, false)

        val btn_revHistory = view.findViewById<MaterialButton>(R.id.btn_riwayat_review)
        val recyclerCompReview = view.findViewById<RecyclerView>(R.id.revList)
        val allRating = view.findViewById<RatingBar>(R.id.allRating)
        val sumRate = view.findViewById<TextView>(R.id.jumlah_review)

        btn_revHistory.setOnClickListener{
            replaceFragment(ReviewHistory())
        }
        CompanyReviewAPI().getCompanyReviewAsync(context, data?.userNo) {
            allRating.rating = it?.data?.userInfo?.rating?.toFloat()!!
            sumRate.text = "${it?.data?.userInfo?.rating} dari 5"
            if (it != null) {
                recyclerCompReview?.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = assignAdapter(it.data.reviewList)
                }
            }
        }


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

    internal fun assignAdapter(list: List<reviewList>): CompReviewAdapter {
        return CompReviewAdapter(requireContext(), list, object : AppealModal {
            override fun appealModal(pack: reviewList) {
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