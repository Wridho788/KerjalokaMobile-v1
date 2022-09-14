package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.model.Data.Skill

class ChooseSkillAdapter(private val skilItems: List<Skill>):
    RecyclerView.Adapter<ChooseSkillAdapter.chooseSkil>()
{

    inner class chooseSkil(view: View) : RecyclerView.ViewHolder(view) {

        var item: TextView
        init {
            item = view.findViewById<TextView>(R.id.item_modal)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): chooseSkil {
        val view = View.inflate(parent.context, R.layout.modal_list, null)
        return chooseSkil(view)
    }

    override fun onBindViewHolder(holder: chooseSkil, position: Int) {
        val currentItem = skilItems[position]
        holder.item.text= currentItem.skillName
    }

    override fun getItemCount(): Int {
        return skilItems.size
    }

}