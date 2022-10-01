package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.Bottomsheet.Adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.Bottomsheet.iChooseTypeJob
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.iUpdatePage1
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.model.Data.JobTypeFilter

class TypeJobAdapter(private var dataset: List<JobTypeFilter>?, val iChooseTypeJob: iChooseTypeJob, val iUpdatePage1: iUpdatePage1): RecyclerView.Adapter<TypeJobAdapter.ViewHolder?>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val txtJobType: TextView
        init {
            txtJobType = itemView.findViewById(R.id.txt_location)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.item_location_job,null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataset!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataset!![position]
        holder.txtJobType.text = item.jobTypeName
        val type = item.jobTypeName
        val typeNo = item.jobTypeNo
        holder.txtJobType.setOnClickListener {
            iUpdatePage1.updatePageType(typeNo,type)
            iChooseTypeJob.close() }
    }

}
