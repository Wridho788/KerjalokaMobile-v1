package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.gender
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.religionList

class EditReligionAdapter(private val religiItems: List<religionList>):
    RecyclerView.Adapter<EditReligionAdapter.EditReligion>()
{

    inner class EditReligion(view: View) : RecyclerView.ViewHolder(view) {

        var item: TextView

        init {
            item = view.findViewById<TextView>(R.id.item_modal)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditReligion {
        val view = View.inflate(parent.context, R.layout.modal_list, null)
        return EditReligion(view)
    }

    override fun onBindViewHolder(holder: EditReligion, position: Int) {
        val currentItem = religiItems[position]
        holder.item.text= currentItem.religionName
    }

    override fun getItemCount(): Int {
        return religiItems.size
    }

}