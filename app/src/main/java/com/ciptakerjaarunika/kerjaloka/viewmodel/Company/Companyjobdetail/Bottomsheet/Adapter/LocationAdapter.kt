package com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Companyjobdetail.Bottomsheet.Adapter

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.model.Data.LocationFilter
import com.ciptakerjaarunika.kerjaloka.model.Job.CompanyJobDetail.JobLocation
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Companyjobdetail.Bottomsheet.iUpdateLocation

class LocationAdapter(
    var data: List<JobLocation>,
    private val locations: List<LocationFilter>,
    private val iUpdateLocation: iUpdateLocation
) :
    RecyclerView.Adapter<LocationAdapter.EditCity>() {
    inner class EditCity(view: View) : RecyclerView.ViewHolder(view) {
        var item: TextView
        var checkbox: CheckBox

        init {
            item = view.findViewById(R.id.txt_location)
            checkbox = view.findViewById(R.id.check_location)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditCity {
        val view = View.inflate(parent.context, R.layout.item_location, null)
        view.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return EditCity(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: EditCity, position: Int) {
        val currentItem = locations[position]
        holder.item.text = "${currentItem.city}, ${currentItem.province}"

        var currentData = data.find { item -> item.cityNo == currentItem.locationsNo }
        holder.checkbox.setOnClickListener {
            if (currentData != null) {
                data = data.toMutableList().apply {
                    remove(currentData)
                }
            } else {
                data += JobLocation(
                    currentItem.locationsNo,
                    null,
                    null,
                    holder.item.text.toString()
                )
            }
            iUpdateLocation.updateLocation(data)
        }
        holder.checkbox.isChecked = currentData != null
    }

    override fun getItemCount(): Int {
        return locations.size
    }
}