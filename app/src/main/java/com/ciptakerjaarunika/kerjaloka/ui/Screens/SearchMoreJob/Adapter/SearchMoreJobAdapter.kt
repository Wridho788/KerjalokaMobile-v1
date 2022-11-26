package com.ciptakerjaarunika.kerjaloka.ui.Screens.SearchMoreJob.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.Screens.SearchMoreJob.OnFragmentClickListener
import com.ciptakerjaarunika.kerjaloka.ui.Screens.SearchScreen.Model.jobList
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class SearchMoreJobAdapter(
    private val jobList: List<jobList>,
    private val context: Context,
    private val onFragmentClickListener: OnFragmentClickListener
) :
    RecyclerView.Adapter<SearchMoreJobAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var jobPosition: TextView
        var jobLocation: TextView
        var jobCompany: TextView
        var createOn: TextView
        var logo: ImageView
        var cardJob: MaterialCardView
        var bookmarkedJob: ImageView
        var shareableJob: MaterialButton

        init {
            jobPosition = itemView.findViewById(R.id.jobPosition)
            jobLocation = itemView.findViewById(R.id.jobLocation)
            jobCompany = itemView.findViewById(R.id.jobCompany)
            createOn = itemView.findViewById(R.id.createdOn)
            logo = itemView.findViewById(R.id.logo)
            cardJob = itemView.findViewById(R.id.card_recommendation_job)
            bookmarkedJob = itemView.findViewById(R.id.btn_bookmark)
            shareableJob = itemView.findViewById(R.id.btn_share)

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchMoreJobAdapter.ViewHolder {
        val view = View.inflate(parent.context, R.layout.item_card_recommendation_job, null)
        view.layoutParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return ViewHolder(view)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: SearchMoreJobAdapter.ViewHolder, position: Int) {
        val currentItem = jobList[position]
        holder.jobPosition.text = currentItem.jobPosition
        holder.jobCompany.text = currentItem.companyName
        holder.jobLocation.text = currentItem.jobLocations[0].location
        Glide.with(holder.itemView.context)
            .load(config().portAddress + "/photo/Profile/" + currentItem.photo).fitCenter()
            .into(holder.logo)
        holder.bookmarkedJob.setImageResource(if (currentItem.bookmarked) R.drawable.ic_bookmark_primary_filled else R.drawable.ic_bookmark_primary)

        if (SessionManager(context).user == null) {
            holder.bookmarkedJob.visibility = View.GONE
        }
        holder.bookmarkedJob.setOnClickListener {
//            onFragmentClickListener.BookmarkJob(currentItem.jobNo, position)

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
        holder.shareableJob.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TITLE, currentItem.jobPosition)
                putExtra(Intent.EXTRA_TEXT, currentItem.link)
                type = "text/plain"

            }
            val shareIntent = Intent.createChooser(sendIntent, currentItem.jobPosition)
            ContextCompat.startActivity(context, shareIntent, null)
        }
        holder.cardJob.setOnClickListener {
            onFragmentClickListener.onJobDetailPage(currentItem.companyNo,currentItem.jobNo)
        }
    }

    override fun getItemCount(): Int {
        return jobList.size
    }
}