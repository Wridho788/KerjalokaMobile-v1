package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.*

class ChooseYearAdapter(private val yearList: List<Int>):
    RecyclerView.Adapter<ChooseYearAdapter.chooseYr>()
{

    inner class chooseYr(view: View) : RecyclerView.ViewHolder(view) {

        var item: TextView

        init {
            item = view.findViewById<TextView>(R.id.item_modal)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): chooseYr {
        val view = View.inflate(parent.context, R.layout.modal_list, null)
        return chooseYr(view)
    }

    override fun onBindViewHolder(holder: chooseYr, position: Int) {
        holder.item.text = yearList[position].toString()
    }

    override fun getItemCount(): Int {
        return yearList.size
    }

}