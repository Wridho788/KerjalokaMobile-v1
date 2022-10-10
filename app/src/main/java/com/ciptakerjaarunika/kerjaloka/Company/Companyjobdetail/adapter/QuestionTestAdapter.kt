package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.model.Data.questionList

class QuestionTestAdapter(var data : List<questionList>) :
    RecyclerView.Adapter<QuestionTestAdapter.ViewHolder?>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val index : TextView
        val question : TextView

        init {
            index = itemView.findViewById(R.id.index)
            question = itemView.findViewById(R.id.question)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.question_list, null)
        view.layoutParams = RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        holder.index.text = "${position + 1}."
        holder.question.text = item.question?.get(0)?.question
    }

    override fun getItemCount(): Int {
        return if(data!!.size >= 3) 3 else data!!.size
    }
}