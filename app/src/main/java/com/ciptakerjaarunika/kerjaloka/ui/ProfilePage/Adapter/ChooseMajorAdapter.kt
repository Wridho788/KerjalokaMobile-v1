package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.*

class ChooseMajorAdapter(private val majorList: List<majors>):
    RecyclerView.Adapter<ChooseMajorAdapter.chooseMajor>()
{

    inner class chooseMajor(view: View) : RecyclerView.ViewHolder(view) {

        var item: TextView

        init {
            item = view.findViewById<TextView>(R.id.item_modal)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): chooseMajor {
        val view = View.inflate(parent.context, R.layout.modal_list, null)
        return chooseMajor(view)
    }

    override fun onBindViewHolder(holder: chooseMajor, position: Int) {
        val currentItem = majorList[position]
        holder.item.text= currentItem.majorName
    }

    override fun getItemCount(): Int {
        return majorList.size
    }

}