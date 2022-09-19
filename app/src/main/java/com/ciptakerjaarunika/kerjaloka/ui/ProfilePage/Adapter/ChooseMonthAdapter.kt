package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.month

class ChooseMonthAdapter(private val monthList: List<month>) :
    RecyclerView.Adapter<ChooseMonthAdapter.chooseMonth>() {

    inner class chooseMonth(view: View) : RecyclerView.ViewHolder(view) {

        var item: TextView

        init {
            item = view.findViewById(R.id.item_modal)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): chooseMonth {
        val view = View.inflate(parent.context, R.layout.modal_list, null)
        return chooseMonth(view)
    }

    override fun onBindViewHolder(holder: chooseMonth, position: Int) {
        val currentItem = monthList[position]
        holder.item.text = currentItem.month
    }

    override fun getItemCount(): Int {
        return monthList.size
    }

}
