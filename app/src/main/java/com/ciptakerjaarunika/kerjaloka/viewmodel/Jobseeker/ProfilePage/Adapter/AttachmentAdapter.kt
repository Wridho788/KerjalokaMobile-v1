package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.Adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.model.Data.Documents
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.Attachment.iEditLampiran


class AttachmentAdapter(var dataList: List<Documents>, val iEditLampiran: iEditLampiran) :
    RecyclerView.Adapter<AttachmentAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var fielName: TextView
        var documentName: TextView
        var deleteButton: ImageView

        init {
            fielName = itemView.findViewById(R.id.fileName)
            documentName = itemView.findViewById(R.id.documentName)
            deleteButton = itemView.findViewById(R.id.delete_btn)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.item_lampiran, null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = dataList[position]
        holder.fielName.text = currentItem.documentName
        holder.documentName.text = currentItem.documentFileName
        holder.deleteButton.setOnClickListener {
            iEditLampiran.delete(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}