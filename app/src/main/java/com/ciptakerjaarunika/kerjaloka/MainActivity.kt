package com.ciptakerjaarunika.kerjaloka

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.ciptakerjaarunika.kerjaloka.api.InterviewAPI
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.databinding.ActivityMainBinding
import com.ciptakerjaarunika.kerjaloka.model.Interview.chat_data
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.AkunPage.AkunPage
import com.ciptakerjaarunika.kerjaloka.ui.HomePage.HomePage
import com.ciptakerjaarunika.kerjaloka.ui.InterviewPage.IncomingCallPage
import com.ciptakerjaarunika.kerjaloka.ui.InterviewPage.InterviewPage
import com.ciptakerjaarunika.kerjaloka.ui.LamaranPage.LamaranPage
import com.ciptakerjaarunika.kerjaloka.ui.LoginPage.Login
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.profilepage
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import com.microsoft.signalr.HubConnectionState
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var activityResultLauncher : ActivityResultLauncher<Intent>
    private lateinit var hubConnection: HubConnection

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var context= baseContext

        hubConnection = HubConnectionBuilder.create(config().portAddress+"/ws/chat").build()
        if(SessionManager(context).user != null && hubConnection.connectionState != HubConnectionState.CONNECTED){
            hubConnection.start()

            hubConnection.on("connected",
                { res ->
                    val userNo = SessionManager(context).user!!.userNo.toString()
                    hubConnection.send("Connecting", userNo, SessionManager(context).deviceId)
                }, String::class.java)

            hubConnection.on(
                "getmessage",
                { res: chat_data ->
                    SessionManager(context).chatData = res
                },
                chat_data::class.java
            )
            hubConnection.on(
                "incomingCall",
                { roomId ->
                    val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
                    ft.replace(R.id.fragment_container, IncomingCallPage(roomId), "IncomingCall")
                    ft.commit()
                },
                String::class.java
            )
        }



                binding = ActivityMainBinding.inflate(layoutInflater)
                setContentView(binding.root)
                replaceFragment(HomePage())

                binding.bottomNavigationView.setOnItemSelectedListener { item ->
                    when (item.itemId) {
                        R.id.home -> replaceFragment((HomePage()))
                        R.id.lamaran -> replaceFragment((LamaranPage()))
                        R.id.interview -> replaceFragment((InterviewPage()))
                        R.id.akun -> replaceFragment((profilepage()))

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

        private var MY_CAMERA_REQUEST_CODE = 100;
        //WebSocketService().startWebsocket();
        override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray
        ) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            Log.d("Request Code", requestCode.toString())
            if (requestCode == MY_CAMERA_REQUEST_CODE) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    val intent = Intent("android.media.action.IMAGE_CAPTURE")
                    activityResultLauncher.launch(intent)
                } else {
                    Toast.makeText(baseContext, "Perlu akses kamera untuk fitur ini", Toast.LENGTH_LONG).show()
                }
            }
        }

    private fun replaceFragment(fragment: Fragment) {
        binding.bottomNavigationView.visibility = View.VISIBLE

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }
    open fun showLogin(){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, Login())
        fragmentTransaction.commit()
    }
}
