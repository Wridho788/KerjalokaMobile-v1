package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.InterviewPage.Jobseeker

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
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.InterviewPage.CellClickListener
import com.ciptakerjaarunika.kerjaloka.utils.DateUtils

//class interview_adapter:RecyclerView.Adapter<interview_adapter.ViewHolder>() {
//
//
class jobseeker_interview_adapter
    (
        private val dataList: List<jobseeker_interview_list>,
        private val context: Context?,
        private val cellClickListener: CellClickListener
    ) :
    RecyclerView.Adapter<jobseeker_interview_adapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userPhoto: ImageView
        val sectionName: TextView
        val logo : ImageView
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
            logo = view.findViewById(R.id.userPhoto)
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
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.sectionName.text = "${dataList[position].jobPosition} - ${dataList[position].companyName}"

        val chatData = SessionManager(context).chatData
        Glide.with(viewHolder.itemView.context)
            .load(config().portAddress + "/photo/Profile/" + dataList[position].photo)
            .fitCenter()
            .into(viewHolder.logo)

        if(chatData != null) {

            var currentSection = if (chatData.sections != null) chatData.sections!!.find {
                it.jobNo == dataList[position].jobNo
            }
            else null;

            if (currentSection != null) {


                viewHolder.notRead.text = currentSection.notRead.toString()
                viewHolder.notRead.visibility =
                    if (currentSection.notRead != 0) View.VISIBLE else View.GONE

                viewHolder.lastMessageOn.text =
                    DateUtils().GetLastMessageOn(
                        currentSection.messages?.last()?.createdOn ?: ""
                    )

                viewHolder.lastMessage.text =
                    currentSection.messages.last().message

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
            }
            else{
                viewHolder.itemView.setOnClickListener {
                    cellClickListener.goToChatPage(
                        dataList[position].companyName,
                       null ,
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
    //val count = if(SessionManager(context).chatData == null) 0 else SessionManager(context).chatData?.sections!!.size
    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataList.size

}