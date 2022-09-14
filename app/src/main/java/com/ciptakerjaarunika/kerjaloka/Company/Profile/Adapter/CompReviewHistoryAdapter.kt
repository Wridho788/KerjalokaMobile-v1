package com.ciptakerjaarunika.kerjaloka.Company.Profile.Adapter
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.Company.Profile.review
import com.ciptakerjaarunika.kerjaloka.R
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class CompReviewHistoryAdapter (private val reviewList: List<review>):
    RecyclerView.Adapter<CompReviewHistoryAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var creator: TextView
        //        var reviewTime: TextView
        var Desc: TextView
        var proChip : ChipGroup
        var conChip : ChipGroup
        var ratBar : RatingBar

        init {
            creator = itemView.findViewById(R.id.nama_jobseeker)
//            reviewTime = itemView.findViewById(R.id.record_page_date)
            Desc = itemView.findViewById(R.id.reviewDesc)
            proChip = itemView.findViewById(R.id.chipGroup_kelebihan)
            conChip = itemView.findViewById(R.id.chipGroup_kekurangan)
            ratBar = itemView.findViewById(R.id.ratingbar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.review_history_card, null)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = reviewList[position]
        holder.creator.text = currentItem.userFullName
        holder.Desc.text = currentItem.comment
        holder.ratBar.rating = currentItem.rating.toFloat()

        if (currentItem.conRating.isNotEmpty()){
            currentItem.conRating.forEach {
                val chip = Chip(holder.conChip.context)
                chip.setChipBackgroundColorResource(R.color.danger_100)
                chip.apply {
                    textSize = 12f
                    text = it.con
                    isChipIconVisible = false
                    isCloseIconVisible = false
                    isClickable = false
                    isCheckable = false
                    holder.apply {
                        conChip.addView(chip as View)
                    }
                }
            }
        }
        if (currentItem.proRating.isNotEmpty()){
            currentItem.proRating.forEach {
                val chip = Chip(holder.proChip.context)
                chip.setChipBackgroundColorResource(R.color.danger_100)
                chip.apply {
                    textSize = 12f
                    text = it.con
                    isChipIconVisible = false
                    isCloseIconVisible = false
                    isClickable = false
                    isCheckable = false
                    holder.apply {
                        proChip.addView(chip as View)
                    }
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return reviewList?.size ?:0
    }

}
