package com.ciptakerjaarunika.kerjaloka.ui.NotificationPage

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.ui.NotificationPage.item.itemViewHolder
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.NotificationPage.Model.Model

class Adapter (private val onLoadMore:()-> Unit): RecyclerView.Adapter<itemViewHolder>() {

    val list = mutableListOf<Model>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itemViewHolder {
        return itemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.notif_card, parent, false))
    }

    override fun onBindViewHolder(holder: itemViewHolder, position: Int) {
        holder.itemModel = list[position]
        holder.updateView()

        if(position == list.size - 1){
            onLoadMore()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun reload(list: MutableList<Model>){
        this.list.clear()
        val addAll: Boolean = this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun loadMore(list: MutableList<Model>){
        this.list.addAll(list)
        //notifyItemRangeChanged(this.list.size - list.size + 1, list.size)
    }
}