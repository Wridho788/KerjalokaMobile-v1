package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.listCity

class EditCityAdapter(private val listCity: List<listCity>):
    RecyclerView.Adapter<EditCityAdapter.EditCity>()
{
    inner class EditCity(view: View): RecyclerView.ViewHolder(view){
        var item: TextView

        init {
            item = view.findViewById<TextView>(R.id.item_modal)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditCity {
        val view = View.inflate(parent.context, R.layout.modal_list, null)
        return EditCity(view)
    }

    override fun onBindViewHolder(holder: EditCity, position: Int) {
        val currentItem = listCity[position]
        holder.item.text= currentItem.city
    }

    override fun getItemCount(): Int {
        return listCity.size
    }
}