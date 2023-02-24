package com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Companyjobdetail.Bottomsheet.Adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.model.Job.CompanyJobDetail.JobSkill
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Companyjobdetail.ManageJobPage.iUpdateJobAdditionalInfo

class SelectedSkillAdapter(var data: List<JobSkill>, val updateData: iUpdateJobAdditionalInfo) :
    RecyclerView.Adapter<SelectedSkillAdapter.EditCity>() {
    inner class EditCity(view: View) : RecyclerView.ViewHolder(view) {
        var item: TextView
        var remove: ImageView

        init {
            item = view.findViewById(R.id.name)
            remove = view.findViewById(R.id.remove_btn)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditCity {
        val view = View.inflate(parent.context, R.layout.manage_job_selected_list, null)
        view.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return EditCity(view)
    }

    override fun onBindViewHolder(holder: EditCity, position: Int) {
        val currentItem = data[position]
        holder.item.text = "${currentItem.skillName}"

        holder.remove.setOnClickListener {
            data = data.toMutableList().apply {
                remove(currentItem)
            }
            updateData.updateSkill(data)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}