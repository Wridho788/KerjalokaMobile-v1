package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Bottomsheet.iUpdate
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Model.location_model

class LocationAdapter(private var dataSet: List<location_model>, val context: Context, val iUpdate: iUpdate) :
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
        holder.txtLocation.text = currentItem.city

        holder.checkBox.setOnClickListener{
            dataSet[position].checked = holder.checkBox.isChecked

            val locations = dataSet.filter { item->
                item.checked
            }
            iUpdate.updateLocation(locations)
        }
    }
//    open fun getListItem(): List<location_model> {
//        return dataSet.filter { item->
//            item.checked
//        }
//    }

}


