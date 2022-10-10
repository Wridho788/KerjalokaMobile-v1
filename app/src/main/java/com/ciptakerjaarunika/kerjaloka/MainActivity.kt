package com.ciptakerjaarunika.kerjaloka

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.ciptakerjaarunika.kerjaloka.api.AUTHAPI
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.databinding.ActivityMainBinding
import com.ciptakerjaarunika.kerjaloka.enum.Role
import com.ciptakerjaarunika.kerjaloka.model.Interview.chat_data
import com.ciptakerjaarunika.kerjaloka.model.Interview.incoming_call_model
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.AkunPage.AkunPage
import com.ciptakerjaarunika.kerjaloka.ui.HomePage.CompanyDashboard
import com.ciptakerjaarunika.kerjaloka.ui.HomePage.HomePage
import com.ciptakerjaarunika.kerjaloka.ui.InterviewPage.IncomingCallPage
import com.ciptakerjaarunika.kerjaloka.ui.InterviewPage.InterviewPage
import com.ciptakerjaarunika.kerjaloka.ui.LamaranPage.LamaranPage
import com.ciptakerjaarunika.kerjaloka.ui.LoginPage.Login
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ListApplicant.CompanyListApplicantFragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import com.microsoft.signalr.HubConnectionState
import java.lang.Boolean
import kotlin.Array
import kotlin.Int
import kotlin.IntArray
import kotlin.String


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var hubConnection: HubConnection

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
            val token = task.result
            SessionManager(baseContext).device_token = token
        })

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AUTHAPI().CheckLogin(baseContext, this) {

            var context = baseContext
            hubConnection = HubConnectionBuilder.create(config().portAddress + "/ws/chat").build()
            if (SessionManager(context).user != null && hubConnection.connectionState != HubConnectionState.CONNECTED) {
                hubConnection.start()

                hubConnection.on("connected",
                    { res ->
                        val userNo = SessionManager(context).user!!.userNo.toString()
                        hubConnection.send("Connecting", userNo, SessionManager(context).deviceId)
                    }, String::class.java
                )

                hubConnection.on(
                    "getmessage",
                    { res: chat_data ->
                        SessionManager(context).chatData = res
                    },
                    chat_data::class.java
                )
                hubConnection.on(
                    "incomingCall",
                    { data ->
                        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
                        ft.replace(
                            R.id.fragment_container,
                            IncomingCallPage(data),
                            "IncomingCall"
                        )
                        ft.addToBackStack("Main")
                        ft.commit()
                    },
                    incoming_call_model::class.java
                )
            }



//            window.decorView.setOnApplyWindowInsetsListener { view, insets ->
//                val insetsCompat = toWindowInsetsCompat(insets, view)
//                binding.bottomNavigationView.isGone = true
//                view.onApplyWindowInsets(insets)
//            }
            window.decorView.viewTreeObserver.addOnGlobalFocusChangeListener { oldView, newView ->
                val imm = baseContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                if (newView !is EditText) imm.hideSoftInputFromWindow(
                    (oldView ?: newView)?.windowToken
                        ?: window.attributes.token,
                    0 // or HIDE_IMPLICIT_ONLY
                )
            }


            var isCompany = SessionManager(context).user != null && (SessionManager(context).user?.roleNo == Role.Companies.value || SessionManager(context).user?.company != null)

            if (!isCompany) {
                replaceFragment(HomePage())
            } else {
                replaceFragment(CompanyDashboard())
            }
            binding.bottomNavigationView.menu.forEach { item ->
                if(item.itemId == R.id.lamaran){
                    if(!isCompany) {
                        item.title = "Lamaran"
                    }
                    else{
                        item.title = "Pelamar"
                    }
                }
            }
            binding.bottomNavigationView.setOnItemSelectedListener { item ->
                var isCompany = SessionManager(context).user != null && (SessionManager(context).user?.roleNo == Role.Companies.value || SessionManager(context).user?.company != null)

                when (item.itemId) {
                    R.id.home -> replaceFragment(if(!isCompany) HomePage() else CompanyDashboard())
                    R.id.lamaran -> {
                        if(!isCompany) {
                            replaceFragment(LamaranPage())
                        }
                        else{
                            item.title = "Pelamar"
                            replaceFragment(CompanyListApplicantFragment())
                        }
                    }
                    R.id.interview -> replaceFragment(InterviewPage())
                    R.id.akun -> replaceFragment(AkunPage())
                }
                true
            }
//            binding.bottomNavigationCompanyView.setOnItemSelectedListener { item ->
//                when (item.itemId) {
//                    R.id.home -> replaceFragment(CompanyDashboard())
//                    R.id.pelamar -> replaceFragment(CompanyListApplicantFragment())
//                    R.id.interview -> replaceFragment(InterviewPage())
//                    R.id.akun -> replaceFragment(AkunPage())
//                }
//                true
//            }
        }

        val settings = getSharedPreferences("prefs", 0)
        val editor = settings.edit()
        editor.putBoolean("firstRun", false)
        editor.commit()

        val firstRun = settings.getBoolean("firstRun", true)
        Log.d("TAG1", "firstRun: " + Boolean.valueOf(firstRun).toString())

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

    private var MY_CAMERA_REQUEST_CODE = 100

    //WebSocketService().startWebsocket();
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if(requestCode == 101) {
            supportFragmentManager.fragments.find { a ->
                a.id != 0
            }?.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        }
        }

    open fun replaceFragment(fragment: Fragment) {
//        AUTHAPI().CheckLogin(baseContext, this) {
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, fragment)
            fragmentTransaction.commit()
//        }
    }

    open fun showLogin(Goto: Fragment, nameFragment: String) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, Login(Goto, nameFragment))
        fragmentTransaction.commit()
    }

    open fun refreshBottomSheet() {
        var isCompany = SessionManager(baseContext).user != null && (SessionManager(baseContext).user?.roleNo == Role.Companies.value || SessionManager(baseContext).user?.company != null)
        binding.bottomNavigationView.menu.forEach { item ->
            if(item.itemId == R.id.lamaran){
                if(!isCompany) {
                    item.title = "Lamaran"
                }
                else{
                    item.title = "Pelamar"
                }
            }
        }
    }
}
