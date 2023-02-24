package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.JobPage.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.model.Job.SearchJobModel
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.JobPage.IJobPage
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.JobSearch.iSearchJob
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class JobAdapter(
    val ListType: Int,
    private val rJobList: List<SearchJobModel>,
    private val context: Context,
    private val iJobPage: IJobPage,
    private val searchJob: iSearchJob?
) : RecyclerView.Adapter<JobAdapter.ViewHolder>() {


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


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = rJobList[position]
        holder.jobPosition.text = currentItem.jobPosition
        holder.jobCompany.text = currentItem.company.companyName
        holder.jobLocation.text =
            if (currentItem.jobLocation.size > 1) "Banyak lokasi" else currentItem.jobLocation[0].label
        if (position + 1 >= rJobList.size && searchJob != null) {
            searchJob.nextPage()
        }

        val SECOND = 1
        val MINUTE = 60 * SECOND
        val HOUR = 60 * MINUTE
        val DAY = 24 * HOUR
        val WEEK = 7 * DAY

        var time = currentItem.createdOn
        val now = LocalDateTime.now().toString()

        @SuppressLint("SimpleDateFormat")
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
                else -> LocalDateTime.parse(time).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
            }

        }
        holder.timeUploadApplicant.text = dateDiff()

        if (SessionManager(context).user == null) {
            holder.bookmarkedJob.visibility = GONE
        }
        Glide.with(holder.itemView.context)
            .load(config().portAddress + "photo/Profile/" + currentItem.company.logo).fitCenter()
            .into(holder.logo)
        holder.bookmarkedJob.setImageResource(if (currentItem.bookmarked) R.drawable.ic_bookmark_primary_filled else R.drawable.ic_bookmark_primary)

        holder.bookmarkedJob.setOnClickListener {
            iJobPage.BookmarkJob(ListType, currentItem.jobNo.toLong(), position)
        }
        holder.shareableJob.setOnClickListener {
            val text =
                "${currentItem.company.companyName}\n" + "sedang membuka lowongan pekerjaan sebagai '${currentItem.jobPosition}'.\n" + "Lihat informasi selengkapnya ${currentItem.link}"
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TITLE, currentItem.jobPosition)
                putExtra(Intent.EXTRA_TEXT, text)
                type = "text/plain"
            }

            context.startActivity(Intent.createChooser(sendIntent, "Bagikan Informasi Pekerjaan"))
        }
        holder.cardRecommendationJob.setOnClickListener {
            iJobPage.GoToJobDetail(
                currentItem.jobNo.toLong(),
                if (currentItem.company.userNo != null) currentItem.company.userNo else currentItem.company.companyNo
            )
        }
    }

}
