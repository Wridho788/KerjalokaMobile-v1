package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.*

class EduAdapter(private val eduList: List<education>):
    RecyclerView.Adapter<EduAdapter.edu>()
{

    inner class edu(view: View) : RecyclerView.ViewHolder(view) {

        var schName: TextView
        var loc: TextView
        var drt: TextView
        var gpa: TextView

        init {
            schName = view.findViewById<TextView>(R.id.school)
            loc = view.findViewById<TextView>(R.id.eduLoc)
            drt = view.findViewById<TextView>(R.id.eduDuration)
            gpa = view.findViewById<TextView>(R.id.gpa)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): edu {
        val view = View.inflate(parent.context, R.layout.card_education, null)
        return edu(view)
    }

    override fun onBindViewHolder(holder: edu, position: Int) {
        val currentItem = eduList[position]
        holder.schName.text= currentItem.educationMajorName + " - " + currentItem.educationSchool
        holder.loc.text= currentItem.educationCityName + ", " + currentItem.educationCountry
        holder.drt.text= currentItem.educationBeginAt + " - " + currentItem.educationEndedAt
        holder.gpa.text= currentItem.gpa.toString()
    }

    override fun getItemCount(): Int {
        return eduList.size
    }

}