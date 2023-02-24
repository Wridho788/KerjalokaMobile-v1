package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.JobSearch.Adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.HomePage.Model.rJobModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

class SearchJobAdapter(private val rJobList: List<rJobModel>) :
    RecyclerView.Adapter<SearchJobAdapter.ViewHolder>() {


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var jobPosition: TextView
        var logo: ImageView
        var jobCompany: TextView
        var jobLocation: TextView
        var timeUploadApplicant: TextView
        var bookmarkedJob: ImageView
        var shareableJob: MaterialButton
        var cardRecommendationJob: MaterialCardView


        init {
            jobPosition = itemView.findViewById(R.id.jobPosition)
            logo = itemView.findViewById(R.id.logo)
            jobCompany = itemView.findViewById(R.id.jobCompany)
            jobLocation = itemView.findViewById(R.id.jobLocation)
            timeUploadApplicant = itemView.findViewById(R.id.createdOn)
            bookmarkedJob = itemView.findViewById(R.id.btn_bookmark)
            shareableJob = itemView.findViewById(R.id.btn_share)
            cardRecommendationJob = itemView.findViewById(R.id.card_recommendation_job)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.item_card_recommendation_job, null)
        view.layoutParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return rJobList.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = rJobList[position]
        holder.jobPosition.text = currentItem.jobPosition
        holder.jobCompany.text = currentItem.companyName
        holder.jobLocation.text = currentItem.jobLocation
        holder.timeUploadApplicant.text = currentItem.createdOn
        Glide.with(holder.itemView.context).load(currentItem.logo).fitCenter().into(holder.logo)

        holder.bookmarkedJob.setOnClickListener {}
        holder.shareableJob.setOnClickListener {}
        holder.cardRecommendationJob.setOnClickListener {}
    }

}
