package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.InterviewPage.Company

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
import com.ciptakerjaarunika.kerjaloka.utils.DateUtils
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class company_interview_byjob_adapter
    (
    private val dataSet: company_interview_list,
    private val cellClickListener: CellClickListener,
    private val jobNo: Long?,
    private val context: Context,
    private val jobPosition: String?,
) : RecyclerView.Adapter<company_interview_byjob_adapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userPhoto: ImageView
        val sectionName: TextView
        val lastMessage: TextView
        val lastMessageOn: TextView
        val notRead: TextView
        val logo: ImageView

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
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.message_section, viewGroup, false)

        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.sectionName.text = dataSet.interviewer[position].jobseekerName
        Glide.with(viewHolder.itemView.context)
            .load(config().portAddress + "/photo/Profile/" + dataSet.interviewer[position].photo)
            .fitCenter().into(viewHolder.logo)

        viewHolder.lastMessage.text = ""
        viewHolder.lastMessageOn.text = ""

        val chatData = SessionManager(context).chatData
        if (chatData != null) {
            val currentSection = chatData.sections.find {
                it.jobNo == jobNo && it.receiver.contains(dataSet.interviewer[position].userNo)
            }

            if (currentSection != null) {
                viewHolder.notRead.text = currentSection.notRead.toString()
                viewHolder.notRead.visibility =
                    if (currentSection.notRead != 0) View.VISIBLE else View.GONE

                viewHolder.lastMessageOn.text =
                    DateUtils().GetLastMessageOn(currentSection.messages.last().createdOn)

                viewHolder.lastMessage.text = currentSection.messages.last().message

                viewHolder.itemView.setOnClickListener {
                    cellClickListener.goToChatPage(
                        dataSet.interviewer[position].jobseekerName,
                        currentSection.sectionNo,
                        jobNo,
                        dataSet.interviewer[position].userNo,
                        dataSet.interviewer[position].photo,
                        jobPosition
                    )
                }
            } else {
                viewHolder.itemView.setOnClickListener {
                    cellClickListener.goToChatPage(
                        dataSet.interviewer[position].jobseekerName,
                        null,
                        jobNo,
                        dataSet.interviewer[position].userNo,
                        dataSet.interviewer[position].photo,
                        jobPosition
                    )
                }
            }
        } else {
            viewHolder.itemView.setOnClickListener {
                cellClickListener.goToChatPage(
                    dataSet.interviewer[position].jobseekerName,
                    null,
                    jobNo,
                    dataSet.interviewer[position].userNo,
                    dataSet.interviewer[position].photo,
                    jobPosition
                )
            }
        }
    }

    fun LocalDateTime.dateToString(format: String): String {
        val dateFormatter = SimpleDateFormat(format, Locale.getDefault())
        return dateFormatter.format(this)
    }

    override fun getItemCount() = dataSet.interviewer.size

}