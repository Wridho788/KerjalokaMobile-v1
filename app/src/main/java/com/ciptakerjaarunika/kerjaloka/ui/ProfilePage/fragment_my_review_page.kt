package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.ProfileAPI
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentMyReviewPageBinding
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter.ReviewAdapter

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

        ProfileAPI().JobseekerGetMyReview(context){
            if(it?.data != null){
                binding.ratingValue.rating = it.data.userInfo.rating.toFloat()
                binding.jumlahReview.text = "${it.data.userInfo.rating.toFloat()} dari 5"
                binding.totalReview.text = "${it.data.reviewList.size} Reviews"
                val recyclerViewLang = view.findViewById<RecyclerView>(R.id.revList)
                recyclerViewLang.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = ReviewAdapter(it.data.reviewList)
                }

            }
        }
    }
}