package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.Listener.JobDetail
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.model.Data
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.JobAPI
import com.ciptakerjaarunika.kerjaloka.model.Job.jobHomeListData


class Companyjobs_adapter (private val joblist: List<Data>, private val listener: JobDetail):
    RecyclerView.Adapter<Companyjobs_adapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var jobTitle: TextView
        var jobInput: TextView
        var jobStatus: TextView
        var jobExpired: TextView
        var jobAuthor: TextView
        var card: RelativeLayout

        init {
            jobTitle = itemView.findViewById(R.id.company_job_title)
            jobInput = itemView.findViewById(R.id.company_job_input)
            jobExpired = itemView.findViewById(R.id.company_job_expired)
            jobStatus = itemView.findViewById(R.id.company_job_status)
            jobAuthor = itemView.findViewById(R.id.company_job_author)
            card = itemView.findViewById(R.id.cardJob)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.section_company_jobs, null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = joblist[position]
        holder.jobTitle.text=currentItem.jobPosition
        holder.jobInput.text=currentItem.createdOn
        if (currentItem.publish==true){
            holder.jobStatus.text="Aktif"
        }
        else{
            holder.jobStatus.text="Draft"
        }
        holder.jobExpired.text=currentItem.expired
        holder.jobAuthor.text=currentItem.createdBy
        holder.card.setOnClickListener{
            listener.jobDetail(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return joblist?.size ?:0
    }

}