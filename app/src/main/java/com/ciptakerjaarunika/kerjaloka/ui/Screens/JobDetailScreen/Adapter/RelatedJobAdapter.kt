package com.ciptakerjaarunika.kerjaloka.ui.Screens.JobDetailScreen.Adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobDetailScreen.Model.relatedJobModel
import com.google.android.material.card.MaterialCardView

class RelatedJobAdapter(private val rJobModel: List<relatedJobModel>) :
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = rJobModel[position]
        holder.relatedjobPosition.text = currentItem.jobPosition
        holder.relatedjobCompany.text = currentItem.jobCompany
        holder.relatedjobLocation.text = currentItem.jobLocation
        holder.relatedJobDate.text = currentItem.timeUploadApplicant
        Glide.with(holder.itemView.context).load(currentItem.logo).into(holder.relatedlogo)
        holder.cardrelatedJob.setOnClickListener {
            when (currentItem.jobNo) {
                1 -> Toast.makeText(holder.itemView.context, "Job 1", Toast.LENGTH_SHORT).show()
                2 -> Toast.makeText(holder.itemView.context, "Job 2", Toast.LENGTH_SHORT).show()
                3 -> Toast.makeText(holder.itemView.context, "Job 3", Toast.LENGTH_SHORT).show()
                4 -> Toast.makeText(holder.itemView.context, "Job 4", Toast.LENGTH_SHORT).show()
                5 -> Toast.makeText(holder.itemView.context, "Job 5", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return rJobModel.size
    }
}