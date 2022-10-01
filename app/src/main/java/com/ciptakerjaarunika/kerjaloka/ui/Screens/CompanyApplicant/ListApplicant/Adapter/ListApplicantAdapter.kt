package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ListApplicant.Adapter

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ListApplicant.Model.listApplicantJobModel
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ListApplicant.OnFragmentClickListener
import com.google.android.material.card.MaterialCardView
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class ListApplicantAdapter(
    private val context: Context,
    private val listApplicantJobModel: List<listApplicantJobModel>?, private val onFragmentClickListener: OnFragmentClickListener? ) :
    RecyclerView.Adapter<ListApplicantAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var jobPosition: TextView
        var uploadAt: TextView
        var status: TextView
        var cardApplicantJob: MaterialCardView

        init {
            jobPosition = itemView.findViewById(R.id.job_title_applicant)
            uploadAt = itemView.findViewById(R.id.txt_uploadAt)
            status = itemView.findViewById(R.id.status_applicant_text)
            cardApplicantJob = itemView.findViewById(R.id.card_applicant_job)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.item_card_applicant, null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = listApplicantJobModel?.get(position)
        holder.jobPosition.text = currentItem?.jobPosition

        val SECOND = 1
        val MINUTE = 60 * SECOND
        val HOUR = 60 * MINUTE
        val DAY = 24 * HOUR
        val WEEK = 7 * DAY
        var time = currentItem?.createdOn
        val now = LocalDateTime.now().toString()

        fun GetDateValue(value: String): Date {
            val temp = value.split("T")
            val time = temp[1].split(":")
            val date = "${temp[0]} ${time[0]}:${time[1]}"
            var dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
            return dateFormat.parse(date)
        }

        fun dateDiff(): String {
            val date1 = GetDateValue(time!!).time
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

        holder.uploadAt.text = dateDiff()
        val status = currentItem?.publish
        if (status == true) {
            holder.status.text = "Aktif"
            holder.status.setTextColor(Color.parseColor("#27AE60"))
        } else {
            holder.status.text = "Tidak Aktif"
            holder.status.setTextColor(Color.parseColor("#C12929"))
        }
        holder.cardApplicantJob.setOnClickListener {
           onFragmentClickListener?.goToListJobApplicant(currentItem?.jobNo?.toLong()!!)
        }
    }

    override fun getItemCount(): Int {
        return listApplicantJobModel?.size ?: 0
    }
}