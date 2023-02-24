package com.ciptakerjaarunika.kerjaloka.viewmodel.Company.CompanyApplicant.ApplicantDetail.SectionEducations

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.CompanyApplicant.JobApplicant.Model.education
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class EducationsAdapter(private val education: List<education>) :
    RecyclerView.Adapter<EducationsAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var eduMajorName: TextView
        var eduCityName: TextView
        var eduBeginAt: TextView
        var gpaText: TextView

        init {
            eduMajorName = itemView.findViewById(R.id.educationSchool)
            eduCityName = itemView.findViewById(R.id.educationCityName)
            eduBeginAt = itemView.findViewById(R.id.educationBeginAt)
            gpaText = itemView.findViewById(R.id.gpaText)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_educations, null)
        val lp = RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        view.layoutParams = lp

        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = education[position]
        holder.eduMajorName.text = item.educationMajorName + " - " + item.educationSchool
        holder.eduCityName.text = item.educationCityName + " - " + item.educationCountry
        holder.eduBeginAt.text = LocalDateTime.parse(item.educationBeginAt)
            .format(DateTimeFormatter.ofPattern("MMMM yyyy")) + " - " + LocalDateTime.parse(item.educationEndedAt)
            .format(DateTimeFormatter.ofPattern("MMMM yyyy"))
        holder.gpaText.text = item.gpa.toString() + " dari 4"
    }

    override fun getItemCount(): Int {
        return education.size
    }
}