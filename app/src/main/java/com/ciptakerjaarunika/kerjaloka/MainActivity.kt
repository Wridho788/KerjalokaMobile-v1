package com.ciptakerjaarunika.kerjaloka

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.databinding.ActivityMainBinding
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.AkunPage.AkunPage
import com.ciptakerjaarunika.kerjaloka.ui.HomePage.HomePage
import com.ciptakerjaarunika.kerjaloka.ui.InterviewPage.InterviewPage
import com.ciptakerjaarunika.kerjaloka.ui.LamaranPage.LamaranPage
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ManageCV.PapikostickResult
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder

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
//        if(SessionManager(context).user != null && hubConnection.connectionState == HubConnectionState.DISCONNECTED){
//            hubConnection.start()
//            while (hubConnection.connectionState == HubConnectionState.DISCONNECTED){}
//                val userNo = SessionManager(context).user!!.userNo.toString()
//                hubConnection.send("Connecting", userNo)
//        }
//        SessionManager(context).chatData = null
//        hubConnection.on("getMessage",
//            {chat ->
//                SessionManager(context).chatData = chat;
//                Log.d("newMessage", chat.toString())
//            }, chat_data::class.java )
//        hubConnection.on("getMessage", {->
//
//        },)
        //WebSocketService().startWebsocket();

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> replaceFragment((HomePage()))
                R.id.lamaran -> replaceFragment((PapikostickResult()))
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
