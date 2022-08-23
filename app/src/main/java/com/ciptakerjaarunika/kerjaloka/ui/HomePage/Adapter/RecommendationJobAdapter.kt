package com.ciptakerjaarunika.kerjaloka.ui.HomePage.Adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.ui.HomePage.Model.rJobModel
import com.ciptakerjaarunika.kerjaloka.ui.HomePage.OnFragmentClickListener
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

class RecommendationJobAdapter(
    private val rJobList: List<rJobModel>?,
    private val onFragmentClickListener: OnFragmentClickListener
    ?
) :
    RecyclerView.Adapter<RecommendationJobAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var jobPosition: TextView
        var logo: ImageView
        var companyName: TextView
        var jobLocation: TextView
        var CreatedOn: TextView
        var bookmarkedJob: MaterialButton
        var shareableJob: MaterialButton
        var cardRecommendationJob: MaterialCardView

        init {
            jobPosition = itemView.findViewById(R.id.jobPosition)
            logo = itemView.findViewById(R.id.logo)
            companyName = itemView.findViewById(R.id.jobCompany)
            jobLocation = itemView.findViewById(R.id.jobLocation)
            CreatedOn = itemView.findViewById(R.id.timeUploadApplicant)
            bookmarkedJob = itemView.findViewById(R.id.btn_bookmark)
            shareableJob = itemView.findViewById(R.id.btn_share)
            cardRecommendationJob = itemView.findViewById(R.id.card_recommendation_job)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.item_card_recommendation_job, null)
//        Log.d("tes", rJobList.toString())
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return rJobList?.size ?: 0
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (rJobList != null) {
            val currentItem = rJobList[position]
            holder.jobPosition.text = currentItem.jobPosition
            holder.companyName.text = currentItem.CompanyName
            holder.jobLocation.text = currentItem.jobLocation
//        holder.CreatedOn.text = currentItem.CreatedOn.toString()
            Glide.with(holder.itemView.context)
                .load(config().portAddress + "/photo/Profile/" + currentItem.logo).fitCenter()
                .into(holder.logo)

            holder.bookmarkedJob.setOnClickListener {
//                Toast.makeText(this, "testing", Toast.LENGTH_SHORT).show()
            }
            holder.shareableJob.setOnClickListener {
//                onFragmentClickListener?.onFragmentClick()
            }
            holder.cardRecommendationJob.setOnClickListener {
                onFragmentClickListener?.onFragmentClick(currentItem.jobNo, currentItem.companyNo)
            }
        }
    }

}
