package com.ciptakerjaarunika.kerjaloka.ui.Screens.SearchScreen.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.JobAPI
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.Screens.SearchScreen.Model.jobList
import com.ciptakerjaarunika.kerjaloka.ui.Screens.SearchScreen.onFragmentTransactionList
import com.google.android.material.card.MaterialCardView
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class SearchJobAdapter(
    private val joblist: List<jobList>,
    private val context: Context,
    private val onFragmentClickListener: onFragmentTransactionList
) :
    RecyclerView.Adapter<SearchJobAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var jobPosition: TextView
        var jobLocation: TextView
        var jobCompany: TextView
        var createOn: TextView
        var logo: ImageView
        var cardJob: MaterialCardView
        var bookmarkedJob: ImageView

        init {
            jobPosition = itemView.findViewById(R.id.jobPosition)
            jobLocation = itemView.findViewById(R.id.jobLocation)
            jobCompany = itemView.findViewById(R.id.jobCompany)
            createOn = itemView.findViewById(R.id.createdOn)
            logo = itemView.findViewById(R.id.logo)
            cardJob = itemView.findViewById(R.id.card_recommendation_job)
            bookmarkedJob = itemView.findViewById(R.id.btn_bookmark)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.item_card_recommendation_job, null)
        view.layoutParams = ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            return ViewHolder(view)
    }
    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = joblist[position]
        holder.jobPosition.text = currentItem.jobPosition
        holder.jobCompany.text = currentItem.companyName
        holder.jobLocation.text = currentItem.jobLocations[0].location
        Glide.with(holder.itemView.context)
            .load(config().portAddress + "/photo/Profile/" + currentItem.photo).fitCenter()
            .into(holder.logo)
        holder.bookmarkedJob.setImageResource(if (currentItem.bookmarked) R.drawable.ic_bookmark_primary_filled else R.drawable.ic_bookmark_primary)

        if(SessionManager(context).user == null){
            holder.bookmarkedJob.visibility = View.GONE
        }
        holder.bookmarkedJob.setOnClickListener {
            JobAPI().BookmarkJob(currentItem.jobNo.toLong(), !currentItem.bookmarked, context) {
                if(it != null) {
                    if (it.code == 210) {
                        currentItem.bookmarked = !currentItem.bookmarked
                        joblist!![position].bookmarked = joblist!![position].bookmarked
//                        onFragmentClick.bookmarkJob(rJobList!!)
                    } else {
                        Toast.makeText(context, it.Message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

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
        holder.createOn.text = dateDiff()

        holder.cardJob.setOnClickListener{
            onFragmentClickListener.onFragmentTransactionListenerClick(currentItem.companyNo, currentItem.jobNo)
        }

    }

    override fun getItemCount(): Int {
        var limit: Int = 4
        return Math.min(joblist.size, limit)
    }
}