package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail.SectionExperiences

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.JobApplicant.Model.experience
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ExperiencesAdapter(private val experiences: List<experience>) :
    RecyclerView.Adapter<ExperiencesAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var expPosition: TextView
        var expCityName: TextView
        var expBeginAt: TextView

        init {
            expPosition = itemView.findViewById(R.id.experiencePosition)
            expCityName = itemView.findViewById(R.id.experienceCityName)
            expBeginAt = itemView.findViewById(R.id.experienceBeginAt)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_experiences, null)
        val lp = RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        view.layoutParams = lp

        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = experiences[position]
        holder.expPosition.text = item.experiencePosition + " - " + item.experienceCompanyName
        holder.expCityName.text = item.experienceCityName + " - " + item.experienceCountry
        if (item.experienceBeginAt != null) {
            if (item.experienceEndedAt != null) {
                val yearExp = LocalDateTime.parse(item.experienceBeginAt)
                    .format(DateTimeFormatter.ofPattern("MMMM yyyy")) + " - " +
                        LocalDateTime.parse(item.experienceEndedAt)
                            .format(DateTimeFormatter.ofPattern("MMMM yyyy"))
                holder.expBeginAt.text = yearExp
            }
            holder.expBeginAt.text = LocalDateTime.parse(item.experienceBeginAt)
                .format(DateTimeFormatter.ofPattern("MMMM yyyy")) + " - Sekarang"
        } else {
            holder.expBeginAt.text = ""
        }

    }

    override fun getItemCount(): Int {
        return experiences.size
    }
}