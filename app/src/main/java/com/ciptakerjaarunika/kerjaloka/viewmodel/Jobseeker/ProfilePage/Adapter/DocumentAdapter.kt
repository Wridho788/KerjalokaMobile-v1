package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.Adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.model.Data.Documents

class DocumentAdapter(private val docList: List<Documents>):
    RecyclerView.Adapter<DocumentAdapter.exp>()
{

    inner class exp(view: View) : RecyclerView.ViewHolder(view) {

        var documentName: TextView?
        var downloadButton: ImageView?

        init {
            documentName = view.findViewById(R.id.documentName)
            downloadButton = view.findViewById(R.id.btn_download)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): exp {
        val view = View.inflate(parent.context, R.layout.attachment_card, null)
        return exp(view)
    }

    override fun onBindViewHolder(holder: exp, position: Int) {
        val currentItem = docList[position]
        holder.documentName?.text= currentItem.documentName
    }

    override fun getItemCount(): Int {
        return docList.size
    }

}