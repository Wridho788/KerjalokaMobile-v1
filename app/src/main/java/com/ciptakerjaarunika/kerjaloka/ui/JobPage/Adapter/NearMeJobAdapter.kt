package com.ciptakerjaarunika.kerjaloka.ui.JobPage.Adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.HomePage.Model.rJobModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

class NearMeJobAdapter(private val rJobList: List<rJobModel>) :
    RecyclerView.Adapter<NearMeJobAdapter.ViewHolder>() {


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var jobPosition: TextView
        var logo: ImageView
        var jobCompany: TextView
        var jobLocation: TextView
        var timeUploadApplicant: TextView
        var bookmarkedJob: MaterialButton
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
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return rJobList?.size ?: 0
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = rJobList[position]
        holder.jobPosition.text = currentItem.jobPosition
        holder.jobCompany.text = currentItem.companyName
        holder.jobLocation.text = currentItem.jobLocation
        holder.timeUploadApplicant.text = currentItem.createdOn
        Glide.with(holder.itemView.context).load(currentItem.logo).fitCenter().into(holder.logo)

        holder.bookmarkedJob.setOnClickListener {
//            when (currentItem.jobNo) {
//                1 -> {
//                    Toast.makeText(
//                        holder.itemView.context,
//                        "Job 1 telah di bookmark",
//                        Toast.LENGTH_SHORT
//                    ).show()
////                    val intent = Intent(it.context, CityActivity::class.java)
////                    intent.putExtra("currentItem", currentItem)
////                    it.context.startActivity(intent)
//                }
//                2 -> {
//                    Toast.makeText(
//                        holder.itemView.context,
//                        "Job 2 telah di bookmark",
//                        Toast.LENGTH_SHORT
//                    ).show()
////                     holder.bookmarkedJob.setBackgroundResource(R.drawable.ic_bookmark_border_black_24dp)
//                }
//                3 -> {
//                    Toast.makeText(
//                        holder.itemView.context,
//                        "Job 3 telah di bookmark",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//                4 -> {
//                    Toast.makeText(
//                        holder.itemView.context,
//                        "Job 4 telah di bookmark",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }

        }
        holder.shareableJob.setOnClickListener {
//            when (currentItem.jobNo) {
//                1 -> {
//                    Toast.makeText(
//                        holder.itemView.context,
//                        "Job 1 telah di share",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//                2 -> {
//                    Toast.makeText(
//                        holder.itemView.context,
//                        "Job 2 telah di share",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//                3 -> {
//                    Toast.makeText(
//                        holder.itemView.context,
//                        "Job 3 telah di share",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//                4 -> {
//                    Toast.makeText(
//                        holder.itemView.context,
//                        "Job 4 telah di share",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
        }
        holder.cardRecommendationJob.setOnClickListener {
//            when (currentItem.jobNo) {
//                1 -> {
//                    Toast.makeText(
//                        holder.itemView.context,
//                        "Job 1 telah di klik",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//                2 -> {
//                    Toast.makeText(
//                        holder.itemView.context,
//                        "Job 2 telah di klik",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//                3 -> {
//                    Toast.makeText(
//                        holder.itemView.context,
//                        "Job 3 telah di klik",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//                4 -> {
//                    Toast.makeText(
//                        holder.itemView.context,
//                        "Job 4 telah di klik",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
        }
    }

}
