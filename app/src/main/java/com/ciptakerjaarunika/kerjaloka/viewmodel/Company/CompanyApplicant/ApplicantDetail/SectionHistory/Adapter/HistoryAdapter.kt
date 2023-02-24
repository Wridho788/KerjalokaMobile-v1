package com.ciptakerjaarunika.kerjaloka.viewmodel.Company.CompanyApplicant.ApplicantDetail.SectionHistory.Adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.enum.ApplicanStatusType
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.CompanyApplicant.JobApplicant.Model.jobApplicantHistory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HistoryAdapter(private val jobApplicationHistory: List<List<jobApplicantHistory>>) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var field: TextView
        var status: TextView
        var createdOn: TextView

        init {
            field = itemView.findViewById(R.id.jobPosition)
            status = itemView.findViewById(R.id.applicationStatusHistory)
            createdOn = itemView.findViewById(R.id.lastUpdated)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card_history, null)
        val lp = RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        view.layoutParams = lp

        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = jobApplicationHistory[position][0]
        holder.field.text = item.jobPosition
        if (item.applicationStatusHistory == ApplicanStatusType.Applied.value) {
            holder.status.text = ApplicanStatusType.Applied.name
        } else if (item.applicationStatusHistory == ApplicanStatusType.ShortList.value) {
            holder.status.text = ApplicanStatusType.ShortList.name
        } else if (item.applicationStatusHistory == ApplicanStatusType.Test.value) {
            holder.status.text = ApplicanStatusType.Test.name
        } else if (item.applicationStatusHistory == ApplicanStatusType.Interview.value) {
            holder.status.text = ApplicanStatusType.Interview.name
        } else if (item.applicationStatusHistory == ApplicanStatusType.Accepted.value) {
            holder.status.text = ApplicanStatusType.Accepted.name
        } else {
            holder.status.text = ApplicanStatusType.Rejected.name
        }
        holder.createdOn.text = LocalDateTime.parse(item.lastUpdated)
            .format(DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm"))
    }

    override fun getItemCount(): Int {
        return jobApplicationHistory.size
    }
}