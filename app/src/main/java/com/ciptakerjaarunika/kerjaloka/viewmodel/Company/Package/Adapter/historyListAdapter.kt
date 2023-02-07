package com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Package.Adapter

import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Package.pckHistory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: History, position: Int) {
        val currentItem = HistoryList[position]
        val dateAction = LocalDateTime.parse(currentItem.actionOn)
        val formattedDate = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm")
        val result = formattedDate.format(dateAction)
        holder.actionOn.text= result


        holder.Desc.text=currentItem.userPackageLogDescription
    }

    override fun getItemCount(): Int {
        return  HistoryList.size
    }
}