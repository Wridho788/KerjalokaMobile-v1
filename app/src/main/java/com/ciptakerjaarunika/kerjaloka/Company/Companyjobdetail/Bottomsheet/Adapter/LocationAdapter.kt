package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.Bottomsheet.Adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.Bottomsheet.iChooseLocation
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.iUpdatePage1
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.model.Data.LocationFilter

class LocationAdapter(
    private var dataset: List<LocationFilter>?,
    val iChooseLocation: iChooseLocation,
    val iUpdatePage1: iUpdatePage1
) : RecyclerView.Adapter<LocationAdapter.ViewHolder?>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtLocation: TextView

        init {
            txtLocation = itemView.findViewById(R.id.txt_location)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.item_location_job, null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataset!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataset!![position]
        holder.txtLocation.text = item.city + ", " + item.province
        val locationName = item.city + ", " + item.province
        holder.txtLocation.setOnClickListener {
            iUpdatePage1.updatePage1(locationName)
            iChooseLocation.close()
        }
    }

}
