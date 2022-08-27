package com.ciptakerjaarunika.kerjaloka.ui.InterviewPage

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.model.Interview.chat_model
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.microsoft.signalr.HubConnection


class ChatPage(var sectionName: String,var sectionNo : Int?, val hubConnection: HubConnection, val jobNo : Long?, val Receiver : List<Long>) : Fragment() {

    private var chatModel : chat_model? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        val titlePage = itemView.findViewById<TextView>(R.id.title)
        val backButton = itemView.findViewById<ImageButton>(R.id.backButton)
        backButton.setOnClickListener{
            parentFragmentManager.popBackStack()
        }
        titlePage.text = sectionName
        if(sectionNo != null){
            //search Section Message And Filter Required Dta
        }


        var LinearLayoutManager = LinearLayoutManager(activity)
        val recyclerView = itemView.findViewById<RecyclerView>(R.id.recyclerView) as RecyclerView;
        //recyclerView.scrollToPosition(section.Messages.size-1)
        recyclerView.apply {
            layoutManager = LinearLayoutManager
            adapter = ChatAdapter(null)
        }

        var message = itemView.findViewById<EditText>(R.id.txt_message);
        var img_btnsend = itemView.findViewById<ImageView>(R.id.img_btnsend);
        var btn_send = itemView.findViewById<CardView>(R.id.btn_send);

        message.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(s: Editable) {
                if(!message.text.toString().isNullOrEmpty() && !message.text.toString().isNullOrBlank()){
                    img_btnsend.setImageResource(R.drawable.icon_send);
                    img_btnsend.rotation=-25f
                    btn_send.setOnClickListener{

                        val sender = SessionManager(context).user!!.userNo.toString()
                        val message = itemView.findViewById<EditText>(R.id.txt_message).text.toString()

                        Log.d("SectionNo", sectionNo.toString())
                        Log.d("Sender", sender.toString())
                        Log.d("Message", message.toString())
                        Log.d("Receiver", Receiver.toString())
                        Log.d("JobNo", jobNo.toString())
                        Log.d("Hub Connection", hubConnection.toString())
                        Log.d("Connection Status", hubConnection.connectionState.toString())
                        val receiver = Receiver;

                        hubConnection.send("SendMessage", sectionNo, sender, message, receiver, jobNo)
                        hubConnection.send("sendMessage", sectionNo, sender, message, receiver, jobNo)
                    }
                }
                else{
                    img_btnsend.setImageResource(R.drawable.ic_attach_file);
                    img_btnsend.rotation=45f
                }
            }
        })
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.chat_page, container, false)
    }
}