package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.Listener.JobDetail
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.model.DataCount
import com.ciptakerjaarunika.kerjaloka.R
import com.google.android.material.button.MaterialButton
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Companyjobs_adapter(private val joblist: List<DataCount>, private val listener: JobDetail) :
    RecyclerView.Adapter<Companyjobs_adapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var jobTitle: TextView
        var jobInput: TextView
        var jobStatus: TextView
        var jobExpired: TextView
        var jobAuthor: TextView
        var card: RelativeLayout
        var shareJob: MaterialButton
        var imageJob: ImageView

        init {
            imageJob = itemView.findViewById(R.id.img_job)
            jobTitle = itemView.findViewById(R.id.company_job_title)
            jobInput = itemView.findViewById(R.id.company_job_input)
            jobExpired = itemView.findViewById(R.id.company_job_expired)
            jobStatus = itemView.findViewById(R.id.company_job_status)
            jobAuthor = itemView.findViewById(R.id.company_job_author)
            card = itemView.findViewById(R.id.cardJob)
            shareJob = itemView.findViewById(R.id.btn_share)
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
//        var logoComp = SessionManager(context).user?.companyAdditional?.logo
////        Log.d("logo", logoComp.toString())
//        Glide.with(holder.imageJob)
//            .load(config().portAddress + "/photo/Profile/" + SessionManager(context).user?.companyAdditional?.logo).fitCenter()
//            .into(holder.imageJob)
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
        holder.card.setOnClickListener {
            listener.jobDetail(currentItem)
        }
        holder.shareJob.setOnClickListener {
            listener.shareJob(currentItem)
        }
        if (currentItem.publish == true) {
            holder.jobStatus.text = "Aktif"
            holder.jobStatus.setTextColor(Color.parseColor("#27AE60"))
        } else {
            holder.jobStatus.text = "Draft"
            holder.jobStatus.setTextColor(Color.parseColor("#999999"))
        }
        holder.jobAuthor.text = currentItem.createdBy
    }

    override fun getItemCount(): Int {
        return joblist.size
    }

}