package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.InterviewPage.Company

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.model.Interview.company_interview_list
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.utils.DateUtils
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.InterviewPage.CellClickListener

class company_interview_adapter(
    private val dataSet: List<company_interview_list>,
    private val cellClickListener: CellClickListener,
    val context: Context
) : RecyclerView.Adapter<company_interview_adapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val jobName: TextView
        val applicantCount: TextView
        val lastMessageOn: TextView
        val notRead: TextView

        init {
            jobName = view.findViewById(R.id.sectionName)
            applicantCount = view.findViewById(R.id.applicantCount)
            lastMessageOn = view.findViewById(R.id.lastMessageOn)
            notRead = view.findViewById(R.id.not_read)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.company_message_section, viewGroup, false)

        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.jobName.text = dataSet[position].jobPosition
        viewHolder.applicantCount.text = dataSet[position].interviewer.count().toString()

        val chatData = SessionManager(context).chatData
        if (chatData != null) {

            var currentSection = if (chatData.sections != null) chatData.sections.find {
                it.jobNo == dataSet[position].jobNo
            }
            else null

            if (currentSection != null) {
                viewHolder.notRead.text = currentSection.notRead.toString()
                viewHolder.notRead.visibility = if (currentSection.notRead != 0) VISIBLE else GONE

                viewHolder.lastMessageOn.text =
                    DateUtils().GetLastMessageOn(currentSection.messages.last().createdOn)
                viewHolder.lastMessageOn.visibility = VISIBLE
            }
        }
        viewHolder.itemView.setOnClickListener {
            cellClickListener.companyInterviewClick(dataSet[position], dataSet[position].jobNo)
        }
    }

    override fun getItemCount() = dataSet.size

}