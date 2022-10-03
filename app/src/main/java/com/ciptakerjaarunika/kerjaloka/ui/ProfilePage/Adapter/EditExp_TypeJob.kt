package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.model.Data.JobTypeFilter
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.typeJob

class EditExp_TypeJob(private val typeList: List<JobTypeFilter>):
    RecyclerView.Adapter<EditExp_TypeJob.ChooseType>()
{

    inner class ChooseType(view: View) : RecyclerView.ViewHolder(view) {

        var item: TextView

        init {
            item = view.findViewById<TextView>(R.id.item_modal)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChooseType {
        val view = View.inflate(parent.context, R.layout.modal_list, null)
        return ChooseType(view)
    }

    override fun onBindViewHolder(holder: ChooseType, position: Int) {
        val currentItem = typeList[position]
        holder.item.text= currentItem.jobTypeName
    }

    override fun getItemCount(): Int {
        return typeList.size
    }

}