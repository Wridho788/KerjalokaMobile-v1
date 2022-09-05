package com.ciptakerjaarunika.kerjaloka.ui.Screens.JobDetailScreen.Adapter

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.ui.HomePage.Model.job
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobDetailScreen.OnFragmentClickListener
import com.google.android.material.card.MaterialCardView

class RelatedJobAdapter(
    private val jobList: List<job>,
    private val onFragmentClickListener: OnFragmentClickListener
) :
    RecyclerView.Adapter<RelatedJobAdapter.ViewHolder>() {


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

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = jobList[position]
        holder.relatedjobPosition.text = currentItem.jobPosition
        holder.relatedjobCompany.text = currentItem.company.companyName
        holder.relatedjobLocation.text =
            currentItem.company.location.city + ", " + currentItem.company.location.province

//        holder.relatedJobDate.text = currentItem.createdOn
        Glide.with(holder.itemView.context)
            .load(config().portAddress + "/photo/Profile/" + currentItem.company.logo)
            .into(holder.relatedlogo)

        holder.cardrelatedJob.setOnClickListener {
            onFragmentClickListener.onFragmentClick(
                currentItem.jobNo,
                currentItem.company.companyNo
            )
        }
    }

    override fun getItemCount(): Int {
        return jobList.size
    }
}