package com.ciptakerjaarunika.kerjaloka.ui.HomePage.Adapter

import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.ui.HomePage.Model.rJobModel
import com.ciptakerjaarunika.kerjaloka.ui.HomePage.OnFragmentClickListener
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class RecommendationJobAdapter(
    private val rJobList: List<rJobModel>?,
    private val onFragmentClick: OnFragmentClickListener,
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
            CreatedOn = itemView.findViewById(R.id.createdOn)
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

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (rJobList != null) {
            val currentItem = rJobList[position]
            holder.jobPosition.text = currentItem.jobPosition
            holder.companyName.text = currentItem.companyName
            holder.jobLocation.text = currentItem.jobLocation
            val SECOND = 1
            val MINUTE = 60 * SECOND
            val HOUR = 60 * MINUTE
            val DAY = 24 * HOUR
            val WEEK = 7 * DAY

            var time = currentItem.createdOn
            val now = LocalDateTime.now().toString()

            fun GetDateValue(value: String): Date {
                val temp = value.split("T")
                val time = temp[1].split(":")
                val date = "${temp[0]} ${time[0]}:${time[1]}"
                var dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
                return dateFormat.parse(date)
            }

            fun dateDiff(): String {
                val date1 = GetDateValue(time).time
                val date2 = GetDateValue(now).time

                val diff = (date2 - date1) / 1000
                return when {
                    diff < MINUTE -> "Baru Saja"
                    diff < 2 * MINUTE -> "Beberapa Menit Lalu"
                    diff < 60 * MINUTE -> "${diff / MINUTE} Menit Lalu"
                    diff < 2 * HOUR -> "Beberapa Jam Lalu"
                    diff < 24 * HOUR -> "${diff / HOUR} Jam Lalu"
                    diff < 2 * DAY -> "Kemarin"
                    diff < WEEK -> "${diff / DAY} Hari Lalu"
                    else -> LocalDateTime.parse(time)
                        .format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                }

            }
            holder.CreatedOn.text = dateDiff()

            Glide.with(holder.itemView.context)
                .load(config().portAddress + "/photo/Profile/" + currentItem.logo).fitCenter()
                .into(holder.logo)

//            holder.bookmarkedJob.setOnClickListener {
//            }
//            holder.shareableJob.setOnClickListener {
//            }
            holder.cardRecommendationJob.setOnClickListener {
                onFragmentClick.onFragmentClick(currentItem.jobNo, currentItem.companyNo)
            }
        }
    }

}
