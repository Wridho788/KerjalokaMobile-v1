package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.MainActivity
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.enum.Scale

class ChooseScaleAdapter():
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
        val currentItem = Scale.values()
        holder.item.text= currentItem[position].name
    }

    override fun getItemCount(): Int {
        return Scale.values().size
    }

}