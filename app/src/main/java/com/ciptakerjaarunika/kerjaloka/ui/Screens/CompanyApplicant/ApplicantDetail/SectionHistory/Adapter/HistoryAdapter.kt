package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail.SectionHistory.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail.SectionHistory.Model.HistoryModel

class HistoryAdapter(private val historyModel: List<HistoryModel>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var field: TextView
        var status: TextView
        var createdOn: TextView

        init {
            field = itemView.findViewById(R.id.txt_posisi)
            status = itemView.findViewById(R.id.text_status_applicant)
            createdOn = itemView.findViewById(R.id.text_date)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card_history, null)
        val lp = RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        view.setLayoutParams(lp)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = historyModel[position]
        holder.field.text = item.applicantField
        holder.status.text = item.applicantStatus
        holder.createdOn.text = item.createdOn
    }

    override fun getItemCount(): Int {
        return historyModel.size
    }
}