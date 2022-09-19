package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.scale

class ChooseScaleAdapter(private val scaleItems: List<scale>):
    RecyclerView.Adapter<ChooseScaleAdapter.chooseScale>()
{

    inner class chooseScale(view: View) : RecyclerView.ViewHolder(view) {

        var item: TextView

        init {
            item = view.findViewById<TextView>(R.id.item_modal)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): chooseScale {
        val view = View.inflate(parent.context, R.layout.modal_list, null)
        return chooseScale(view)
    }

    override fun onBindViewHolder(holder: chooseScale, position: Int) {
        val currentItem = scaleItems[position]
        holder.item.text= currentItem.scaleName
    }

    override fun getItemCount(): Int {
        return scaleItems.size
    }

}
