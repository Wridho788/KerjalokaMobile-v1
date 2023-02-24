package com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Test.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Test.Question

class QuestionTestAdapter(private val testList: List<Question>) :
    RecyclerView.Adapter<QuestionTestAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val question: TextView
        init {
            question = view.findViewById(R.id.quesName)

        }
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.card_question, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = testList[position]
        holder.question.text = data.question
    }

    override fun getItemCount(): Int {
        return testList.size
    }
}