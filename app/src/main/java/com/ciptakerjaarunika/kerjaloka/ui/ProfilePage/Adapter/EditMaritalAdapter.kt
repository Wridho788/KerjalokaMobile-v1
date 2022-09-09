package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.maritalStatus

class EditMaritalAdapter(private val listStatus: List<maritalStatus>):
    RecyclerView.Adapter<EditMaritalAdapter.EditMarital>()
{
    inner class EditMarital(view: View): RecyclerView.ViewHolder(view){
        var item: TextView

        init {
            item = view.findViewById<TextView>(R.id.item_modal)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditMarital {
        val view = View.inflate(parent.context, R.layout.modal_list, null)
        return EditMarital(view)
    }

    override fun onBindViewHolder(holder: EditMarital, position: Int) {
        val currentItem = listStatus[position]
        holder.item.text= currentItem.maritalName
    }

    override fun getItemCount(): Int {
        return listStatus.size
    }
}