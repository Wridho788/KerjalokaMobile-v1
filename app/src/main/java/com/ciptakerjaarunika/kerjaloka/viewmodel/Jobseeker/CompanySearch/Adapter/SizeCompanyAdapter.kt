package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.CompanySearch.Adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.CompanySearch.Model.size_company_model

class SizeCompanyAdapter(
    var value : List<Int>,
    var dataSet: List<size_company_model>,
    val context: Context
) : RecyclerView.Adapter<SizeCompanyAdapter.ViewHolder?>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var txtSizename: TextView
        var checkbox: CheckBox
        init {
            txtSizename = itemView.findViewById(R.id.txt_sizeName)
            checkbox = itemView.findViewById(R.id.checkBox_size)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.item_size_company, null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = dataSet[position]
        holder.txtSizename.text = currentItem.sizeName
        holder.checkbox.isChecked = value.any { data -> data == currentItem.sizeNo}
        holder.checkbox.setOnClickListener{
            currentItem.checked = holder.checkbox.isChecked
            dataSet[position].checked = holder.checkbox.isChecked
        }
    }
}