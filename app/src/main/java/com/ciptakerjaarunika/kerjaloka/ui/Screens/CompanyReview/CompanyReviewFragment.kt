package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyReview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.CanSendReview
import com.ciptakerjaarunika.kerjaloka.api.CompanyDetailAPI
import com.ciptakerjaarunika.kerjaloka.api.CompanyReviewAPI
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.databinding.ActivityMainBinding
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyReview.Adapter.CompanyReviewAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyReview.Bottomsheet.SendReview
import com.google.android.material.appbar.MaterialToolbar

class CompanyReviewFragment(private val CompanyNo: Long? = null) : Fragment() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_company_review, container, false)
        val companyName = view.findViewById<TextView>(R.id.company_name)
        val logo = view.findViewById<ImageView>(R.id.company_logo)
        val field = view.findViewById<TextView>(R.id.company_field)
        val location = view.findViewById<TextView>(R.id.company_location)
        val rv_review = view.findViewById<RecyclerView>(R.id.rv_item_card)
        val rv_my_review = view.findViewById<RecyclerView>(R.id.rv_item_my_review)
        val layout_my_review = view.findViewById<LinearLayout>(R.id.layout_my_review)
        val ratingBar = view.findViewById<RatingBar>(R.id.rBar)
        val txtRating = view.findViewById<TextView>(R.id.ratingtext)
        val totalReview = view.findViewById<TextView>(R.id.totalReviewText)
        val layout_send_review = view.findViewById<LinearLayout>(R.id.btn_send_review_company)

        val thisActivity = this
        CompanyDetailAPI().getCompanyDetailAsync(context, CompanyNo!!) {
            if (it != null) {
                Log.d("company review", it.toString())
                companyName.text = it.data.companyName
                Glide.with(this).load(config().portAddress + "/photo/Profile/" + it.data.logo)
                    .fitCenter().into(logo)
                field.text = it.data.field
                location.text = it.data.companyAddress
                ratingBar.rating = it.data.rating.ratingValue
                txtRating.text =
                    it.data.rating.ratingList.size.toString() + " dari " + it.data.rating.ratingList.size.toString()
                totalReview.text = it.data.rating.ratingList.size.toString()
            }
        }
        var UserNo = 20211027141022
        var user = SessionManager(context).user
        layout_my_review.visibility = View.GONE
        layout_send_review.visibility = View.GONE
        if (user != null) {
            CanSendReview().getSendReviewAsync(context, UserNo) {
                if (it != null) {
                    if (it.data.hasSend == true) {
                        layout_my_review.visibility = View.GONE
                    } else {
                        layout_my_review.visibility = View.VISIBLE
                    }
                    if (it.data.canSend == true) {
                        layout_send_review.visibility = View.VISIBLE
                        layout_send_review.setOnClickListener {
                          sendReviewModal(UserNo)
                        }
                    } else {
                        layout_send_review.setOnClickListener(null)

                    }
                }
            }
        }
        CompanyReviewAPI().getCompanyReviewAsync(context, UserNo) {
            if (it != null) {
                rv_review?.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = CompanyReviewAdapter(it.data.reviewList)
                }
            }
        }
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar_review)
        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    fun sendReviewModal(UserNo: Long){
        val sheet = SendReview(UserNo, id, CompanyReviewFragment())
        activity.let { it1 ->
            sheet.show(
                it1!!.supportFragmentManager,
                "SendReview"
            )
        }
    }
}