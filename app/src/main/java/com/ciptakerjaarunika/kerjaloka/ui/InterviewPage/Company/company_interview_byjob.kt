package com.ciptakerjaarunika.kerjaloka.ui.InterviewPage.Company

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.model.Interview.chat_data
import com.ciptakerjaarunika.kerjaloka.model.Interview.chat_model
import com.ciptakerjaarunika.kerjaloka.model.Interview.company_interview_list
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.InterviewPage.CellClickListener
import com.microsoft.signalr.HubConnection
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

//class interview_adapter:RecyclerView.Adapter<interview_adapter.ViewHolder>() {
//
//
class company_interview_byjob

    (private val dataSet: company_interview_list,
     private val cellClickListener: CellClickListener,
     private val hubConnection: HubConnection,
     private val jobNo : Long?,
     private val chatData: chat_data?,
) :
    RecyclerView.Adapter<company_interview_byjob.ViewHolder>() {

    private lateinit var mListner : onItemClickListner
    interface onItemClickListner{
        fun onItemClick(position : Int)
    }

    fun setOnItemClickListner(listner : onItemClickListner){
        mListner = listner
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userPhoto: ImageView
        val sectionName: TextView
        val lastMessage: TextView
        val lastMessageOn : TextView
        val notRead : TextView

        init {
            // Define click listener for the ViewHolder's View.
            userPhoto = view.findViewById(R.id.userPhoto)
            sectionName = view.findViewById(R.id.sectionName)
            lastMessage = view.findViewById(R.id.lastMessage)
            lastMessageOn = view.findViewById(R.id.lastMessageOn)
            notRead = view.findViewById(R.id.not_read)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.message_section, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.sectionName.text = dataSet.interviewer[position].jobseekerName
        viewHolder.notRead.text = ""
        viewHolder.lastMessage.text = ""
        viewHolder.lastMessageOn.text = ""

        val receiver = listOf<Long>(dataSet.interviewer[position].userNo)
        if(chatData!= null) {
            val sectionNo = chatData.sections?.find {
                it.jobNo == jobNo &&
                        it.receiver.contains(dataSet.interviewer[position].userNo) &&
                        it.sectionName == dataSet.interviewer[position].jobseekerName
            }

            viewHolder.itemView.setOnClickListener {
                cellClickListener.goToChatPage(
                    dataSet.interviewer[position].jobseekerName,
                    sectionNo?.sectionNo, hubConnection, jobNo, receiver
                )
            }
        }
        else{
            viewHolder.itemView.setOnClickListener {
                cellClickListener.goToChatPage(
                    dataSet.interviewer[position].jobseekerName,
                    null, hubConnection, jobNo, receiver
                )
            }
        }
    }
    public fun LocalDateTime.dateToString(format: String): String {
        val dateFormatter = SimpleDateFormat(format, Locale.getDefault())
        return dateFormatter.format(this)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.interviewer.size

}