package com.ciptakerjaarunika.kerjaloka

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.databinding.ActivityMainBinding
import com.ciptakerjaarunika.kerjaloka.model.Interview.chat_data
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.AkunPage.AkunPage
import com.ciptakerjaarunika.kerjaloka.ui.HomePage.HomePage
import com.ciptakerjaarunika.kerjaloka.ui.InterviewPage.InterviewPage
import com.ciptakerjaarunika.kerjaloka.ui.LamaranPage.LamaranPage
import com.microsoft.signalr.Action1
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import com.microsoft.signalr.HubConnectionState
import java.lang.reflect.Type
import java.util.*
import kotlin.jvm.internal.TypeReference


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    lateinit var hubConnection: HubConnection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomePage())
        var context= baseContext

        hubConnection = HubConnectionBuilder.create(config().portAddress+"ws/chat").build()
        Log.d("User", SessionManager(context).user.toString())

        Log.d("state : ",hubConnection.connectionState.toString())
        if(SessionManager(context).user != null && hubConnection.connectionState != HubConnectionState.CONNECTED){
            hubConnection.start()
        }
        hubConnection.on("connected",
            {res -> Log.d("Websocket Response : ", res.toString())
                val userNo = SessionManager(context).user!!.userNo.toString()
                hubConnection.send("Connecting", userNo)
            }, String::class.java)

        hubConnection.on<chat_data>(
            "getMessage",
            Action1<chat_data> { res: chat_data ->
                Log.d("Cast", res.toString())
                SessionManager(context).chatData = res
                Log.d("Cast", SessionManager(context).chatData.toString())
            },
            chat_data::class.java
        )

//        hubConnection.<chat_data>on("getMessage",
//            {
//                if(it is chat_data? || it is chat_data){
//                    Log.d("Response is", "Chat Data")
//                }
//                else if(it is Object){
//
//                    Log.d("Response is", "Object")
//                    Log.d("Response is", it.toString())
//                }
//                val chat :chat_data? = it as? chat_data?
//
//                Log.d("Cast", chat.toString())
//                if (chat != null) {
//                    SessionManager(context).chatData = chat_data(sections = chat.sections)
//                }
//            }, Any::class.java )

        //WebSocketService().startWebsocket();

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> replaceFragment((HomePage()))
                R.id.lamaran -> replaceFragment((LamaranPage()))
                R.id.interview -> replaceFragment((InterviewPage(hubConnection)))
                R.id.akun -> replaceFragment((AkunPage()))

            else ->{

                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }
}
