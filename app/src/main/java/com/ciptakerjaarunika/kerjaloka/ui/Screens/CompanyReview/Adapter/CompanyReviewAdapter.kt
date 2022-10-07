package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyReview.Adapter

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyReview.Model.reviewList
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class CompanyReviewAdapter(
    private val reviewList: List<reviewList>
) : RecyclerView.Adapter<CompanyReviewAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var picture: ImageView
        var username: TextView
        var comment: TextView
        var ratingAt: TextView
        var ratingBar: RatingBar
        var chipProRating: ChipGroup
        var chipConRating: ChipGroup
        init {
            picture = itemView.findViewById(R.id.profile_picture)
            username = itemView.findViewById(R.id.username)
            comment = itemView.findViewById(R.id.text_review)
            ratingAt = itemView.findViewById(R.id.date_review)
            ratingBar = itemView.findViewById(R.id.ratingUser)
            chipProRating = itemView.findViewById(R.id.chipGroupKelebihan)
            chipConRating = itemView.findViewById(R.id.chipGroupTantangan)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.item_card_review, null)
        view.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        return ViewHolder(view)
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = reviewList[position]
        holder.username.text = currentItem.userFullName
        holder.comment.text = currentItem.comment
        val proRating = currentItem.proRating
        proRating.forEach{
            val chip = Chip(holder.chipProRating.context)
            chip.setChipBackgroundColorResource(R.color.danger_100)
            chip.apply {
                textSize = 12f
                text = it
                isChipIconVisible = false
                isCloseIconVisible = false
                isClickable = false
                isCheckable = false
                rootView.apply {
                    holder.chipProRating.addView(chip as View)
                }
            }
        }
        val conRating = currentItem.conRating
        conRating.forEach{
            val chip = Chip(holder.chipConRating.context)
            chip.setChipBackgroundColorResource(R.color.danger_100)
            chip.apply {
                textSize = 12f
                text = it
                isChipIconVisible = false
                isCloseIconVisible = false
                isClickable = false
                isCheckable = false
                rootView.apply {
                    holder.chipConRating.addView(chip as View)
                }
            }
        }
        holder.ratingBar.rating = currentItem.rating
        Glide.with(holder.itemView.context)
            .load(config().portAddress + "/photo/Profile/" + currentItem.raterPhoto).fitCenter()
            .into(holder.picture)

        val SECOND = 1
        val MINUTE = 60 * SECOND
        val HOUR = 60 * MINUTE
        val DAY = 24 * HOUR
        val WEEK = 7 * DAY

        var time = currentItem.ratingAt
        Log.d("time", time.toString())
        val now = LocalDateTime.now().toString()

        fun GetDateValue(value: String): Date? {
            val temp = value.split("T")
            val time = temp[1].split(":")
            val date = "${temp[0]} ${time[0]}:${time[1]}"
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
            return dateFormat.parse(date)
        }

        fun dateDiff(): String {
            val date1 = GetDateValue(time)?.time
            val date2 = GetDateValue(now)?.time

            val diff = (date2!! - date1!!) / 1000
            return when {
                diff < MINUTE -> "Baru Saja"
                diff < 2 * MINUTE -> "Beberapa Menit Lalu"
                diff < 60 * MINUTE -> "${diff / MINUTE} Menit Lalu"
                diff < 2 * HOUR -> "Beberapa Jam Lalu"
                diff < 24 * HOUR -> "${diff / HOUR} Jam Lalu"
                diff < 2 * DAY -> "Kemarin"
                diff < WEEK -> "${diff / DAY} Hari Lalu"
                else -> LocalDateTime.parse(time).format(DateTimeFormatter.ofPattern("dd MMMM yyyy 'pada' h:mm "))
            }
        }
        holder.ratingAt.text = dateDiff()
    }

    override fun getItemCount(): Int {
        return reviewList.size
    }
}