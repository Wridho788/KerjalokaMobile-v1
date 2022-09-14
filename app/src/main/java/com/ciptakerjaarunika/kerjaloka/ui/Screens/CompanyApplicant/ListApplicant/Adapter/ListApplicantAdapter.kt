package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ListApplicant.Adapter

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ListApplicant.Model.listApplicantJobModel
import com.google.android.material.card.MaterialCardView

class ListApplicantAdapter(private val listApplicantJobModel: List<listApplicantJobModel>) :
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
        val currentItem = listApplicantJobModel[position]
        holder.jobPosition.text = currentItem.jobPosition
        holder.uploadAt.text = currentItem.uploadedAt
        val status = currentItem.status
        if (status == true) {
            holder.status.text = "Aktif"
            holder.status.setTextColor(Color.GREEN)
        } else {
            holder.status.text = "Tidak Aktif"
            holder.status.setTextColor(Color.RED)
        }
        holder.cardApplicantJob.setOnClickListener {
            when (currentItem.jobNo) {
                1 -> Toast.makeText(
                    holder.itemView.context,
                    "Job 1 telah di klik",
                    Toast.LENGTH_SHORT
                ).show()
                2 -> Toast.makeText(
                    holder.itemView.context,
                    "Job 2 telah di klik",
                    Toast.LENGTH_SHORT
                ).show()
                3 -> Toast.makeText(
                    holder.itemView.context,
                    "Job 3 telah di klik",
                    Toast.LENGTH_SHORT
                ).show()
                4 -> Toast.makeText(
                    holder.itemView.context,
                    "Job 4 telah di klik",
                    Toast.LENGTH_SHORT
                ).show()
                5 -> Toast.makeText(
                    holder.itemView.context,
                    "Job 5 telah di klik",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return listApplicantJobModel.size
    }
}