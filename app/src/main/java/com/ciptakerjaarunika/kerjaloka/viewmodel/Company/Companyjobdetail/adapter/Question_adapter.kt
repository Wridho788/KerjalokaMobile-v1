package com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Companyjobdetail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.enum.QuestionType
import com.ciptakerjaarunika.kerjaloka.model.Data.ShortQuestion
import com.ciptakerjaarunika.kerjaloka.model.Job.CompanyJobDetail.JobShortQuestion
import com.ciptakerjaarunika.kerjaloka.model.Job.CompanyJobDetail.JobShortQuestionChoice
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Companyjobdetail.iAddidiontalInfoPage

class ShortQuestion_adapter(
    var data: List<JobShortQuestion>,
    private val shortQuestionList: List<ShortQuestion>,
    val iAddidiontalInfoPage: iAddidiontalInfoPage
) : RecyclerView.Adapter<ShortQuestion_adapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var questionType: TextView
        var question: TextView
        var container: LinearLayout

        init {
            questionType = view.findViewById(R.id.question_type)
            question = view.findViewById(R.id.question)
            container = view.findViewById(R.id.container)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.section_company_add_jobs_5, null)
        val lp = RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        view.layoutParams = lp
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = shortQuestionList[position]

        var questionType =
            QuestionType.values().find { type -> type.value == currentItem.questionType }
        if (questionType != null) {
            holder.questionType.text = questionType.name
        }
        holder.question.text = currentItem.shortQuestion
        var currentData = data.find { test -> test.shortQuestionNo == currentItem.shortQuestionNo }
        if (currentData == null) {
            holder.container.setBackgroundResource(R.drawable.card_background_500)
        } else {
            holder.container.setBackgroundResource(R.drawable.card_background_selected)
        }

        holder.container.setOnClickListener {
            if (currentData != null) {
                holder.container.setBackgroundResource(R.drawable.card_background_500)
                data = data.toMutableList().apply {
                    remove(currentData)
                }
            } else {
                holder.container.setBackgroundResource(R.drawable.card_background_selected)
                data += JobShortQuestion(currentItem.shortQuestionNo, currentItem.shortQuestion,
                    currentItem.questionType, null, currentItem.choice.map { it ->
                        JobShortQuestionChoice(null, it.choice, it.isAnswer)
                    })
            }
            iAddidiontalInfoPage.updateJobShortQuestion(data)
        }
    }

    override fun getItemCount(): Int {
        return shortQuestionList.size
    }
}
