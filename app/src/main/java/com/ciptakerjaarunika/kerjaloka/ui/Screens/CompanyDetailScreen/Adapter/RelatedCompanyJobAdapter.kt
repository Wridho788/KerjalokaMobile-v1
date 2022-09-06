package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyDetailScreen.Adapter

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
import com.ciptakerjaarunika.kerjaloka.model.CompanyDetail.job
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyDetailScreen.OnFragmentCompanyDetailListener
import com.google.android.material.card.MaterialCardView
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class RelatedCompanyJobAdapter(
    private val companyJobList: List<job>,
    private val onFragmentClickListener: OnFragmentCompanyDetailListener?
) :
    RecyclerView.Adapter<RelatedCompanyJobAdapter.ViewHolder>() {


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var relatedjobPosition: TextView
        var relatedlogo: ImageView
        var relatedjobCompany: TextView
        var relatedjobLocation: TextView
        var relatedJobDate: TextView
        var cardrelatedJob: MaterialCardView

        init {
            relatedjobPosition = itemView.findViewById(R.id.relatedjobPosition)
            relatedlogo = itemView.findViewById(R.id.relatedJoblogo)
            relatedjobCompany = itemView.findViewById(R.id.relatedjobCompany)
            relatedjobLocation = itemView.findViewById(R.id.relatedjobLocation)
            relatedJobDate = itemView.findViewById(R.id.relatedJobDate)
            cardrelatedJob = itemView.findViewById(R.id.card_related_job)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.item_card_job_related, null)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = companyJobList[position]
        holder.relatedjobPosition.text = currentItem.jobPosition
        holder.relatedjobCompany.text = currentItem.company.companyName
        holder.relatedjobLocation.text =
            currentItem.company.location.city + ", " + currentItem.company.location.province

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
                else ->LocalDateTime.parse(time).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
            }

        }

        holder.relatedJobDate.text = dateDiff()
        Glide.with(holder.itemView.context)
            .load(config().portAddress + "/photo/Profile/" + currentItem.company.logo)
            .into(holder.relatedlogo)

        holder.cardrelatedJob.setOnClickListener {
            onFragmentClickListener?.goToJobDetail(currentItem.jobNo, currentItem.company.companyNo)
        }
    }

    override fun getItemCount(): Int {
        return companyJobList.size
    }
}