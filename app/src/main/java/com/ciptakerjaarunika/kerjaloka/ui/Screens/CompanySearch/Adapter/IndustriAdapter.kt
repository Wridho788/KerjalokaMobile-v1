package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Bottomsheet.iUpdate
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Model.industri_model

class IndustriAdapter(private var dataSet: List<industri_model>, val context: Context, val iUpdate: iUpdate) :
    RecyclerView.Adapter<IndustriAdapter.ViewHolder?>(){

        inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            var txtFieldname: TextView
            var checkbox: CheckBox

            init {
                txtFieldname = itemView.findViewById(R.id.txt_fieldname)
                checkbox = itemView.findViewById(R.id.checkBox_industri)
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.item_industri,null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = dataSet[position]
        holder.txtFieldname.text = currentItem.fieldName
        holder.checkbox.setOnClickListener{
            dataSet[position].checked = holder.checkbox.isChecked
            val field = dataSet.filter {
                item -> item.checked
            }
            iUpdate.updateField(field)
        }

    }
}