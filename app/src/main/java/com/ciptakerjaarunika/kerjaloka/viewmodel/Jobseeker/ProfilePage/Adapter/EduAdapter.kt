package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.Adapter

import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.model.Profile.JobseekerEducations
import com.ciptakerjaarunika.kerjaloka.utils.DateUtils
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.iCvPage
import java.time.LocalDateTime

class EduAdapter(private val eduList: List<JobseekerEducations>, val iCvPage: iCvPage) :
    RecyclerView.Adapter<EduAdapter.edu>() {

    inner class edu(view: View) : RecyclerView.ViewHolder(view) {

        var schName: TextView
        var loc: TextView
        var drt: TextView
        var gpa: TextView
        var delete: ImageButton
        var edit: ImageButton

        init {
            schName = view.findViewById(R.id.school)
            loc = view.findViewById(R.id.eduLoc)
            drt = view.findViewById(R.id.eduDuration)
            gpa = view.findViewById(R.id.gpa)
            delete = view.findViewById(R.id.delete_btn)
            edit = view.findViewById(R.id.edit_btn)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): edu {
        val view = View.inflate(parent.context, R.layout.card_education, null)
        view.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        return edu(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: edu, position: Int) {
        val currentItem = eduList[position]
        holder.schName.text = currentItem.educationMajorName + " - " + currentItem.educationSchool
        holder.loc.text = currentItem.educationCityName + ", " + currentItem.educationCountry
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            holder.drt.text = DateUtils().GetDateValueWithFormat(
                currentItem.educationBeginAt,
                "MMMM yyyy"
            ) + " - " + getEnded(currentItem.educationBeginAt, currentItem.educationEndedAt)
        }

        holder.gpa.text = currentItem.gpa.toString()

        holder.delete.setOnClickListener {
            iCvPage.deleteEducation(currentItem)
        }
        holder.edit.setOnClickListener {
            iCvPage.editEdu(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return eduList.size
    }

    fun getEnded(start: String, ended: String?): String {
        if (ended != null) {
            val diff = DateUtils().GetDiffMonth(start, ended)
            var endedText = String.format("%.1f", (diff.toFloat() / 12f)).replace(".0", "")
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                "${DateUtils().GetDateValueWithFormat(ended, "MMMM yyyy")} (${endedText} tahun)"
            } else {
                TODO("VERSION.SDK_INT < O")
            }
        } else {
            val diff = DateUtils().GetDiffMonth(start, LocalDateTime.now().toString())
            var endedText = String.format("%.1f", (diff.toFloat() / 12f)).replace(".0", "")
            return "Saat ini (${endedText} tahun)"
        }
    }

}