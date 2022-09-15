package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.model.Profile.JobseekerExperiences

class ExpAdapter(private val expList: List<JobseekerExperiences>):
    RecyclerView.Adapter<ExpAdapter.exp>()
{

    inner class exp(view: View) : RecyclerView.ViewHolder(view) {

        var pst: TextView
        var loc: TextView
        var drt: TextView

        init {
            pst = view.findViewById<TextView>(R.id.txt_Position)
            loc = view.findViewById<TextView>(R.id.txt_loc)
            drt = view.findViewById<TextView>(R.id.txt_duration)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): exp {
        val view = View.inflate(parent.context, R.layout.card_experience, null)
        return exp(view)
    }

    override fun onBindViewHolder(holder: exp, position: Int) {
        val currentItem = expList[position]
        holder.pst.text= currentItem.experiencePosition + " - " + currentItem.experienceCompanyName
        holder.loc.text= currentItem.experienceCityName + ", " + currentItem.experienceCountry
        holder.drt.text= currentItem.experienceBeginAt + " - " + currentItem.experienceEndedAt
    }

    override fun getItemCount(): Int {
        return expList.size
    }

}