package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anychart.ui.contextmenu.Item
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.ProfileAPI
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentMyReviewPageBinding
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Profile.Adapter.CompReviewAdapter
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Profile.Listener.AppealModal
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Profile.ReviewSaya.AppealReviewModal
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Profile.ReviewSaya.Model.Review
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Profile.ReviewSaya.ReviewHistory
import com.google.firebase.ktx.Firebase
import com.google.firebase.perf.ktx.performance
import com.google.firebase.perf.metrics.AddTrace
import com.google.gson.Gson

class fragment_my_review_page : Fragment() {
    private lateinit var binding: FragmentMyReviewPageBinding

    @AddTrace(name = "onReviewPageTrace", enabled = true)
    class ItemCache {
        fun fetch(name: String): Item? {
            return null
        }
    }

    fun myReviewTrace() {
        val cache = ItemCache()
        val myTrace = Firebase.performance.newTrace("my_review_page_trace")
        myTrace.start()
        val item = cache.fetch("item")
        if (item != null) {
            myTrace.incrementMetric("item_cache_hit", 1)
        } else {
            myTrace.incrementMetric("item_cache_miss", 1)
        }
        myTrace.stop()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myReviewTrace()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyReviewPageBinding.inflate((layoutInflater))
        val view = binding.root
        return view
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ProfileAPI().JobseekerGetMyReview(context) {
            if (it != null) {
                binding.spinner.visibility = GONE
                binding.contentContainer.visibility = VISIBLE
                binding.reviewBtn.setOnClickListener {
                    seeHistory()
                }
                binding.ratingValue.rating = it.data.userInfo.rating.toFloat()
                binding.jumlahReview.text =
                    "${String.format("%.0f", it.data.userInfo.rating.toFloat())} dari 5"
                binding.totalReview.text = "${it.data.reviewList.size} Reviews"
                val recyclerViewLang = view.findViewById<RecyclerView>(R.id.revList)
                recyclerViewLang.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = assignAdapter(it.data.reviewList)
                }

            }
        }
    }

    private fun assignAdapter(list: List<Review>): CompReviewAdapter? {
        return context?.let {
            CompReviewAdapter(it, list, object : AppealModal {
                override fun appealModal(pack: Review) {
                    val sheet = AppealReviewModal()
                    val mBundle = Bundle()
                    val reviewData = Gson().toJson(pack)
                    mBundle.putString(AppealReviewModal.EXTRA_APPEAL_REVIEW, reviewData)
                    sheet.arguments = mBundle
                    activity?.let { it1 ->
                        sheet.show(
                            it1.supportFragmentManager, "DemoBottomSheetFragment"
                        )
                    }
                }
            })
        }
    }

    private fun seeHistory() {
        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.addToBackStack("")
        fragmentTransaction?.replace(R.id.fragment_container, ReviewHistory())
        fragmentTransaction?.commit()
    }
}