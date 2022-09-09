package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.*

class ChooseTitleAdapter(private val titleList: List<title>):
    RecyclerView.Adapter<ChooseTitleAdapter.chooseTitle>()
{

    inner class chooseTitle(view: View) : RecyclerView.ViewHolder(view) {

        var item: TextView

        init {
            item = view.findViewById<TextView>(R.id.item_modal)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): chooseTitle {
        val view = View.inflate(parent.context, R.layout.modal_list, null)
        return chooseTitle(view)
    }

    override fun onBindViewHolder(holder: chooseTitle, position: Int) {
        val currentItem = titleList[position]
        holder.item.text= currentItem.Title
    }

    override fun getItemCount(): Int {
        return titleList.size
    }

}