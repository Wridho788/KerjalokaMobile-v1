package com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Companyjobdetail.adapter

import android.text.Html
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.model.Data.TestJob
import com.ciptakerjaarunika.kerjaloka.model.Job.CompanyJobDetail.JobTest
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Companyjobdetail.iAddidiontalInfoPage

class TestAdapter(
    var data: List<JobTest>,
    private var dataset: List<TestJob>?,
    val iAddidiontalInfoPage: iAddidiontalInfoPage
) :
    RecyclerView.Adapter<TestAdapter.ViewHolder?>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val container: LinearLayout
        val titleTest: TextView
        val questionList: TextView

        init {
            container = itemView.findViewById(R.id.container)
            titleTest = itemView.findViewById(R.id.testName)
            questionList = itemView.findViewById(R.id.questions)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.test_card_job, null)
        view.layoutParams = RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataset!![position]

        holder.titleTest.text = item.testName
        var question = ""
        item.question.forEachIndexed { index, it ->
            question += "${index + 1}. ${it.question.get(0)?.question}<br/>"
        }
        holder.questionList.text = Html.fromHtml(question)

        var currentData = data.find { test -> test.testNo == item.testNo }
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
                data += JobTest(
                    null, null, null, null, null, null, null,
                    null, null, null, null, null, null, null, null, null,
                    null, item.testNo, null
                )
            }
            iAddidiontalInfoPage.updateJobTest(data)
        }

    }

    override fun getItemCount(): Int {
        return dataset!!.size
    }
}
