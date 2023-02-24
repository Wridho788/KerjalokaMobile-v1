package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.Adapter

import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.model.Profile.JobseekerExperiences
import com.ciptakerjaarunika.kerjaloka.utils.DateUtils
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.iCvPage
import java.time.LocalDateTime

class ExpAdapter(private val expList: List<JobseekerExperiences>, val iCvPage: iCvPage) :
    RecyclerView.Adapter<ExpAdapter.exp>() {

    inner class exp(view: View) : RecyclerView.ViewHolder(view) {

        var pst: TextView
        var loc: TextView
        var drt: TextView
        var edit: ImageButton
        var delete: ImageButton

        init {
            pst = view.findViewById(R.id.txt_Position)
            loc = view.findViewById(R.id.txt_loc)
            drt = view.findViewById(R.id.txt_duration)
            edit = view.findViewById(R.id.edit_btn)
            delete = view.findViewById(R.id.delete_btn)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): exp {
        val view = View.inflate(parent.context, R.layout.card_experience, null)
        return exp(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: exp, position: Int) {
        val currentItem = expList[position]
        holder.pst.text = currentItem.experiencePosition + " - " + currentItem.experienceCompanyName
        holder.loc.text = currentItem.experienceCityName + ", " + currentItem.experienceCountry
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            holder.drt.text = DateUtils().GetDateValueWithFormat(
                currentItem.experienceBeginAt,
                "MMMM yyyy"
            ) + " - " + getEnded(currentItem.experienceBeginAt, currentItem.experienceEndedAt)
        }
        holder.edit.setOnClickListener {
            iCvPage.editExp(currentItem)
        }
        holder.delete.setOnClickListener {
            iCvPage.deleteExp(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return expList.size
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