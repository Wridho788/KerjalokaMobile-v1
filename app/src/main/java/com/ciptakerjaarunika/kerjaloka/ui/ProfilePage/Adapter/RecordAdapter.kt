package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.record

class RecordAdapter (private val recordList: List<record>):
    RecyclerView.Adapter<RecordAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var creator: TextView
        var recordTime: TextView
        var Desc: TextView

        init {
            creator = itemView.findViewById(R.id.record_page_by)
            recordTime = itemView.findViewById(R.id.record_page_date)
            Desc = itemView.findViewById(R.id.recordDesc)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.section_my_record_page, null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = recordList[position]
        holder.creator.text = currentItem.ownerName
        holder.recordTime.text = currentItem.statusChangeOn
        holder.Desc.text = currentItem.description
    }

    override fun getItemCount(): Int {
        return recordList?.size ?:0
    }

}