package com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Companyjobdetail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Companyjobdetail.model.JobShortQuestion

class JobSQListAdapter(private val testList: List<JobShortQuestion>) :
    RecyclerView.Adapter<JobSQListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val question: TextView

        init {
            question = view.findViewById(R.id.itemName)
        }
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.company_test_list_job, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = testList[position]
        holder.question.text = data.shortQuestion
    }

    override fun getItemCount(): Int {
        return testList.size
    }


}