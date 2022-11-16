package com.ciptakerjaarunika.kerjaloka.Company.Profile.ReviewSaya

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.Company.Profile.Adapter.CompReviewAdapter
import com.ciptakerjaarunika.kerjaloka.Company.Profile.Listener.AppealModal
import com.ciptakerjaarunika.kerjaloka.Company.Profile.ReviewSaya.Model.Review
import com.ciptakerjaarunika.kerjaloka.Company.Profile.data
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.company_profile_api
import com.google.android.material.button.MaterialButton
import com.google.gson.Gson



class CompMyReview(val data: data?, private val CompanyNo: Long? = null): Fragment() {
private var layoutManager: RecyclerView.LayoutManager? = null
private var adapterRec: RecyclerView.Adapter<CompReviewAdapter.ViewHolder>? = null

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

        company_profile_api().CompMyReview(sortByNewest = false, context){
            if (it != null) {
            allRating.rating = it?.data?.userInfo?.rating?.toFloat()!!
            sumRate.text = "${it?.data?.userInfo?.rating} dari 5"
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
        fragmentTransaction?.addToBackStack("")
        fragmentTransaction?.commit()
    }

    internal fun assignAdapter(list: List<Review>): CompReviewAdapter {
        return CompReviewAdapter(requireContext(), list, object : AppealModal {
            override fun appealModal(pack: Review) {
                val sheet = AppealReviewModal()
                val mBundle = Bundle()
                val reviewData = Gson().toJson(pack)
                mBundle.putString(AppealReviewModal.EXTRA_APPEAL_REVIEW, reviewData)
                sheet.arguments = mBundle
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