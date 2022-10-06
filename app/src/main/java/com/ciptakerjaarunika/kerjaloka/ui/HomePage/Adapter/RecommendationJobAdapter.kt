package com.ciptakerjaarunika.kerjaloka.ui.HomePage.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.JobAPI
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.model.Job.SearchJobModel
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.HomePage.Model.rJobModel
import com.ciptakerjaarunika.kerjaloka.ui.HomePage.OnFragmentClickListener
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobPage.Adapter.JobAdapter
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class RecommendationJobAdapter(
    private val context: Context,
    private var rJobList: List<SearchJobModel>?,
    private val onFragmentClick: OnFragmentClickListener,
) :
    RecyclerView.Adapter<RecommendationJobAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var jobPosition: TextView
        var logo: ImageView
        var companyName: TextView
        var jobLocation: TextView
        var CreatedOn: TextView
        var bookmarkedJob: ImageView
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
        view.layoutParams= ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return rJobList?.take(5)?.size ?: 0
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (rJobList != null) {
            val currentItem = rJobList!![position]
            holder.jobPosition.text = currentItem.jobPosition
            holder.companyName.text = currentItem.company.companyName
            holder.jobLocation.text = if(currentItem.jobLocation.size > 1) "Banyak lokasi" else currentItem.jobLocation[0].label
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
                .load(config().portAddress + "/photo/Profile/" + currentItem.company.logo).fitCenter()
                .into(holder.logo)
            holder.bookmarkedJob.setImageResource(if (currentItem.bookmarked) R.drawable.ic_bookmark_primary_filled else R.drawable.ic_bookmark_primary)

            if(SessionManager(context).user == null){
                holder.bookmarkedJob.visibility = GONE
            }
            holder.bookmarkedJob.setOnClickListener {
                JobAPI().BookmarkJob(currentItem.jobNo.toLong(), !currentItem.bookmarked, context) {
                    if(it != null) {
                        if (it.code == 210) {
                            currentItem.bookmarked = !currentItem.bookmarked
                            rJobList!![position].bookmarked = rJobList!![position].bookmarked
                            onFragmentClick.bookmarkJob(rJobList!!)
                        } else {
                            Toast.makeText(context, it.Message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            holder.shareableJob.setOnClickListener {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TITLE, currentItem.jobPosition)
                    putExtra(Intent.EXTRA_TEXT, currentItem.link)
                    type = "text/plain"

                }
                val shareIntent = Intent.createChooser(sendIntent, currentItem.jobPosition)
                startActivity(context, shareIntent, null)
            }
            holder.cardRecommendationJob.setOnClickListener {
                onFragmentClick.onFragmentClick(currentItem.jobNo.toLong(), currentItem.company.companyNo)
            }
        }
    }

}
