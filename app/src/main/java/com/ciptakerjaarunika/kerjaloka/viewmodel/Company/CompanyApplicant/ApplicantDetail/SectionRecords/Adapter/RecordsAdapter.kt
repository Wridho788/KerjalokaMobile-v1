package com.ciptakerjaarunika.kerjaloka.viewmodel.Company.CompanyApplicant.ApplicantDetail.SectionRecords.Adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.CompanyApplicant.ApplicantDetail.SectionRecords.Model.RecordsModel

class RecordsAdapter(private val recordsModel: List<RecordsModel>) :
    RecyclerView.Adapter<RecordsAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var companyName: TextView
        var createdOn: TextView
        var description: TextView

        init {
            companyName = itemView.findViewById(R.id.record_company_name_text)
            createdOn = itemView.findViewById(R.id.record_date_text)
            description = itemView.findViewById(R.id.record_desc_text)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.item_card_record, null)
        val lp = RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        view.setLayoutParams(lp)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = recordsModel[position]
        holder.companyName.text = item.companyName
        holder.createdOn.text = item.createdOn
        holder.description.text = item.description
    }

    override fun getItemCount(): Int {
        return recordsModel.size
    }
}