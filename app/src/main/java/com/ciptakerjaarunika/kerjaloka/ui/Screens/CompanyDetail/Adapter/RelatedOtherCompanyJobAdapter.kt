package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyDetail.Adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.model.CompanyDetail.job
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyDetail.Model.relatedOtherCompanyJobModel
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobDetailScreen.IJobDetail
import com.google.android.material.card.MaterialCardView

class RelatedOtherCompanyJobAdapter(private val listItem : List<job>?, private val iJobDetail: IJobDetail) :
    RecyclerView.Adapter<RelatedOtherCompanyJobAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var relatedjobPosition: TextView
        var relatedlogo: ImageView
        var relatedjobCompany: TextView
        var relatedjobLocation: TextView
        var relatedJobDate: TextView
        var cardrelatedJob: MaterialCardView

        init {
            relatedjobPosition = itemView.findViewById(R.id.relatedjobPosition)
            relatedlogo = itemView.findViewById(R.id.relatedJoblogo)
            relatedjobCompany = itemView.findViewById(R.id.relatedjobCompany)
            relatedjobLocation = itemView.findViewById(R.id.relatedjobLocation)
            relatedJobDate = itemView.findViewById(R.id.relatedJobDate)
            cardrelatedJob = itemView.findViewById(R.id.card_related_job)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.item_card_job_related, null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = listItem?.get(position)
        holder.relatedjobPosition.text = currentItem?.jobPosition
        holder.relatedjobCompany.text = currentItem?.company?.companyName
        holder.relatedjobLocation.text = if(currentItem?.jobLocation?.size!! > 1) "Banyak lokasi" else currentItem?.jobLocation?.get(0)?.location
        holder.relatedJobDate.text = currentItem?.createdOn
        Glide.with(holder.itemView.context).load(config().portAddress + "/photo/Profile/" + currentItem.company.logo).into(holder.relatedlogo)

        holder.cardrelatedJob.setOnClickListener {
            currentItem?.company?.companyNo?.let { it1 -> iJobDetail.onFragmentClick(it1, currentItem.jobNo) }
        }
    }

    override fun getItemCount(): Int {
        return if(listItem == null) 0 else listItem.size
    }
}