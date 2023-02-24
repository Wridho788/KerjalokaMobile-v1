package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.Adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.model.Data.City

class EditExp_CompLoc(private val cityList: List<City>) :
    RecyclerView.Adapter<EditExp_CompLoc.ChooseCity>() {

    inner class ChooseCity(view: View) : RecyclerView.ViewHolder(view) {

        var item: TextView

        init {
            item = view.findViewById<TextView>(R.id.item_modal)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChooseCity {
        val view = View.inflate(parent.context, R.layout.modal_list, null)
        return ChooseCity(view)
    }

    override fun onBindViewHolder(holder: ChooseCity, position: Int) {
        val currentItem = cityList[position]
        holder.item.text = currentItem.cityName
    }

    override fun getItemCount(): Int {
        return cityList.size
    }

}