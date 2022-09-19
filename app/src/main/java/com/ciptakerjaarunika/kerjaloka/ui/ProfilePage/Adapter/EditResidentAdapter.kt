package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.model.Data.Resident

class EditResidentAdapter(private val residentItems: List<Resident>):
    RecyclerView.Adapter<EditResidentAdapter.EditResident>()
{

    inner class EditResident(view: View) : RecyclerView.ViewHolder(view) {

        var item: TextView

        init {
            item = view.findViewById<TextView>(R.id.item_modal)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditResident {
        val view = View.inflate(parent.context, R.layout.modal_list, null)
        return EditResident(view)
    }

    override fun onBindViewHolder(holder: EditResident, position: Int) {
        val currentItem = residentItems[position]
        holder.item.text= currentItem.residentName
    }

    override fun getItemCount(): Int {
        return residentItems.size
    }

}