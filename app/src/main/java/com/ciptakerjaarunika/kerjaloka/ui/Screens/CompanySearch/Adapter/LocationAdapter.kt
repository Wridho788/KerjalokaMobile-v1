package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.model.Data.LocationFilter

class LocationAdapter(var value : List<Int>,var dataSet: List<LocationFilter>, val context: Context) :
    RecyclerView.Adapter<LocationAdapter.ViewHolder?>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtLocation: TextView
        var checkBox: CheckBox

        init {
            txtLocation = itemView.findViewById(R.id.txt_location)
            checkBox = itemView.findViewById(R.id.check_location)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.item_location, null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = dataSet[position]
        holder.txtLocation.text = "${currentItem.city}, ${currentItem.province}"
        holder.checkBox.isChecked = value.any { data ->  data == currentItem.locationsNo}
        holder.checkBox.setOnClickListener{
            currentItem.checked = holder.checkBox.isChecked
            dataSet[position].checked = holder.checkBox.isChecked
        }
    }
}


