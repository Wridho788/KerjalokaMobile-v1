package com.ciptakerjaarunika.kerjaloka.Company.Package.Adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.Company.Package.pack
import com.ciptakerjaarunika.kerjaloka.Company.Package.pckHistory
import com.ciptakerjaarunika.kerjaloka.R

class historyListAdapter (private val HistoryList: List<pckHistory>):
    RecyclerView.Adapter<historyListAdapter.History>()
{
        inner class History(view: View) : RecyclerView.ViewHolder(view){
            var actionOn: TextView
            var Desc: TextView

            init {
                actionOn = view.findViewById(R.id.actionOn)
                Desc = view.findViewById(R.id.action)
            }

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): History {
        val view = View.inflate(parent.context, R.layout.package_history_list, null)
        return History(view)
    }

    override fun onBindViewHolder(holder: History, position: Int) {
        val currentItem = HistoryList[position]
        holder.actionOn.text= currentItem.actionOn
        holder.Desc.text=currentItem.userPackageLogDescription
    }

    override fun getItemCount(): Int {
        return  HistoryList.size
    }
}