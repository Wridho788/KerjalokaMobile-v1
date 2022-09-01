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
class Header_Message_Adapter(val sectionNo: Int?, val context: Context?): RecyclerView.Adapter<Header_Message_Adapter.ViewHolder>() {

    private var currentSection = SessionManager(context).chatData!!.sections?.find {
        it.sectionNo == sectionNo
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val header: TextView

        init {
            header = view.findViewById(R.id.txt_header)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.header_message, viewGroup, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
          viewHolder.header.text = DateUtils().GetHeaderMessage(currentSection!!.messages[position].createdOn)
    }

    private fun DateTime.dateToString(format: String): String {
        val dateFormatter = SimpleDateFormat(format, Locale.getDefault())
        return dateFormatter.format(this)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        if (currentSection != null) {
            return currentSection!!.messages.size
        } else return 0
    }
}