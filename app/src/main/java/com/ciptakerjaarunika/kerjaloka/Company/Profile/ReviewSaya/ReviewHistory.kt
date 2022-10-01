package com.ciptakerjaarunika.kerjaloka.Company.Profile.ReviewSaya

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.fragment_company_job_active_page
import com.ciptakerjaarunika.kerjaloka.Company.Profile.*
import com.ciptakerjaarunika.kerjaloka.Company.Profile.Adapter.CompReviewHistoryAdapter
import com.ciptakerjaarunika.kerjaloka.Company.Profile.Listener.ShowModal
import com.ciptakerjaarunika.kerjaloka.Company.Profile.ReviewSaya.Model.DataX
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.UsersAPI
import com.ciptakerjaarunika.kerjaloka.ui.Global.GlobalDeleteModal
import com.google.gson.Gson

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

        val recyclerCompReview = view.findViewById<RecyclerView>(R.id.recycleRevHistory)


        UsersAPI().CompSendedReview(SortByNewest = false, context){
                recyclerCompReview?.apply{
                    layoutManager = LinearLayoutManager(context)
                    adapter = it?.let { it1 -> assignAdapter(it1.data) }
            }
        }

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

    internal fun assignAdapter(list: List<DataX>): CompReviewHistoryAdapter {
        return CompReviewHistoryAdapter(requireContext(), list, object : ShowModal {

            override fun showDetail(review: DataX) {
                val sheet = EditMyReview()
                val mBundle = Bundle()
                val reviewData = Gson().toJson(review)
                mBundle.putString(EditMyReview.EXTRA_EDIT_REVIEW, reviewData)
                sheet.arguments = mBundle
                activity?.let { it1 ->
                    sheet.show(
                        it1.supportFragmentManager,
                        "DemoBottomSheetFragment"
                    )
                }
            }

            override fun showDelete(review: DataX) {
                val sheet = DeleteReviewModal()
                val mBundle = Bundle()
                val reviewData = Gson().toJson(review)
                mBundle.putString(DeleteReviewModal.EXTRA_DELETE_REVIEW, reviewData)
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