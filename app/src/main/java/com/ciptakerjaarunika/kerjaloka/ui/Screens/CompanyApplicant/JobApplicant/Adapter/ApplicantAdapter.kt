package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.JobApplicant.Adapter

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.JobApplicant.Model.applicantModel

class ApplicantAdapter(private val applicantModel: List<applicantModel>) : RecyclerView.Adapter<ApplicantAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var nameApplicant: TextView
        var locationApplicant: TextView
        var statusApplicant: TextView
        var profileApplicant: ImageView

        init {
            nameApplicant = itemView.findViewById(R.id.name_applicant)
            locationApplicant = itemView.findViewById(R.id.location_applicant)
            statusApplicant = itemView.findViewById(R.id.status_applicant_text)
            profileApplicant = itemView.findViewById(R.id.logo_applicant)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.item_applicant, null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = applicantModel[position]
        holder.nameApplicant.text = currentItem.NameApplicant
        holder.locationApplicant.text = currentItem.LocationApplicant
        val status = currentItem.status
        if (status == true) {
            holder.statusApplicant.text = "Qualified"
            holder.statusApplicant.setTextColor(R.color.green_300)
        } else {
            holder.statusApplicant.text = "Not Qualified"
            holder.statusApplicant.setTextColor(Color.RED)
        }
        Glide.with(holder.itemView.context).load(currentItem.Picture).centerCrop().into(holder.profileApplicant)

//        holder.cardApplicantJob.setOnClickListener {
//            onFragmentClickListener?.goToListJobApplicant()
//        }
    }

    override fun getItemCount(): Int {
        return applicantModel.size
    }
}