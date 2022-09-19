package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import minat_model


class MinatAdapter(private val minatList: List<minat_model>):
    RecyclerView.Adapter<MinatAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var preference: TextView

        init {
            preference = itemView.findViewById(R.id.prefName)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.interest_section, null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = minatList[position]
        holder.preference.text = currentItem.Preference
    }

    override fun getItemCount(): Int {
        return minatList?.size ?:0
    }
}