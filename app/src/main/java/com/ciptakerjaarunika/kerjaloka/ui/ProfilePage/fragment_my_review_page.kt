package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.Company.Profile.Adapter.CompReviewAdapter
import com.ciptakerjaarunika.kerjaloka.Company.Profile.Listener.AppealModal
import com.ciptakerjaarunika.kerjaloka.Company.Profile.ReviewSaya.AppealReviewModal
import com.ciptakerjaarunika.kerjaloka.Company.Profile.ReviewSaya.Model.Review
import com.ciptakerjaarunika.kerjaloka.Company.Profile.ReviewSaya.ReviewHistory
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.ProfileAPI
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentMyReviewPageBinding
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter.ReviewAdapter
import com.google.gson.Gson

class fragment_my_review_page : Fragment() {
    private lateinit var binding : FragmentMyReviewPageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyReviewPageBinding.inflate((layoutInflater))
        val view = binding.root;
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRiwayatReview.setOnClickListener {
                val fragmentManager = activity?.supportFragmentManager
                val fragmentTransaction = fragmentManager?.beginTransaction()
                fragmentTransaction?.replace(R.id.fragment_container, ReviewHistory())
                fragmentTransaction?.commit()
        }
        ProfileAPI().JobseekerGetMyReview(context){
            if(it?.data != null){
                binding.spinner.visibility = GONE
                binding.contentContainer.visibility = VISIBLE

                binding.ratingValue.rating = it.data.userInfo.rating.toFloat()
                binding.jumlahReview.text = "${it.data.userInfo.rating.toFloat()} dari 5"
                binding.totalReview.text = "${it.data.reviewList.size} Reviews"
                val recyclerViewLang = view.findViewById<RecyclerView>(R.id.revList)
                recyclerViewLang.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = assignAdapter(it.data.reviewList)
                }

            }
        }
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
    private fun replaceFragment(fragment: Fragment){

        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(id, fragment)
        fragmentTransaction?.commit()
    }
}