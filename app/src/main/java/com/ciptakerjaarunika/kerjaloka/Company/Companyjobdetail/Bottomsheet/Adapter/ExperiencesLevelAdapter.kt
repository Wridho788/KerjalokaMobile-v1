package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.Bottomsheet.Adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.Bottomsheet.iChooseExperienceLevel
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.iUpdatePage2
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.model.Data.ExperienceLevelFilter

class ExperiencesLevelAdapter(private var dataset: List<ExperienceLevelFilter>?, val iChooseExperienceLevel: iChooseExperienceLevel, val iUpdatePage2: iUpdatePage2): RecyclerView.Adapter<ExperiencesLevelAdapter.ViewHolder?>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val txtExperienceLevel: TextView
        init {
            txtExperienceLevel = itemView.findViewById(R.id.txt_location)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.item_location_job,null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataset!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataset!![position]
        holder.txtExperienceLevel.text = item.experienceLevelName
        holder.txtExperienceLevel.setOnClickListener { iChooseExperienceLevel.close()
        iUpdatePage2.updateExperience(item.experienceLevelName)}
    }

}
