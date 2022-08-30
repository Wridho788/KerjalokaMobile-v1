package com.ciptakerjaarunika.kerjaloka.ui.InterviewPage

import android.annotation.SuppressLint
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
import com.ciptakerjaarunika.kerjaloka.model.Interview.chat_data
import com.ciptakerjaarunika.kerjaloka.model.Interview.chat_model
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.microsoft.signalr.Action1
import com.microsoft.signalr.HubConnection
import java.util.*


class ChatPage(var sectionName: String,
               var sectionNo : Int?,
               val hubConnection: HubConnection,
               val jobNo : Long?,
               val Receiver : Long)
    :  Fragment() {

    private var chatModel : chat_model? = null
    private lateinit var recyclerView : RecyclerView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        hubConnection.send("RefreshMessage", SessionManager(context).user!!.userNo.toString())

        hubConnection.on<chat_data>(
            "getMessage",
            Action1<chat_data> { res: chat_data ->
                SessionManager(context).chatData = res
                Log.d("Refresh", "Ada pesan baru")

                activity?.runOnUiThread(Runnable {
                    recyclerView.adapter?.notifyDataSetChanged()
                })
            },
            chat_data::class.java
        )
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        val titlePage = itemView.findViewById<TextView>(R.id.title)
        titlePage.text = sectionName
        val backButton = itemView.findViewById<ImageButton>(R.id.backButton)

        backButton.setOnClickListener{
            parentFragmentManager.popBackStack()
        }

        var LinearLayoutManager = LinearLayoutManager(activity)

        //recyclerView.scrollToPosition(section.Messages.size-1)
        recyclerView = itemView.findViewById<RecyclerView>(R.id.recyclerViewChat) as RecyclerView
        recyclerView?.apply {
            layoutManager = LinearLayoutManager
            adapter = ChatAdapter(context, jobNo, Receiver)
        }

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
                        if(!message.text.toString().isNullOrEmpty() && !message.text.toString().isNullOrBlank()){
                            img_btnsend.setImageResource(R.drawable.icon_send);
                            img_btnsend.rotation=-25f
                            btn_send.setOnClickListener{
                                Log.d("Clicke", "Clicked")
                                val sender = SessionManager(context).user!!.userNo.toString()
                                val message = itemView.findViewById<EditText>(R.id.txt_message).text.toString()

                                val receiver = listOf<Long>(Receiver);

                                hubConnection.send("SendMessage", sectionNo, sender, message, receiver, jobNo)
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