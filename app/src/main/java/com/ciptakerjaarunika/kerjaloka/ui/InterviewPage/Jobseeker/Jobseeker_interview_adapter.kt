package com.ciptakerjaarunika.kerjaloka.ui.InterviewPage.Jobseeker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.model.Interview.chat_data
import com.ciptakerjaarunika.kerjaloka.model.Interview.chat_model
import com.ciptakerjaarunika.kerjaloka.ui.InterviewPage.CellClickListener
import com.microsoft.signalr.HubConnection
import java.text.SimpleDateFormat
import java.util.*

//class interview_adapter:RecyclerView.Adapter<interview_adapter.ViewHolder>() {
//
//
class jobseeker_interview_adapter

    (
        private val dataSet: chat_data?,
        private val cellClickListener: CellClickListener,
        val hubConnection: HubConnection) :
    RecyclerView.Adapter<jobseeker_interview_adapter.ViewHolder>() {

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
        viewHolder.sectionName.text = dataSet.sections[position].sectionName
        viewHolder.notRead.text = dataSet.sections[position].notRead.toString()
        viewHolder.lastMessage.text = dataSet.sections[position].messages[dataSet.sections[position].messages.count()-1].message
        viewHolder.lastMessageOn.text = dataSet.sections[position].messages.last().createdOn.dateToString("HH:mm")

        if(chat_data != null) {
            viewHolder.itemView.setOnClickListener {
                cellClickListener.goToChatPage(
                    dataSet.sections[position].sectionName,
                    dataSet.sections[position].sectionNo,
                    hubConnection,
                    null,
                    dataSet.sections[position].receiver
                )
            }
        }
        else{
            viewHolder.itemView.setOnClickListener {
                cellClickListener.goToChatPage(
                    dataSet.sections[position].sectionName,
                    dataSet.sections[position].sectionNo,
                    hubConnection,
                    null,
                    dataSet.sections[position].receiver
                )
            }
        }
    }
    public fun Date.dateToString(format: String): String {
        val dateFormatter = SimpleDateFormat(format, Locale.getDefault())
        return dateFormatter.format(this)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.sections.size

}