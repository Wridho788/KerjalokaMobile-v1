package com.ciptakerjaarunika.kerjaloka.ui.InterviewPage

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.anychart.scales.DateTime
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.model.Interview.Messages
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.utils.DateUtils
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*


//class interview_adapter:RecyclerView.Adapter<interview_adapter.ViewHolder>() {
//
//
class ChatAdapter

    (private val context: Context, private val jobNo : Long?, private val receiver : Long) :
    RecyclerView.Adapter<ChatAdapter.ViewHolder>() {
    private val Right1 = 1
    private val Right2 = 2
    private val Left1 = 3
    private val Left2 = 4
    private val UserNo :Long? = SessionManager(context).user?.userNo

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val message: TextView
        val createdOn : TextView
        val timeContainer : LinearLayout

        init {
            message = view.findViewById(R.id.message)
            createdOn = view.findViewById(R.id.createdOn)
            timeContainer = view.findViewById(R.id.timeContainer)
        }
    }

    // determine which layout to use for the row
    override fun getItemViewType(position: Int): Int {
        val currentSection = SessionManager(context).chatData!!.sections?.find {
            it.jobNo == jobNo &&
                    it.receiver.contains(receiver)
        }

            var dataSet = currentSection!!.messages

            val sender : Long = dataSet[position].createdBy
            return if (sender == UserNo && (position == 0 || dataSet[position-1].createdBy != UserNo)) {
                Right1
            } else if (sender == UserNo && (position == 0 || dataSet[position-1].createdBy == UserNo)) {
                Right2
            } else if (sender != UserNo && (position == 0 || dataSet[position-1].createdBy != sender)) {
                Left1
            } else{
                Left2
            }

    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item

        var view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.message_right1, viewGroup, false)
        if(viewType == Right1){
            view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.message_right1, viewGroup, false)
        }
        else if(viewType == Right2){
            view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.message_right2, viewGroup, false)
        }
        else if(viewType == Left1){
            view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.message_left1, viewGroup, false)
        }
        else{
            view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.message_left2, viewGroup, false)
        }
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val currentSection = SessionManager(context).chatData!!.sections?.find {
            it.jobNo == jobNo &&
                    it.receiver.contains(receiver)
        }

        if(currentSection != null) {
            var dataSet = currentSection.messages
            viewHolder.message.text = dataSet!![position].message
            viewHolder.createdOn.text = DateUtils().GetTime(dataSet[position].createdOn)
//        viewHolder.createdOn.text = dataSet[position].createdOn.dateToString("HH:mm")

            val sender : Long? = SessionManager(context).user?.userNo

            if (dataSet.size -1 == position) {
                viewHolder.timeContainer.visibility= VISIBLE
            }
            else{
                var temp = dataSet[position].createdOn.split("T")
                var time = temp[1].split(":")
                val time1 = "${temp[0]} ${time[0]}:${time[1]}"

                temp = dataSet[position+1].createdOn.split("T")
                time = temp[1].split(":")
                val time2 = "${temp[0]} ${time[0]}:${time[1]}"

                if(dataSet[position+1].createdBy != sender || time1 != time2 ) {
                    viewHolder.timeContainer.visibility= VISIBLE
                } else{
                    viewHolder.timeContainer.visibility= GONE
                }
            }

        }
    }

    private fun DateTime.dateToString(format: String): String {
        val dateFormatter = SimpleDateFormat(format, Locale.getDefault())
        return dateFormatter.format(this)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() : Int{

      val currentSection = SessionManager(context).chatData!!.sections?.find {
          it.jobNo == jobNo &&
                  it.receiver.contains(receiver)
        }
        if(currentSection != null){
          return currentSection.messages.size
        }
        else{
             return 0
        }
    }

}