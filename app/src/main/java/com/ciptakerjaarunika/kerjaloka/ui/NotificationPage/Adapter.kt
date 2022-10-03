package com.ciptakerjaarunika.kerjaloka.ui.NotificationPage

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.NotificationPage.Model.CompanyNotificationModel
import com.ciptakerjaarunika.kerjaloka.ui.NotificationPage.Model.NotifModel
import com.ciptakerjaarunika.kerjaloka.ui.NotificationPage.item.itemViewHolderCompany

class NotifAdapter (private val onLoadMore:()-> Unit): RecyclerView.Adapter<itemViewHolderCompany>() {

    val list = mutableListOf<CompanyNotificationModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itemViewHolderCompany {
        return itemViewHolderCompany(LayoutInflater.from(parent.context).inflate(R.layout.notif_card, parent, false))
    }

    override fun onBindViewHolder(holder: itemViewHolderCompany, position: Int) {
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
    fun reload(list: MutableList<CompanyNotificationModel>){
        this.list.clear()
        val addAll: Boolean = this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun loadMore(list: MutableList<CompanyNotificationModel>){
        this.list.addAll(list)
        //notifyItemRangeChanged(this.list.size - list.size + 1, list.size)
    }
}
