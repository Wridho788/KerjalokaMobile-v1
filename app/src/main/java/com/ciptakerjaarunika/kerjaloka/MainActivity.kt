package com.ciptakerjaarunika.kerjaloka

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.anychart.ui.contextmenu.Item
import com.ciptakerjaarunika.kerjaloka.api.AUTHAPI
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.databinding.ActivityMainBinding
import com.ciptakerjaarunika.kerjaloka.enum.Role
import com.ciptakerjaarunika.kerjaloka.model.Interview.chat_data
import com.ciptakerjaarunika.kerjaloka.model.Interview.incoming_call_model
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.viewmodel.AkunPage.AkunPage
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.CompanyApplicant.ListApplicant.CompanyListApplicantFragment
import com.ciptakerjaarunika.kerjaloka.viewmodel.Components.DeactivatedAccount
import com.ciptakerjaarunika.kerjaloka.viewmodel.HomePage.CompanyDashboard
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.HomePage.HomePage
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.InterviewPage.IncomingCallPage
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.InterviewPage.InterviewPage
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.LamaranPage.LamaranPage
import com.ciptakerjaarunika.kerjaloka.viewmodel.LoginPage.Login
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.perf.ktx.performance
import com.google.firebase.perf.metrics.AddTrace
import com.instabug.apm.APM
import com.instabug.library.Instabug
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
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    @AddTrace(name = "onCreateTrace", enabled = true)
    class ItemCache {
        fun fetch(name: String): Item? {
            return null
        }
    }

    fun MainActivityTrace() {
        val cache = ItemCache()
        val myTrace = Firebase.performance.newTrace("main_activity_trace")
        myTrace.start()
        val item = cache.fetch("item")
        if (item != null) {
            myTrace.incrementMetric("item_cache_hit", 1)
        } else {
            myTrace.incrementMetric("item_cache_miss", 1)
        }
        myTrace.stop()
    }

    override fun onResume() {
        super.onResume()
        APM.setColdAppLaunchEnabled(true)
        APM.setHotAppLaunchEnabled(true)
        APM.setFragmentSpansEnabled(true)
        APM.setScreenLoadingEnabled(true)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        firebaseAnalytics = Firebase.analytics
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM) {
            Log.d("analytic_id", FirebaseAnalytics.Param.ITEM_ID)
            Log.d("analytic_name", FirebaseAnalytics.Param.ITEM_NAME)
            Log.d("analytic_image", FirebaseAnalytics.Param.CONTENT_TYPE)
        }
        Instabug.Builder(application, "0f18f4933ee2994d9e2d30309e7e213b")
            .build()
        MainActivityTrace()
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

                hubConnection.on(
                    "connected",
                    { res ->
                        Log.d("Connected Res", res)
                        val userNo = SessionManager(context).user!!.userNo.toString()
                        hubConnection.send("Connecting", userNo, SessionManager(context).deviceId)
                    }, String::class.java
                )

                hubConnection.on(
                    "getmessage",
                    { res: chat_data ->
                        Log.d("getmessage Res", res.toString())
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

            window.decorView.viewTreeObserver.addOnGlobalFocusChangeListener { oldView, newView ->
                val imm =
                    baseContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                if (newView !is EditText) imm.hideSoftInputFromWindow(
                    (oldView ?: newView)?.windowToken
                        ?: window.attributes.token,
                    0 // or HIDE_IMPLICIT_ONLY
                )
            }

            var isCompany =
                SessionManager(context).user != null && (SessionManager(context).user?.roleNo == Role.Companies.value || SessionManager(
                    context
                ).user?.company != null)

            if (SessionManager(context).user != null && SessionManager(context).user?.deactivated!! == true) {
                val intent = Intent(baseContext, DeactivatedAccount::class.java)
                startActivity(intent)
            } else {
                if (!isCompany) {
                    replaceFragment(HomePage())
                } else {
                    replaceFragment(CompanyDashboard())
                }

            }
            binding.bottomNavigationView.menu.forEach { item ->
                if (item.itemId == R.id.lamaran) {
                    if (!isCompany) {
                        item.title = "Lamaran"
                    } else {
                        item.title = "Pelamar"
                    }
                }
            }
            binding.bottomNavigationView.setOnItemSelectedListener { item ->
                var isCompany =
                    SessionManager(context).user != null && (SessionManager(context).user?.roleNo == Role.Companies.value || SessionManager(
                        context
                    ).user?.company != null)

                when (item.itemId) {
                    R.id.home -> replaceFragment(if (!isCompany) HomePage() else CompanyDashboard())
                    R.id.lamaran -> {
                        if (!isCompany) {
                            replaceFragment(LamaranPage())
                        } else {
                            item.title = "Pelamar"
                            replaceFragment(CompanyListApplicantFragment())
                        }
                    }
                    R.id.interview -> replaceFragment(InterviewPage())
                    R.id.akun -> replaceFragment(AkunPage())
                }
                true
            }
        }

        val settings = getSharedPreferences("prefs", 0)
        val editor = settings.edit()
        editor.putBoolean("firstRun", false)
        editor.commit()

        val firstRun = settings.getBoolean("firstRun", true)
        Log.d("TAG1", "firstRun: " + Boolean.valueOf(firstRun).toString())

    }


    override fun onDestroy() {
        super.onDestroy()
        APM.endAppLaunch();
    }
    private var MY_CAMERA_REQUEST_CODE = 100

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        supportFragmentManager.fragments.find { a ->
            a.id == requestCode - baseContext.resources.getInteger(R.integer.LampiranUploadFile) ||
                    a.id == requestCode - baseContext.resources.getInteger(R.integer.ChatPickCamera) ||
                    a.id == requestCode - baseContext.resources.getInteger(R.integer.ChatUploadFile) ||
                    a.id == requestCode - (baseContext.resources.getInteger(R.integer.UploadVaccine) + 1) ||
                    a.id == requestCode - (baseContext.resources.getInteger(R.integer.UploadVaccine) + 2) ||
                    a.id == requestCode - (baseContext.resources.getInteger(R.integer.UploadVaccine) + 3)
        }?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    open fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }

    open fun showLogin(Goto: Fragment, nameFragment: String) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, Login(Goto, nameFragment))
        fragmentTransaction.commit()
    }

    open fun refreshBottomSheet() {
        var isCompany =
            SessionManager(baseContext).user != null && (SessionManager(baseContext).user?.roleNo == Role.Companies.value || SessionManager(
                baseContext
            ).user?.company != null)
        binding.bottomNavigationView.menu.forEach { item ->
            if (item.itemId == R.id.lamaran) {
                if (!isCompany) {
                    item.title = "Lamaran"
                } else {
                    item.title = "Pelamar"
                }
            }
        }
    }
}
