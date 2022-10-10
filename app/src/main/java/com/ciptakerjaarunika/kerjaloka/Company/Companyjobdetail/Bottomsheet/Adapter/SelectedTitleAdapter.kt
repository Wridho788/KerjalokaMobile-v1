package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.Bottomsheet.Adapter

import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.Bottomsheet.iUpdateLocation
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.ManageJobPage.iUpdateJobAdditionalInfo
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.ManageJobPage.iUpdateJobBasicInfo
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.iBasicInfoPage
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.model.Data.LocationFilter
import com.ciptakerjaarunika.kerjaloka.model.Job.CompanyJobDetail.JobLocation
import com.ciptakerjaarunika.kerjaloka.model.Job.CompanyJobDetail.JobSkill
import com.ciptakerjaarunika.kerjaloka.model.Job.CompanyJobDetail.JobTitle

class SelectedTitleAdapter(var data : List<JobTitle>, val updateData: iUpdateJobAdditionalInfo):
    RecyclerView.Adapter<SelectedTitleAdapter.EditCity>()
{
    inner class EditCity(view: View): RecyclerView.ViewHolder(view){
        var item: TextView
        var remove:ImageView

        init {
            item = view.findViewById(R.id.name)
            remove = view.findViewById(R.id.remove_btn)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditCity {
        val view = View.inflate(parent.context, R.layout.manage_job_selected_list, null)
        view.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        return EditCity(view)
    }

    override fun onBindViewHolder(holder: EditCity, position: Int) {
        val currentItem = data[position]
        holder.item.text= "${currentItem.titleName}"

        holder.remove.setOnClickListener {
            data = data.toMutableList()?.apply {
                remove(currentItem)
            }!!
            updateData.updateTitle(data)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}