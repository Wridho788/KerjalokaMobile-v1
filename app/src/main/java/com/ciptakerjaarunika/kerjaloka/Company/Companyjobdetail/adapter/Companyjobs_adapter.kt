package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.model.ResponseCompanyJobs
import com.ciptakerjaarunika.kerjaloka.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Companyjobs_adapter(private val joblist: List<ResponseCompanyJobs>) :
    RecyclerView.Adapter<Companyjobs_adapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var jobTitle: TextView
        var jobInput: TextView
        var jobExpired: TextView
        var jobAuthor: TextView

        init {
            jobTitle = itemView.findViewById(R.id.company_job_title)
            jobInput = itemView.findViewById(R.id.company_job_input)
            jobExpired = itemView.findViewById(R.id.company_job_expired)
            jobAuthor = itemView.findViewById(R.id.company_job_author)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.section_company_jobs, null)
        val lp = RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        view.layoutParams = lp
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = joblist[position]
        holder.jobTitle.text = currentItem.jobPosition
        if (!currentItem.createdOn.isNullOrEmpty() && !currentItem.createdOn.isNullOrEmpty()) {

            val createdOn = LocalDateTime.parse(currentItem.createdOn)
                .format(DateTimeFormatter.ofPattern("dd MMMM YYYY")).toString()
            holder.jobInput.text = createdOn
        } else holder.jobInput.text = ""
        if (!currentItem.expired.isNullOrEmpty() && !currentItem.expired.isNullOrBlank()) {
            val expired = LocalDateTime.parse(currentItem.expired)
                .format(DateTimeFormatter.ofPattern("dd MMMM YYYY")).toString()
            holder.jobExpired.text = expired
        }
        holder.jobAuthor.text = currentItem.createdBy
    }

    override fun getItemCount(): Int {
        return joblist.size
    }

}