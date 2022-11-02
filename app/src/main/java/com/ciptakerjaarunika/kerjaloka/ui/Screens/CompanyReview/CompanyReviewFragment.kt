package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyReview

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ciptakerjaarunika.kerjaloka.Company.Profile.ReviewSaya.EditMyReview
import com.ciptakerjaarunika.kerjaloka.Company.Profile.ReviewSaya.Model.DataX
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.`interface`.iRefreshData
import com.ciptakerjaarunika.kerjaloka.api.CanSendReview
import com.ciptakerjaarunika.kerjaloka.api.CompanyDetailAPI
import com.ciptakerjaarunika.kerjaloka.api.CompanyReviewAPI
import com.ciptakerjaarunika.kerjaloka.api.UsersAPI
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentCompanyReviewBinding
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyReview.Adapter.CompanyReviewAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyReview.Bottomsheet.SendReview
import com.ciptakerjaarunika.kerjaloka.utils.DateUtils
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.gson.Gson

class CompanyReviewFragment(private val CompanyNo: Long? = null) : Fragment(), iRefreshData {

    private lateinit var binding: FragmentCompanyReviewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCompanyReviewBinding.inflate(layoutInflater)
        return binding.root
    }

    fun refreshData() {
        val companyName = view?.findViewById<TextView>(R.id.company_name)
        val logo = view?.findViewById<ImageView>(R.id.company_logo)
        val field = view?.findViewById<TextView>(R.id.company_field)
        val location = view?.findViewById<TextView>(R.id.company_location)
        val rv_review = view?.findViewById<RecyclerView>(R.id.rv_item_card)
        val layout_my_review = view?.findViewById<LinearLayout>(R.id.layout_my_review)
        val ratingBar = view?.findViewById<RatingBar>(R.id.rBar)
        val txtRating = view?.findViewById<TextView>(R.id.ratingtext)
        val totalReview = view?.findViewById<TextView>(R.id.totalReviewText)
        val layout_send_review = view?.findViewById<LinearLayout>(R.id.btn_send_review_company)

        val thisActivity = this
        CompanyDetailAPI().getCompanyDetailAsync(context, CompanyNo!!) {
            if (it != null) {
                companyName?.text = it.data.companyName
                if (logo != null) {
                    Glide.with(this).load(config().portAddress + "/photo/Profile/" + it.data.logo)
                        .fitCenter().into(logo)
                }
                field?.text = it.data.field
                location?.text = "${it.data.location.city}, ${it.data.location.province}"
                ratingBar?.rating = it.data.rating.ratingValue
                txtRating?.text = String.format("%.0f", it.data.rating.ratingValue) + " dari " + 5
                totalReview?.text = it.data.rating.ratingList.size.toString() + "Reviews"

                if (it.data.ownRating != null) {
                    layout_my_review?.visibility = VISIBLE

                    view?.findViewById<ImageView>(R.id.myReviewPhoto)?.let { it1 ->
                        Glide.with(this)
                            .load(config().portAddress + "/photo/Profile/" + SessionManager(context).user?.photo)
                            .fitCenter().into(it1)
                    }

                    view?.findViewById<TextView>(R.id.myReviewName)?.text =
                        SessionManager(context).user!!.userFullname
                    view?.findViewById<RatingBar>(R.id.myRating)?.rating =
                        it.data.ownRating!!.toFloat()

                    it.data.ownProRating?.forEach {
                        val chip = Chip(context)
                        chip.setChipBackgroundColorResource(R.color.danger_100)
                        chip.apply {
                            textSize = 12f
                            text = it
                            isChipIconVisible = false
                            isCloseIconVisible = false
                            isClickable = false
                            isCheckable = false
                            rootView.apply {
                                view?.findViewById<ChipGroup>(R.id.myChipGroupPro)
                                    ?.addView(chip as View)
                            }
                        }
                    }
                    it.data.ownConRating?.forEach {
                        val chip = Chip(context)
                        chip.setChipBackgroundColorResource(R.color.danger_100)
                        chip.apply {
                            textSize = 12f
                            text = it
                            isChipIconVisible = false
                            isCloseIconVisible = false
                            isClickable = false
                            isCheckable = false
                            rootView.apply {
                                view?.findViewById<ChipGroup>(R.id.myChipGroupCon)
                                    ?.addView(chip as View)
                            }
                        }
                    }

                    view?.findViewById<TextView>(R.id.myReviewText)?.text = it.data.ownRatingComment
                    view?.findViewById<TextView>(R.id.myReviewAt)?.text = "${
                        DateUtils().GetDateValueWithFormat(
                            it.data.ownRatingAt,
                            "dd MMMM yyyy"
                        )
                    } pada " +
                            "${DateUtils().GetDateValueWithFormat(it.data.ownRatingAt, "hh:mm")}"

                    view?.findViewById<MaterialButton>(R.id.btn_Edit)?.setOnClickListener { btn ->
                        val sheet = EditMyReview(this)
                        val mBundle = Bundle()
                        val reviewData = Gson().toJson(
                            DataX(
                                true,
                                null,
                                it.data.ownRatingComment,
                                it.data.ownConRating!!,
                                it.data.ownProRating!!,
                                SessionManager(context).user?.photo,
                                it.data.ownRating!!,
                                it.data.ownRatingAt!!,
                                "",
                                "",
                                CompanyNo,
                                it.data.ownUserRatingNo!!
                            )
                        )
                        mBundle.putString(EditMyReview.EXTRA_EDIT_REVIEW, reviewData)
                        sheet.arguments = mBundle
                        activity?.let { it1 ->
                            sheet.show(
                                it1.supportFragmentManager,
                                "DemoBottomSheetFragment"
                            )
                        }
                    }
                    view?.findViewById<MaterialButton>(R.id.btn_delete)?.setOnClickListener { btn ->
                        AlertDialog.Builder(context)
                            .setMessage("Yakin ingin menghapus review kamu pada '${it.data.companyName}'?")
                            .setTitle("Konfirmasi menghapus")
                            .setPositiveButton("Ya", object : DialogInterface.OnClickListener {
                                override fun onClick(dialog: DialogInterface, which: Int) {
                                    it.data.ownUserRatingNo?.let { it1 ->
                                        UsersAPI().DeleteSendedReview(it1, context) {
                                            dialog.dismiss()
                                            layout_my_review?.visibility = GONE
                                        }
                                    }
                                }
                            })
                            .setNegativeButton("Batal", object : DialogInterface.OnClickListener {
                                override fun onClick(dialog: DialogInterface, which: Int) {
                                    dialog.dismiss()
                                }
                            }).create().show()
                    }
                } else {
                    layout_my_review?.visibility = GONE
                }
            }
        }
        layout_send_review?.visibility = GONE
        CanSendReview().getSendReviewAsync(context, CompanyNo) {
            if (it != null) {
                if (it.data.canSend == true) {
                    layout_send_review?.visibility = View.VISIBLE
                    layout_send_review?.setOnClickListener {
                        sendReviewModal(CompanyNo)
                    }
                }
            }
        }
        CompanyReviewAPI().getCompanyReviewAsync(context, CompanyNo) {
            if (it != null) {
                if (it.data.reviewList.isEmpty()) {
                    binding.noDataTxt.visibility = VISIBLE
                } else {
                    binding.noDataTxt.visibility = GONE
                    rv_review?.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter = CompanyReviewAdapter(it.data.reviewList.filter { item ->
                            item.userNo != SessionManager(context).user?.userNo
                        })
                    }
                }
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar_review)
        toolbar.setNavigationOnClickListener {
            fragmentManager?.popBackStack()
        }
        refreshData()
    }

    fun sendReviewModal(UserNo: Long) {
        val sheet = SendReview(UserNo, id, CompanyReviewFragment())
        activity.let { it1 ->
            sheet.show(
                it1!!.supportFragmentManager,
                "SendReview"
            )
        }
    }

    override fun refresh() {
        refreshData()
    }
}