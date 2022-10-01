package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter

import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.model.Data.Field
import com.ciptakerjaarunika.kerjaloka.model.Data.FieldFilter
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.minat_model


class MinatAdapter(var dataList: List<FieldFilter>):
    RecyclerView.Adapter<MinatAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var fielName: TextView
        var checkBox: CheckBox

        init {
            fielName = itemView.findViewById(R.id.field_txt)
            checkBox = itemView.findViewById(R.id.checkbox)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.interest_section, null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = dataList[position]
        holder.fielName.text = currentItem.fieldName
        holder.checkBox.isChecked = currentItem.checked == true
        holder.checkBox.setOnClickListener{
            currentItem.checked = holder.checkBox.isChecked
            dataList[position].checked = holder.checkBox.isChecked
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}