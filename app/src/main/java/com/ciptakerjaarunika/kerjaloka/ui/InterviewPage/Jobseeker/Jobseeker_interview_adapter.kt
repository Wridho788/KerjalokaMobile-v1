package com.ciptakerjaarunika.kerjaloka.ui.InterviewPage.Jobseeker

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.anychart.scales.DateTime
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.model.Interview.chat_data
import com.ciptakerjaarunika.kerjaloka.ui.InterviewPage.CellClickListener
import com.microsoft.signalr.HubConnection
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

//class interview_adapter:RecyclerView.Adapter<interview_adapter.ViewHolder>() {
//
//
class jobseeker_interview_adapter

    (
        private val dataSet: chat_data?,
        private val cellClickListener: CellClickListener,
        val hubConnection: HubConnection,
) :
    RecyclerView.Adapter<jobseeker_interview_adapter.ViewHolder>() {


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
        if (dataSet != null) {
            viewHolder.sectionName.text = dataSet.sections[position].sectionName
            viewHolder.notRead.text = dataSet.sections[position].notRead.toString()
            viewHolder.lastMessage.text =
                dataSet.sections[position].messages[dataSet.sections[position].messages.count() - 1].message
            viewHolder.lastMessageOn.text =
                dataSet.sections[position].messages.last().createdOn
//                dataSet.sections[position].messages.last().createdOn.dateToString("HH:mm")

//        if(chat_data != null) {
//            viewHolder.itemView.setOnClickListener {
//                cellClickListener.goToChatPage(
//                    dataSet.sections[position].sectionName,
//                    dataSet.sections[position].sectionNo,
//                    hubConnection,
//                    null,
//                    dataSet.sections[position].receiver
//                )
//            }
//        }
//        else{
//            viewHolder.itemView.setOnClickListener {

            cellClickListener.goToChatPage(
                dataSet.sections[position].sectionName,
                dataSet.sections[position].sectionNo,
                hubConnection,
                dataSet.sections[position].jobNo,
                dataSet.sections[position].receiver[0]
            )
//            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
//    public fun String.ChatTimeFormat(): String {
//        var dateValue = LocalDateTime.parse(this)
//        Log.d("Month :", (Calendar.getInstance().time - dateValue).month.toString())
//
//        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
//        var Date = this.split("T")[0]
//        var Time = this.split("T")[0]
////        val formatter = SimpleDateFormat(format)
////        return formatter.format(parser.parse(this))
////        val dateFormatter = SimpleDateFormat(format, Locale.getDefault())
//        return dateFormatter.format(this)
//    }
    val count = if(dataSet == null) 0 else dataSet.sections.size
    // Return the size of your dataset (invoked by the layout manager)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun getItemCount() = count

}