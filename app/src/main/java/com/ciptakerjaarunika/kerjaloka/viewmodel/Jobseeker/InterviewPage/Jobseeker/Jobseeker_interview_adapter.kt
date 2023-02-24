package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.InterviewPage.Jobseeker

import android.annotation.SuppressLint
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
import com.ciptakerjaarunika.kerjaloka.model.Interview.jobseeker_interview_list
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.utils.DateUtils
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.InterviewPage.CellClickListener

class jobseeker_interview_adapter
    (
    private val dataList: List<jobseeker_interview_list>,
    private val context: Context?,
    private val cellClickListener: CellClickListener
) : RecyclerView.Adapter<jobseeker_interview_adapter.ViewHolder>() {

    @SuppressLint("CutPasteId")
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userPhoto: ImageView
        val sectionName: TextView
        val logo: ImageView
        val lastMessage: TextView
        val lastMessageOn: TextView
        val notRead: TextView

        init {
            userPhoto = view.findViewById(R.id.userPhoto)
            sectionName = view.findViewById(R.id.sectionName)
            lastMessage = view.findViewById(R.id.lastMessage)
            lastMessageOn = view.findViewById(R.id.lastMessageOn)
            notRead = view.findViewById(R.id.not_read)
            logo = view.findViewById(R.id.userPhoto)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.message_section, viewGroup, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.sectionName.text =
            "${dataList[position].jobPosition} - ${dataList[position].companyName}"

        val chatData = SessionManager(context).chatData
        Glide.with(viewHolder.itemView.context)
            .load(config().portAddress + "photo/Profile/" + dataList[position].photo).fitCenter()
            .into(viewHolder.logo)

        if (chatData != null) {
            var currentSection = if (chatData.sections != null) chatData.sections.find {
                it.jobNo == dataList[position].jobNo
            }
            else null
            if (currentSection != null) {
                viewHolder.notRead.text = currentSection.notRead.toString()
                viewHolder.notRead.visibility =
                    if (currentSection.notRead != 0) View.VISIBLE else View.GONE

                viewHolder.lastMessageOn.text = DateUtils().GetLastMessageOn(
                    currentSection.messages.last().createdOn
                )

                viewHolder.lastMessage.text = currentSection.messages.last().message

                viewHolder.itemView.setOnClickListener {
                    cellClickListener.goToChatPage(
                        dataList[position].companyName,
                        currentSection.sectionNo,
                        dataList[position].jobNo,
                        dataList[position].companyNo,
                        dataList[position].photo,
                        dataList[position].jobPosition
                    )
                }
            } else {
                viewHolder.itemView.setOnClickListener {
                    cellClickListener.goToChatPage(
                        dataList[position].companyName,
                        null,
                        dataList[position].jobNo,
                        dataList[position].companyNo,
                        dataList[position].photo,
                        dataList[position].jobPosition
                    )
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getItemCount() = dataList.size
}