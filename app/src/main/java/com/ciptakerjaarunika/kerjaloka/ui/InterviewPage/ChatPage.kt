package com.ciptakerjaarunika.kerjaloka.ui.InterviewPage

import android.annotation.SuppressLint
import android.content.Context
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
import androidx.activity.addCallback
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anychart.core.resource.Logo
import com.bumptech.glide.Glide
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.InterviewAPI
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.model.Interview.chat_data
import com.ciptakerjaarunika.kerjaloka.model.Interview.chat_model
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.InterviewPage.Company.company_interview_adapter
import com.ciptakerjaarunika.kerjaloka.ui.InterviewPage.Company.company_interview_byjob
import com.microsoft.signalr.Action1
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import com.microsoft.signalr.HubConnectionState
import java.util.*


class ChatPage(var sectionName: String,
               var sectionNo : Int?,
               val jobNo : Long?,
               val Receiver : Long,
               val logo: String?)
    :  Fragment() {

    private var chatModel : chat_model? = null
    private lateinit var recyclerView : RecyclerView;
    private lateinit var hubConnection: HubConnection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        hubConnection = HubConnectionBuilder.create(config().portAddress+"/ws/chat").build()
        if(SessionManager(context).user != null && hubConnection.connectionState != HubConnectionState.CONNECTED){
            hubConnection.start()
        }
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)

        val titlePage = itemView.findViewById<TextView>(R.id.title)
        titlePage.text = sectionName
        val backButton = itemView.findViewById<ImageButton>(R.id.backButton)

        Glide.with(itemView.context)
            .load(config().portAddress + "/photo/Profile/" + logo).fitCenter()
            .into(itemView.findViewById<ImageView>(R.id.userPhoto))

        backButton.setOnClickListener{
            var user = SessionManager(context).user
            val isCompany = user != null && user.roleNo == 2

            if(isCompany) {
                val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
                InterviewAPI().CompanyGetInterviewList(context) {
                    if (it != null) {
                        val current_data = it.data.find { data -> data.jobNo == jobNo }
                        if (current_data != null) {
                            ft.replace(
                                id,
                                company_interview_byjob(current_data, jobNo),
                                "ChatFragment"
                            )
                            ft.commit()
                        }
                    }
                }
            }
            else{
                val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
                ft.replace(id,  InterviewPage(), "InterviewPage")
                ft.commit()
            }

//            parentFragmentManager.popBackStack()
        }
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            var user = SessionManager(context).user
            val isCompany = user != null && user.roleNo == 2

            if(isCompany) {
                val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
                InterviewAPI().CompanyGetInterviewList(context) {
                    if (it != null) {
                        hubConnection.stop()
                        val current_data = it.data.find { data -> data.jobNo == jobNo }
                        if (current_data != null) {
                            ft.replace(
                                id,
                                company_interview_byjob(current_data, jobNo),
                                "ChatFragment"
                            )
                            ft.commit()
                        }
                    }
                }
            }
            else{
                hubConnection.stop();
                val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
                ft.replace(id,  InterviewPage(), "InterviewPage")
                ft.commit()
            }
        }


        //recyclerView.scrollToPosition(section.Messages.size-1)
        recyclerView = itemView.findViewById<RecyclerView>(R.id.recyclerViewChat) as RecyclerView
        hubConnection.on("connected",
            { res ->
                Log.d("Websocket Response : ", res.toString())
                val userNo = SessionManager(context).user!!.userNo.toString()
                hubConnection.send("Connecting", userNo, SessionManager(context).deviceId)
                hubConnection.send("ReadSectionMessage", sectionNo.toString())
            }, String::class.java)

        hubConnection.on<chat_data>(
            "getMessage",
            Action1<chat_data> { res: chat_data ->
                SessionManager(context).chatData = res
                Log.d("Message", res.toString())
                hubConnection.send("ReadSectionMessage", sectionNo.toString())
                activity?.runOnUiThread(Runnable {
                    recyclerView.adapter?.notifyDataSetChanged()
                })
            },
            chat_data::class.java
        )

        var LinearLayoutManager = LinearLayoutManager(activity)

        recyclerView?.apply {
            layoutManager = LinearLayoutManager
            adapter = ChatAdapter(context, jobNo, Receiver)
        }

        recyclerView.scrollToPosition(1000)
        recyclerView.smoothScrollToPosition(100000)

        var message = itemView.findViewById<EditText>(R.id.txt_message);
        var img_btnsend = itemView.findViewById<ImageView>(R.id.img_btnsend);
        var btn_send = itemView.findViewById<CardView>(R.id.btn_send);


//        Timer().scheduleAtFixedRate(object : TimerTask() {
//            override fun run() {
//                activity?.runOnUiThread(Runnable {
//                    recyclerView.adapter?.notifyDataSetChanged()
//                })
//            }
//        }, 0, 1000)
                message.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                    @SuppressLint("NotifyDataSetChanged")
                    override fun afterTextChanged(s: Editable) {
                        if(!message.text.toString().isNullOrEmpty() && !message.text.toString().isNullOrBlank() && message.text.toString() != ""){
                            img_btnsend.setImageResource(R.drawable.icon_send);
                            img_btnsend.rotation=-25f
                            btn_send.setOnClickListener{
                                val sender = SessionManager(context).user!!.userNo.toString()
                                val message = itemView.findViewById<EditText>(R.id.txt_message).text.toString()

                                val receiver = listOf<Long>(Receiver);
                                hubConnection.send("SendMessage", sectionNo, sender, message, receiver, jobNo)
                                itemView.findViewById<EditText>(R.id.txt_message).text = null
                                recyclerView?.adapter?.notifyDataSetChanged()
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