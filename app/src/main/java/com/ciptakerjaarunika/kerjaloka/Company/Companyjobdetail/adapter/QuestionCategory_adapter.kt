package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.iUpdatePage5
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.enum.QuestionType
import com.ciptakerjaarunika.kerjaloka.model.Data.ShortQuestion
import com.google.android.material.card.MaterialCardView

class QuestionCategory_adapter(private val shortQuestionList: List<ShortQuestion>,val iUpdatePage5: iUpdatePage5, val shortQuestionCategoryNo: Long) : RecyclerView.Adapter<QuestionCategory_adapter.ViewHolder>() {
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        var questionType: TextView
        var question: TextView
        var cardShortQuestion: MaterialCardView
        init {
            questionType = view.findViewById(R.id.question_type)
            question = view.findViewById(R.id.question)
            cardShortQuestion = view.findViewById(R.id.compny_tes_page)
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
        if (shortQuestionCategoryNo == currentItem.shortQuestionCategoryNo) {
            if (currentItem.questionType == QuestionType.SubQuestion.value) {
                holder.questionType.text = QuestionType.SubQuestion.name
            } else if (currentItem.questionType == QuestionType.MultipleChoice.value) {
                holder.questionType.text = QuestionType.MultipleChoice.name
            } else if (currentItem.questionType == QuestionType.MultipleAnswer.value) {
                holder.questionType.text = QuestionType.MultipleAnswer.name
            } else if (currentItem.questionType == QuestionType.Essay.value) {
                holder.questionType.text = QuestionType.Essay.name
            } else if (currentItem.questionType == QuestionType.Programming.value) {
                holder.questionType.text = QuestionType.Programming.name
            } else if (currentItem.questionType == QuestionType.Attachment.value) {
                holder.questionType.text = QuestionType.Attachment.name
            } else if (currentItem.questionType == QuestionType.PapiKostick.value) {
                holder.questionType.text = QuestionType.PapiKostick.name
            }
            holder.question.text = currentItem.shortQuestion
            holder.cardShortQuestion.setOnClickListener {
                holder.cardShortQuestion.strokeColor = R.color.danger_500
                holder.cardShortQuestion.strokeWidth(3)
                iUpdatePage5.updateShortQuestion(listOf(currentItem))
            }
        }


    }

    override fun getItemCount(): Int {
        return shortQuestionList.filter { it -> shortQuestionCategoryNo == it.shortQuestionCategoryNo}.size
    }
}

private fun MaterialCardView.strokeWidth(i: Int) {
}
