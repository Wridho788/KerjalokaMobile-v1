package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyCompareJobseeker.bottomsheet.Adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyCompareJobseeker.bottomsheet.Model.AddJobseekerModel

class AddJobseekerAdapter(private val addJobseekerModel: List<AddJobseekerModel>) : RecyclerView.Adapter<AddJobseekerAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameApplicant: TextView
        var location: TextView
        var jobExperience: TextView
        var jobLocation: TextView
        var pendidikan: TextView
        var instansi: TextView
        init {
            nameApplicant = itemView.findViewById(R.id.name_applicant)
            location = itemView.findViewById(R.id.location_text)
            jobLocation = itemView.findViewById(R.id.locationJob)
            jobExperience = itemView.findViewById(R.id.positionJob)
            pendidikan = itemView.findViewById(R.id.txt_pendidikan)
            instansi = itemView.findViewById(R.id.instansi)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.item_card_pelamar_list, null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = addJobseekerModel[position]
        holder.nameApplicant.text = item.nameApplicant
        holder.jobExperience.text = item.jobExperience
        holder.jobLocation.text = item.jobLocation
        holder.pendidikan.text = item.pendidikan
        holder.instansi.text = item.instansi
    }

    override fun getItemCount(): Int {
        return addJobseekerModel.size
    }
}