package com.ciptakerjaarunika.kerjaloka

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.PackageManagerCompat.LOG_TAG
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
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var context= baseContext

                binding = ActivityMainBinding.inflate(layoutInflater)
                setContentView(binding.root)
                replaceFragment(HomePage())

                binding.bottomNavigationView.setOnItemSelectedListener { item ->
                    when (item.itemId) {
                        R.id.home -> replaceFragment((HomePage()))
                        R.id.lamaran -> replaceFragment((LamaranPage()))
                        R.id.interview -> replaceFragment((InterviewPage()))
                        R.id.akun -> replaceFragment((AkunPage()))

                        else -> {

                        }
                    }
                    true
                }
            }

//        hubConnection.on("connected",
//            {res -> Log.d("Websocket Response : ", res.toString())
//                val userNo = SessionManager(context).user!!.userNo.toString()
//                hubConnection.send("Connecting", userNo, SessionManager(context).deviceId)
//                SessionManager(context).refreshChat(hubConnection);
//
//                this?.runOnUiThread(Runnable {
//                    if(stateFragment == null) {
//                        binding = ActivityMainBinding.inflate(layoutInflater)
//                        setContentView(binding.root)
//
//                        replaceFragment(HomePage(hubConnection))
//
//                        binding.bottomNavigationView.setOnItemSelectedListener { item ->
//                            when (item.itemId) {
//                                R.id.home -> replaceFragment((HomePage(hubConnection)))
//                                R.id.lamaran -> replaceFragment((LamaranPage(hubConnection)))
//                                R.id.interview -> replaceFragment((InterviewPage(hubConnection)))
//                                R.id.akun -> replaceFragment((AkunPage(hubConnection)))
//
//                                else -> {
//
//                                }
//                            }
//                            true
//                        }
//                    }
//                })
//
//            }, String::class.java)
//        hubConnection.onClosed{
//                if(SessionManager(context).user != null
//                    && hubConnection.connectionState != HubConnectionState.CONNECTED){
//                    hubConnection.start()
//                }
//        }

        //WebSocketService().startWebsocket();

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }
}
