package com.ciptakerjaarunika.kerjaloka.ui.InterviewPage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import java.text.SimpleDateFormat
import java.util.*

//class interview_adapter:RecyclerView.Adapter<interview_adapter.ViewHolder>() {
//
//
class interview_adapter

    (private val dataSet: List<chat_model>, private val cellClickListener: CellClickListener) :
    RecyclerView.Adapter<interview_adapter.ViewHolder>() {

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
        viewHolder.sectionName.text = dataSet[position].SectionName
        viewHolder.notRead.text = dataSet[position].NotRead.toString()
        viewHolder.lastMessage.text = dataSet[position].Messages[dataSet[position].Messages.count()-1].Message
        viewHolder.lastMessageOn.text = dataSet[position].Messages[dataSet[position].Messages.count()-1].CreatedOn.dateToString("HH:mm")

        viewHolder.itemView.setOnClickListener {
            cellClickListener.onCellClickListener(dataSet[position])
        }
    }
    public fun Date.dateToString(format: String): String {
        val dateFormatter = SimpleDateFormat(format, Locale.getDefault())
        return dateFormatter.format(this)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}