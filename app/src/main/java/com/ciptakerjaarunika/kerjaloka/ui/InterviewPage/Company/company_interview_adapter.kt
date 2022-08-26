package com.ciptakerjaarunika.kerjaloka.ui.InterviewPage.Company

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.model.Interview.company_interview_list
import com.ciptakerjaarunika.kerjaloka.ui.InterviewPage.CellClickListener
import java.text.SimpleDateFormat
import java.util.*

//class interview_adapter:RecyclerView.Adapter<interview_adapter.ViewHolder>() {
//
//
class company_interview_adapter

    (private val dataSet: List<company_interview_list>, private val cellClickListener: CellClickListener) :
    RecyclerView.Adapter<company_interview_adapter.ViewHolder>() {

    private lateinit var mListner : onItemClickListner
    interface onItemClickListner{
        fun onItemClick(position : Int)
    }

    fun setOnItemClickListner(listner : onItemClickListner){
        mListner = listner
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val jobName: TextView
        val applicantCount: TextView
        val lastMessageOn : TextView
        val notRead : TextView

        init {
            // Define click listener for the ViewHolder's View.
            jobName = view.findViewById(R.id.sectionName)
            applicantCount = view.findViewById(R.id.applicantCount)
            lastMessageOn = view.findViewById(R.id.lastMessageOn)
            notRead = view.findViewById(R.id.not_read)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.company_message_section, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.jobName.text = dataSet[position].jobPosition
        viewHolder.notRead.text = "12"
        viewHolder.applicantCount.text = dataSet[position].interviewer.count().toString()
        viewHolder.lastMessageOn.text = "12:50"
//        viewHolder.lastMessageOn.text = dataSet[position].Messages[dataSet[position].Messages.count()-1].CreatedOn.dateToString("HH:mm")

        viewHolder.itemView.setOnClickListener {
            cellClickListener.companyInterviewClick(dataSet[position])
        }
    }
    public fun Date.dateToString(format: String): String {
        val dateFormatter = SimpleDateFormat(format, Locale.getDefault())
        return dateFormatter.format(this)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}