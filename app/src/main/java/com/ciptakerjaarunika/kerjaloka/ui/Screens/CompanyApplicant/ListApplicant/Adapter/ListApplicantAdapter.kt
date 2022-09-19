package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ListApplicant.Adapter

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ListApplicant.Model.listApplicantJobModel
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ListApplicant.OnFragmentClickListener
import com.google.android.material.card.MaterialCardView

class ListApplicantAdapter(private val listApplicantJobModel: List<listApplicantJobModel>, private val onFragmentClickListener: OnFragmentClickListener? ) :
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
           onFragmentClickListener?.goToListJobApplicant()
        }
    }

    override fun getItemCount(): Int {
        return listApplicantJobModel.size
    }
}