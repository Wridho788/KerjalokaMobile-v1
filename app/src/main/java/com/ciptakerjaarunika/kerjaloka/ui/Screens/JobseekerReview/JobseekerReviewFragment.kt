package com.ciptakerjaarunika.kerjaloka.ui.Screens.JobseekerReview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.databinding.ActivityMainBinding
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobseekerReview.Adapter.JobseekerReviewAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobseekerReview.Bottomsheet.SendReview
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobseekerReview.Model.review_response
import com.google.android.material.appbar.MaterialToolbar


class JobseekerReviewFragment : Fragment() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_jobseeker_review, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar_review)
        val btn_gift_review = view.findViewById<LinearLayout>(R.id.btn_send_review_company)

        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)

        val list = ArrayList<review_response>()
        val list1 = review_response(
            1,
            "Binford Ltd.",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png",
            2f,
            "11 Agustus 2022 pada 13:32",
            "Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint. Velit officia consequat duis enim velit mollit. Exercitation veniam consequat sunt nostrud amet. Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint. Velit officia consequat duis enim velit mollit. Exercitation veniam consequat sunt nostrud amet.",
            )
        val list2 = review_response(
            2,
            "Binford Ltd.",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png",
            2f,
            "11 Agustus 2022 pada 13:32",
            "Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint. Velit officia consequat duis enim velit mollit. Exercitation veniam consequat sunt nostrud amet. Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint. Velit officia consequat duis enim velit mollit. Exercitation veniam consequat sunt nostrud amet.",
        )
        val list3 = review_response(
            3,
            "Binford Ltd.",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png",
            2f,
            "11 Agustus 2022 pada 13:32",
            "Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint. Velit officia consequat duis enim velit mollit. Exercitation veniam consequat sunt nostrud amet. Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint. Velit officia consequat duis enim velit mollit. Exercitation veniam consequat sunt nostrud amet.",
        )
        val list4= review_response(
            4,
            "Binford Ltd.",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png",
            2f,
            "11 Agustus 2022 pada 13:32",
            "Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint. Velit officia consequat duis enim velit mollit. Exercitation veniam consequat sunt nostrud amet. Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint. Velit officia consequat duis enim velit mollit. Exercitation veniam consequat sunt nostrud amet.",
        )
        val list5 = review_response(
            5,
            "Binford Ltd.",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png",
            2f,
            "11 Agustus 2022 pada 13:32",
            "Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint. Velit officia consequat duis enim velit mollit. Exercitation veniam consequat sunt nostrud amet. Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint. Velit officia consequat duis enim velit mollit. Exercitation veniam consequat sunt nostrud amet.",
        )

        list.add(list1)
        list.add(list2)
        list.add(list3)
        list.add(list4)
        list.add(list5)

        val rv_listReview = view.findViewById<RecyclerView>(R.id.rv_item_card)
        rv_listReview.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = JobseekerReviewAdapter(list)
        }

        btn_gift_review.setOnClickListener {
            val sheet = SendReview()
            sheet.let { it1 -> sheet.show(childFragmentManager,"sendreview") }
            }
    }
}