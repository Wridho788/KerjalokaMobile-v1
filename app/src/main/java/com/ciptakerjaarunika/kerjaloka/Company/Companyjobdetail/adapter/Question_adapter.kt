package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.model.Data.ShortQuestion

class Question_adapter(private val shortQuestionList: List<ShortQuestion>) : RecyclerView.Adapter<Question_adapter.ViewHolder>() {
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        var shortQuestion: TextView
        init {
            shortQuestion = view.findViewById(R.id.question_type)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.section_company_add_jobs_5, null)
        val lp = RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        view.layoutParams = lp
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = shortQuestionList[position]
        holder.shortQuestion.text = currentItem.shortQuestion
    }

    override fun getItemCount(): Int {
        return shortQuestionList.size
    }
}