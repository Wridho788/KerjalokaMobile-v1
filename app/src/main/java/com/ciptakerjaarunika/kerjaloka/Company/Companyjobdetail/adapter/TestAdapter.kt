package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.adapter

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.iChooseTest
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.model.Data.TestJob
import com.ciptakerjaarunika.kerjaloka.model.Data.questionList
import com.google.android.material.card.MaterialCardView

class TestAdapter(private var dataset: List<TestJob>?, val iChooseTest: iChooseTest) :
    RecyclerView.Adapter<TestAdapter.ViewHolder?>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardTest: MaterialCardView
        val titleTest: TextView
        val itemTest: TextView

        init {
            cardTest = itemView.findViewById(R.id.compny_tes_page)
            titleTest = itemView.findViewById(R.id.title_test)
            itemTest = itemView.findViewById(R.id.test_item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.section_company_add_jobs_4, null)
        val lp = RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        view.layoutParams = lp
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataset!![position]

        holder.titleTest.text = item.testName
        val questionList = item.question.map { question -> questionList(question = question.question) }
        questionList.map { question -> holder.itemTest.text = question.question[0].question}
        holder.cardTest.setOnClickListener {
           holder.cardTest.setStrokeColor(R.color.danger_500)
            holder.cardTest.strokeWidth(3)
            iChooseTest.updateTest(listOf(item))
        }

    }

    override fun getItemCount(): Int {
        return dataset!!.size
    }
}

private fun MaterialCardView.strokeWidth(i: Int) {
}
