package com.ciptakerjaarunika.kerjaloka.ui.InterviewPage

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.anychart.scales.DateTime
import com.bumptech.glide.Glide
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.model.Interview.MessageType
import com.ciptakerjaarunika.kerjaloka.model.Interview.Messages
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.utils.DateUtils
import java.text.SimpleDateFormat
import java.util.*


//class interview_adapter:RecyclerView.Adapter<interview_adapter.ViewHolder>() {
//
//
class ChatAdapter
    (private val context: Context, private val jobNo : Long?, private val receiver : Long, val positionOnBottom : PositionOnBottom) :
    RecyclerView.Adapter<ChatAdapter.ViewHolder>(){
    private val Right1 = 1
    private val Right2 = 2
    private val Left1 = 3
    private val Left2 = 4
    private val UserNo :Long? = SessionManager(context).user?.userNo
    private var messagesGroup : Map<String ,List<Messages>>? = null;
    private var indexHeader : List<HeaderMessages> = listOf();

    private data class HeaderMessages(val Header : String, val Index : Int)


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val message: TextView?
        val photoMessage: ImageView?
        val messageContainer: LinearLayout?
        val photoContainer: CardView?
        val createdOn: TextView?
        val timeContainer: LinearLayout?
        val header: TextView?
        val headerContainer : LinearLayout?
        val fileContainer : LinearLayout?
        val fileName : TextView?

        init {
            message = view.findViewById(R.id.message)
            createdOn = view.findViewById(R.id.createdOn)
            timeContainer = view.findViewById(R.id.timeContainer)
            header = view.findViewById(R.id.txt_header)
            headerContainer = view.findViewById(R.id.headerMessage)
            photoMessage = view.findViewById(R.id.photoMessage)
            messageContainer = view.findViewById(R.id.message_container)
            photoContainer = view.findViewById(R.id.photo_message_container)
            fileContainer = view.findViewById(R.id.file_container)
            fileName = view.findViewById(R.id.fileName)
        }
    }
    /*
    @RequiresApi(Build.VERSION_CODES.O)
    fun getHeader(){
        for(message in currentSection!!.messages){
            var header = DateUtils().GetHeaderMessage(message.createdOn)
            var exist = headerList.find { header -> header == header }
            if(exist != null) {
                headerList += header
            }
        }
    }
    */

    // determine which layout to use for the row
    override fun getItemViewType(position: Int): Int {
            val currentSection = SessionManager(context).chatData!!.sections?.find {
                it.jobNo == jobNo && it.receiver.contains(receiver)
            }
            var dataSet = currentSection!!.messages

            var founded = indexHeader?.find { head-> head.Index == position } != null
            var viewSelected = Right1
            if (dataSet[position].createdBy == UserNo && (founded || (position == 0 || dataSet[position-1].createdBy != UserNo))) {
                viewSelected = Right1
            } else if (dataSet[position].createdBy == UserNo && (position == 0 || dataSet[position-1].createdBy == UserNo)) {
                viewSelected = Right2
            } else if (dataSet[position].createdBy != UserNo && (founded || (position == 0 || dataSet[position-1].createdBy != dataSet[position].createdBy))) {
                viewSelected = Left1
            } else{
                viewSelected = Left2
            }
            return viewSelected
    }

    // Create new views (invoked by the layout manager)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val currentSection = SessionManager(context).chatData!!.sections?.find {
            it.jobNo == jobNo && it.receiver.contains(receiver)
        }
        messagesGroup = currentSection!!.messages.groupBy { item -> DateUtils().GetHeaderMessage(item.createdOn) }

        var index = 0
        if(messagesGroup != null) {
            indexHeader = listOf()
            for (header in messagesGroup!!) {
                indexHeader += (HeaderMessages(header.key, index))
                index += header.value.size
            }
        }

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
        positionOnBottom.isOnBottom(position == (itemCount -1))

        val currentSection = SessionManager(context).chatData!!.sections?.find {
            it.jobNo == jobNo && it.receiver.contains(receiver)
        }
        if(currentSection != null) {
            if(viewHolder.header != null){
                viewHolder.header.text = indexHeader?.find { head-> head.Index == position }?.Header
            }
            var dataSet = currentSection.messages

            if(indexHeader.any()){
                var founded = indexHeader.find { head-> head.Index == position } != null
                if(founded){
                    viewHolder.headerContainer?.visibility = VISIBLE
                }
                else{
                    viewHolder.headerContainer?.visibility = GONE
                }
            }
            viewHolder.message?.visibility = GONE
            viewHolder.fileContainer?.visibility = GONE
            viewHolder.photoContainer?.visibility = GONE

            if(dataSet!![position].messageType == MessageType.NormalMessage.type.toString().toInt()){
                viewHolder.message?.visibility = VISIBLE
                viewHolder.message?.text = dataSet!![position].message
                viewHolder.messageContainer?.setPadding(50,20,50,20)
                viewHolder.messageContainer?.isEnabled = false
            }
            else if(dataSet!![position].messageType == MessageType.ImageMessage.type.toString().toInt()){
                viewHolder.messageContainer?.setOnClickListener{
                    Toast.makeText(context, "Message has clicked", Toast.LENGTH_SHORT).show()
                }
                viewHolder.messageContainer?.setPadding(10,10,10,10)
                viewHolder.photoContainer?.visibility = VISIBLE
                if(viewHolder.photoMessage != null) {
                    Glide.with(context)
                        .load(config().portAddress + "/photo/Chat/" + dataSet!![position].fileName)
                        .override(650,675)
                        .into(viewHolder.photoMessage)
                }
            }
            else if(dataSet!![position].messageType == MessageType.FileMessage.type.toString().toInt()){
                viewHolder.messageContainer?.setOnClickListener{
                    Toast.makeText(context, "Downloading File...", Toast.LENGTH_SHORT).show()
                }
                viewHolder.fileContainer?.visibility = VISIBLE
                viewHolder.fileName?.text = dataSet!![position].message
            }

            viewHolder.createdOn?.text = DateUtils().GetTime(dataSet[position].createdOn)

            val sender : Long? = SessionManager(context).user?.userNo

            if (dataSet.size == position+1 ) {
                viewHolder.timeContainer?.visibility= VISIBLE
            }
            else{
                var date1 = dataSet[position].createdOn
                var date2 = dataSet[position+1].createdOn
                if(
                    (dataSet[position+1].createdBy != dataSet[position].createdBy)
                    || DateUtils().GetDiffMinute(date2, date1) >= 5 ) {
                    viewHolder.timeContainer?.visibility= VISIBLE
                } else{
                    viewHolder.timeContainer?.visibility= GONE
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
            it.jobNo == jobNo && it.receiver.contains(receiver)
        }
        if(currentSection != null){
          return currentSection.messages.size
        }
        else{
             return 0
        }
    }

}