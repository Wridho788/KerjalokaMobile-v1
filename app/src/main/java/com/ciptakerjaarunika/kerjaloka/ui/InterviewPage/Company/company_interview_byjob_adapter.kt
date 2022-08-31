package com.ciptakerjaarunika.kerjaloka.ui.InterviewPage.Company

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.model.Interview.company_interview_list
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.InterviewPage.CellClickListener
import com.ciptakerjaarunika.kerjaloka.utils.DateUtils
import com.microsoft.signalr.HubConnection
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

//class interview_adapter:RecyclerView.Adapter<interview_adapter.ViewHolder>() {
//
//
class company_interview_byjob_adapter

    (private val dataSet: company_interview_list,
     private val cellClickListener: com.ciptakerjaarunika.kerjaloka.ui.InterviewPage.Company.CellClickListener,
     private val hubConnection: HubConnection,
     private val jobNo : Long?,
     private val context : Context,
) :
    RecyclerView.Adapter<company_interview_byjob_adapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userPhoto: ImageView
        val sectionName: TextView
        val lastMessage: TextView
        val lastMessageOn : TextView
        val notRead : TextView
        val logo : ImageView

        init {
            // Define click listener for the ViewHolder's View.
            userPhoto = view.findViewById(R.id.userPhoto)
            sectionName = view.findViewById(R.id.sectionName)
            lastMessage = view.findViewById(R.id.lastMessage)
            lastMessageOn = view.findViewById(R.id.lastMessageOn)
            notRead = view.findViewById(R.id.not_read)
            logo = view.findViewById(R.id.userPhoto)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.message_section, viewGroup, false)

        SessionManager(context).refreshChat(hubConnection);

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.sectionName.text = dataSet.interviewer[position].jobseekerName
        Glide.with(viewHolder.itemView.context)
            .load(config().portAddress + "/photo/Profile/" + dataSet.interviewer[position].photo).fitCenter()
            .into(viewHolder.logo)

        viewHolder.lastMessage.text = ""
        viewHolder.lastMessageOn.text = ""

        val chatData = SessionManager(context).chatData
        if(chatData!= null) {
            val currentSection = chatData.sections?.find {
                it.jobNo == jobNo &&
                        it.receiver.contains(dataSet.interviewer[position].userNo)
            }

            if(currentSection != null) {
                viewHolder.notRead.text = currentSection.notRead.toString()
                viewHolder.notRead.visibility = if(currentSection.notRead != 0) View.VISIBLE else View.GONE

                viewHolder.lastMessageOn.text =
                    DateUtils().GetLastMessageOn(currentSection.messages?.last()?.createdOn?: "")

                viewHolder.lastMessage.text =
                    currentSection.messages?.last()?.message

                viewHolder.itemView.setOnClickListener {
                    cellClickListener.goToChatPage(
                        dataSet.interviewer[position].jobseekerName,
                        currentSection.sectionNo, hubConnection, jobNo, dataSet.interviewer[position].userNo,
                        currentSection.logo
                    )
                }
            }
            else{
                viewHolder.itemView.setOnClickListener {
                    cellClickListener.goToChatPage(
                        dataSet.interviewer[position].jobseekerName,
                        null, hubConnection, jobNo, dataSet.interviewer[position].userNo,
                        dataSet.interviewer[position].photo
                    )
                }
            }
        }
        else{
            viewHolder.itemView.setOnClickListener {
                cellClickListener.goToChatPage(
                    dataSet.interviewer[position].jobseekerName,
                    null, hubConnection, jobNo, dataSet.interviewer[position].userNo,
                    dataSet.interviewer[position].photo
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