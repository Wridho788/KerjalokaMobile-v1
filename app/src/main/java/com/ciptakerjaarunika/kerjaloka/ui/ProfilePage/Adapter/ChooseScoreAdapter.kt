package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.*

class ChooseScoreAdapter():
    RecyclerView.Adapter<ChooseScoreAdapter.chooseScore>()
{

    inner class chooseScore(view: View) : RecyclerView.ViewHolder(view) {

        var item: TextView

        init {
            item = view.findViewById<TextView>(R.id.item_modal)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): chooseScore {
        val view = View.inflate(parent.context, R.layout.modal_list, null)
        return chooseScore(view)
    }

    override fun onBindViewHolder(holder: chooseScore, position: Int) {
//        val currentItem = langList[position]
        holder.item.text= position.toString()
    }

    override fun getItemCount(): Int {
        return 10
    }

}