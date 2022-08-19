package com.ciptakerjaarunika.kerjaloka.ui.InterviewPage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import java.util.*

//class interview_adapter:RecyclerView.Adapter<interview_adapter.ViewHolder>() {
//
//
class interview_adapter

    (private val dataSet: List<Object>) :
    RecyclerView.Adapter<interview_adapter.ViewHolder>() {

    private lateinit var mListner : onItemClickListner
    interface onItemClickListner{
        fun onItemClick(position : Int)
    }

    fun setOnItemClickListner(listner : onItemClickListner){
        mListner = listner
    }


    class ViewHolder(view: View, listener : onItemClickListner, position: Int) : RecyclerView.ViewHolder(view) {
        val userPhoto: ImageView
        val userName: TextView
        val lastMessage: TextView
        val lastMessageOn : TextView
        val notRead : TextView

        init {
            // Define click listener for the ViewHolder's View.
            userPhoto = view.findViewById(R.id.userPhoto)
            userName = view.findViewById(R.id.userName)
            lastMessage = view.findViewById(R.id.lastMessage)
            lastMessageOn = view.findViewById(R.id.lastMessageOn)
            notRead = view.findViewById(R.id.not_read)

            view.setOnClickListener {
                listener.onItemClick(position)
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.message_section, viewGroup, false)

        return ViewHolder(view, mListner)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        // viewHolder.user.text = applicationData[position]
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}