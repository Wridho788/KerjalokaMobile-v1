package com.ciptakerjaarunika.kerjaloka.ui.Screens.JobSearch.Adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.Company.Profile.data
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.model.Data.ExperienceLevelFilter
import com.ciptakerjaarunika.kerjaloka.model.Data.JobTypeFilter
import com.ciptakerjaarunika.kerjaloka.model.Data.LocationFilter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Bottomsheet.iUpdate
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobSearch.iUpdateValue

class ExperienceLevelAdapter(private var dataSet: List<ExperienceLevelFilter>, val context: Context, val iUpdateValue: iUpdateValue) :
    RecyclerView.Adapter<ExperienceLevelAdapter.ViewHolder?>(){

        inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            var filterText: TextView
            var checked: CheckBox

            init {
                filterText = itemView.findViewById(R.id.filter_txt)
                checked = itemView.findViewById(R.id.checked)
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.item_filter,null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = dataSet[position]
        holder.filterText.text = currentItem.experienceLevelName
        holder.checked.setOnClickListener{
            currentItem.checked = holder.checked.isChecked
            dataSet[position].checked = holder.checked.isChecked
            iUpdateValue.updateExperienceLevel(currentItem)
        }

    }
}