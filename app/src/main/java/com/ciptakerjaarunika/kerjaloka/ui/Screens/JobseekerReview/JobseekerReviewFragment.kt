package com.ciptakerjaarunika.kerjaloka.ui.Screens.JobseekerReview

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.ciptakerjaarunika.kerjaloka.api.CanSendReview
import com.ciptakerjaarunika.kerjaloka.api.CompanyReviewAPI
import com.ciptakerjaarunika.kerjaloka.api.DeleteReviewResponse
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentJobseekerReviewBinding
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.JobApplicant.Model.applicantModel
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyReview.Bottomsheet.SendReview
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobseekerReview.Adapter.JobseekerReviewAdapter


class JobseekerReviewFragment(
    private val companyNo: Long,
    private val applicantDetail: applicantModel
) : Fragment() {
    private lateinit var binding: FragmentJobseekerReviewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentJobseekerReviewBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbarReview.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.applicantDetailSection.jobseekerAddress.text =
            applicantDetail.applicant.location.city + ", " + applicantDetail.applicant.location.province
        Glide.with(this)
            .load(config().portAddress + "/photo/Profile/" + applicantDetail.applicant.photo)
            .fitCenter().into(binding.applicantDetailSection.jobseekerPicture)
        binding.applicantDetailSection.jobseekerName.text = applicantDetail.applicant.name
        val qualified = applicantDetail.qualified
        if (qualified.isEmpty()) {
            binding.applicantDetailSection.jobseekerStatus.text = "Qualified"
            binding.applicantDetailSection.jobseekerStatus.setTextColor(Color.parseColor("#27AE60"))
        }
        binding.reviewList.rBar.rating = applicantDetail.ownRating.rating.ratingValue
        binding.reviewList.ratingtext.text =
            applicantDetail.ownRating.rating.ratingList.size.toString() + " dari " + applicantDetail.ownRating.rating.ratingList.size.toString()
        binding.reviewList.totalReviewText.text =
            applicantDetail.ownRating.rating.ratingList.size.toString() + " reviews"

//        section send review
        var company = SessionManager(context).user?.company
        binding.reviewList.btnSendReviewCompany.visibility = View.GONE

        if (company != null) {
            CanSendReview().getSendReviewAsync(context, companyNo) {
                if (it != null) {
                    if (it.data.canSend) {
                        binding.reviewList.btnSendReviewCompany.visibility = View.VISIBLE
                        binding.reviewList.btnSendReviewCompany.setOnClickListener {
                            sendReviewModal(companyNo)
                        }
                    }
                }
            }
        }
        val companyUserNo = SessionManager(context).user?.company?.userNo
        // my review
        binding.layoutReviewParent.visibility = View.GONE
        CompanyReviewAPI().getCompanyReviewAsync(context, applicantDetail.applicant.jobseekerNo!!) {
            if (it != null) {
                binding.rvItemCard.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = JobseekerReviewAdapter(it.data.reviewList)
                }
                val my_review = it!!.data.reviewList.filter { item ->
                    item.userNo == companyUserNo
                }
                if (!my_review.isEmpty()) {
                    binding.layoutReviewParent.visibility = View.VISIBLE
                    binding.cardMyReview.btnHapusReview.setOnClickListener {
                        if (applicantDetail.ownRating.ownUserRatingNo != null) {
                            DeleteReviewResponse().getDeleteMyReview(
                                context,
                                applicantDetail.ownRating.ownUserRatingNo
                            ) {
                                binding.sectionItemReview.visibility = View.GONE
                            }
                        } else {
                            binding.layoutReviewParent.visibility = View.GONE
                        }
                    }
                    binding.cardMyReview.btnEditReview.setOnClickListener {
                        sendReviewModal(companyNo)
                    }
                }
            }
        }
    }

    fun sendReviewModal(companyNo: Long) {
        val sheet =
            SendReview(
                companyNo, id, JobseekerReviewFragment(companyNo, applicantDetail)
            )
        activity.let { it1 ->
            sheet.show(
                it1!!.supportFragmentManager,
                "SendReview"
            )
        }
    }
}