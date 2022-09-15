package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.model.Data.Language

class ChooseLanguageAdapter(private val langList: List<Language>):
    RecyclerView.Adapter<ChooseLanguageAdapter.chooseLang>()
{

    inner class chooseLang(view: View) : RecyclerView.ViewHolder(view) {

        var item: TextView

        init {
            item = view.findViewById<TextView>(R.id.item_modal)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): chooseLang {
        val view = View.inflate(parent.context, R.layout.modal_list, null)
        return chooseLang(view)
    }

    override fun onBindViewHolder(holder: chooseLang, position: Int) {
        val currentItem = langList[position]
        holder.item.text= currentItem.languageName
    }

    override fun getItemCount(): Int {
        return langList.size
    }

}