package com.ciptakerjaarunika.kerjaloka.Company.Test.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.Company.Test.Test
import com.ciptakerjaarunika.kerjaloka.Company.Test.mytest_adapter
import com.ciptakerjaarunika.kerjaloka.Company.Test.view_mytest_list
import com.ciptakerjaarunika.kerjaloka.R

class QuestionTestAdapter(private val testList: List<Test>) :
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
    ): QuestionTestAdapter.ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.mytestlist_card, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = testList[position]
        holder.question.text = data.questions[0].question
    }

    override fun getItemCount(): Int {
        return testList.size
    }


}