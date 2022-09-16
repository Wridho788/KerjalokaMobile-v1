package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.model.ResponseJobs
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.model.Job.jobHomeListData
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.minat_model
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.record
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.review

class Companyjobs_adapter (private val joblist: List<ResponseJobs>):
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
        val view = View.inflate(parent.context, R.layout.section_company_jobs, null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = joblist[position]
        holder.jobTitle.text=currentItem.jobPosition
        holder.jobInput.text=currentItem.createdOn
        holder.jobExpired.text=currentItem.expired
        holder.jobAuthor.text=currentItem.createdBy
    }

    override fun getItemCount(): Int {
        return joblist?.size ?:0
    }

}