package com.ciptakerjaarunika.kerjaloka.ui.Screens.JobseekerReview.Adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobseekerReview.Model.review_response
import com.google.android.material.chip.ChipGroup

class JobseekerReviewAdapter(private val reviewList: List<review_response>) : RecyclerView.Adapter<JobseekerReviewAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var picture: ImageView
        var companyName: TextView
        var ratingAt: TextView
        var ratingBar: RatingBar
        var reviewMessage: TextView
        var chipProRating: ChipGroup
        var chipConRating: ChipGroup
        init {
            picture = itemView.findViewById(R.id.profile_picture)
            companyName = itemView.findViewById(R.id.username)
            reviewMessage = itemView.findViewById(R.id.text_review)
            ratingAt = itemView.findViewById(R.id.date_review)
            ratingBar = itemView.findViewById(R.id.ratingUser)
            chipProRating = itemView.findViewById(R.id.chipGroupKelebihan)
            chipConRating = itemView.findViewById(R.id.chipGroupTantangan)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.item_card_review, null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = reviewList[position]
        holder.companyName.text = item.companyName
        holder.reviewMessage.text = item.messageReview
        holder.ratingAt.text = item.ratingAt
        holder.ratingBar.rating = item.rating
        Glide.with(holder.itemView.context).load(item.raterphoto).fitCenter().into(holder.picture)
//
//        val proRating = item.proRating
//        proRating.forEach{
//            val chip = Chip(holder.chipProRating.context)
//            chip.setChipBackgroundColorResource(R.color.danger_100)
//            chip.apply {
//                textSize = 12f
//                text = it
//                isChipIconVisible = false
//                isCloseIconVisible = false
//                isClickable = false
//                isCheckable = false
//                rootView.apply {
//                    holder.chipProRating.addView(chip as View)
//                }
//            }
//        }
//        val conRating = item.conRating
//        conRating.forEach{
//            val chip = Chip(holder.chipConRating.context)
//            chip.setChipBackgroundColorResource(R.color.danger_100)
//            chip.apply {
//                textSize = 12f
//                text = it
//                isChipIconVisible = false
//                isCloseIconVisible = false
//                isClickable = false
//                isCheckable = false
//                rootView.apply {
//                    holder.chipConRating.addView(chip as View)
//                }
//            }
//        }


    }

    override fun getItemCount(): Int {
        return reviewList.size
    }
}