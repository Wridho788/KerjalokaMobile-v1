package com.ciptakerjaarunika.kerjaloka.ui.InterviewPage.ChatPage

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.model.Chat.Messages
import java.text.SimpleDateFormat
import java.util.*


//class interview_adapter:RecyclerView.Adapter<interview_adapter.ViewHolder>() {
//
//
class chat_adapter

    (private val dataSet: List<Messages>) :
    RecyclerView.Adapter<chat_adapter.ViewHolder>() {

    private val Right1 = 1
    private val Right2 = 2
    private val Left1 = 3
    private val Left2 = 4
    private val UserNo :Long = 2022

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val message: TextView
        val createdOn : TextView

        init {
            message = view.findViewById(R.id.message)
            createdOn = view.findViewById(R.id.createdOn)
        }
    }

    // determine which layout to use for the row
    override fun getItemViewType(position: Int): Int {
        val sender : Long = dataSet[position].CreatedBy
        return if (sender == UserNo && (position == 0 || dataSet[position-1].CreatedBy != UserNo)) {
            Right1
        } else if (sender == UserNo && (position == 0 || dataSet[position-1].CreatedBy == UserNo)) {
            Right2
        } else if (sender != UserNo && (position == 0 || dataSet[position-1].CreatedBy != sender)) {
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
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.message.text = dataSet[position].Message
        viewHolder.createdOn.text = dataSet[position].CreatedOn.dateToString("HH:mm")

        val sender : Long = dataSet[position].CreatedBy

        if (dataSet.size -1 == position || dataSet[position+1].CreatedBy != sender || dataSet[position+1].CreatedOn != dataSet[position].CreatedOn) {
            viewHolder.createdOn.visibility= VISIBLE
        } else{
            viewHolder.createdOn.visibility= GONE
        }
    }

    private fun Date.dateToString(format: String): String {
        val dateFormatter = SimpleDateFormat(format, Locale.getDefault())
        return dateFormatter.format(this)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}