package com.ciptakerjaarunika.kerjaloka.ui.InterviewPage

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.MainActivity
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.InterviewAPI
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.model.Interview.chat_data
import com.ciptakerjaarunika.kerjaloka.model.Interview.company_interview_list
import com.ciptakerjaarunika.kerjaloka.model.Interview.incoming_call_model
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.InterviewPage.Company.company_interview_adapter
import com.ciptakerjaarunika.kerjaloka.ui.InterviewPage.Company.company_interview_byjob
import com.ciptakerjaarunika.kerjaloka.ui.InterviewPage.Jobseeker.jobseeker_interview_adapter
import com.ciptakerjaarunika.kerjaloka.ui.LoginPage.Login
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import com.microsoft.signalr.HubConnectionState
import java.util.*

class InterviewPage : Fragment(), CellClickListener {
    // TODO: Rename and change types of parameters
    private var isCompany: Boolean = true
    private var Context = this
    private var recyclerView: RecyclerView? = null
    private lateinit var hubConnection: HubConnection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        hubConnection = HubConnectionBuilder.create(config().portAddress + "ws/chat").build()
        if (SessionManager(context).user != null && hubConnection.connectionState != HubConnectionState.CONNECTED) {
            hubConnection.start()
        }

    }

    private fun getCompanyData() {
        view?.findViewById<TextView>(R.id.titleToolbar)!!.text = "Interview"
        view?.findViewById<ImageButton>(R.id.backButton)!!.visibility = GONE
        val spinner = view?.findViewById<LinearLayout>(R.id.spinnerInterviw)
        var search = view?.findViewById<EditText>(R.id.searchInput)
        search?.hint = "Cari Lowongan"

        InterviewAPI().CompanyGetInterviewList(context) {
            if (it != null) {
                if (it.data.size == 0) {
                    spinner?.visibility = View.GONE
                } else {
                    spinner?.visibility = View.GONE
                    recyclerView?.apply {
                        layoutManager = LinearLayoutManager(activity)
                        adapter = company_interview_adapter(it.data, Context, context)
                    }
                    search?.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(
                            p0: CharSequence?,
                            p1: Int,
                            p2: Int,
                            p3: Int
                        ) {
                        }

                        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                        @SuppressLint("NotifyDataSetChanged")
                        override fun afterTextChanged(s: Editable) {
                            if (!search.text.toString().isNullOrEmpty() && !search.text.toString()
                                    .isNullOrBlank() && search.text.toString() != ""
                            ) {
                                recyclerView?.apply {
                                    layoutManager = LinearLayoutManager(activity)
                                    adapter = company_interview_adapter(it.data.filter { list ->
                                        list.jobPosition.lowercase(Locale.getDefault())
                                            .contains(
                                                search.text.toString()
                                                    .lowercase(Locale.getDefault())
                                            )
                                    }, Context, context)
                                }
                                recyclerView?.adapter?.notifyDataSetChanged()
                            } else {
                                recyclerView?.apply {
                                    layoutManager = LinearLayoutManager(activity)
                                    adapter = company_interview_adapter(it.data, Context, context)
                                }
                                recyclerView?.adapter?.notifyDataSetChanged()
                            }
                        }
                    })
                }
            }
        }
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        var user = SessionManager(context).user


        var spinner = view?.findViewById<LinearLayout>(R.id.spinnerInterviw)
        recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerViewSection)

        hubConnection.on("connected",
            { res ->
                Log.d("Websocket Response : ", res.toString())
                val userNo = SessionManager(context).user!!.userNo.toString()

                hubConnection.send("Connecting", userNo, SessionManager(context).deviceId)
            }, String::class.java
        )

        hubConnection.on(
            "getmessage",
            { res: chat_data ->
                Log.d("Chat data : ", res.toString())
                SessionManager(context).chatData = res

                activity?.runOnUiThread(Runnable {
                    recyclerView?.adapter?.notifyDataSetChanged()
                    spinner?.visibility = GONE
                    recyclerView?.visibility = VISIBLE
                })
            },
            chat_data::class.java
        )
        hubConnection.on(
            "incomingCall",
            { data ->
                val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
                ft.replace(id, IncomingCallPage(data), "IncomingCall")
                ft.addToBackStack("InterviewPage")
                ft.commit()
            },
            incoming_call_model::class.java
        )


        if (user != null && (user.roleNo == 2 || user.roleNo > 4)) {
            this.getCompanyData()
        } else if (user != null && user.roleNo == 4) {
            view?.findViewById<TextView>(R.id.titleToolbar)!!.text = "Interview"
            view?.findViewById<ImageButton>(R.id.backButton)!!.visibility = GONE

            val search = view?.findViewById<EditText>(R.id.searchInput)
            search!!.hint = "Cari Perusahaan"
            val mainActivity = activity as MainActivity
            InterviewAPI().JobseekerGetInterviewList(context, mainActivity) {
                if (it != null) {
                    recyclerView?.apply {
                        layoutManager = LinearLayoutManager(activity)
                        adapter = jobseeker_interview_adapter(it.data, context, Context)
                    }
                    search.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(
                            p0: CharSequence?,
                            p1: Int,
                            p2: Int,
                            p3: Int
                        ) {
                        }

                        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                        @SuppressLint("NotifyDataSetChanged")
                        override fun afterTextChanged(s: Editable) {
                            if (!search.text.toString().isNullOrEmpty() && !search.text.toString()
                                    .isNullOrBlank() && search.text.toString() != ""
                            ) {
                                recyclerView?.apply {
                                    layoutManager = LinearLayoutManager(activity)
                                    adapter = jobseeker_interview_adapter(it.data.filter { list ->
                                        list.jobPosition.lowercase(Locale.getDefault())
                                            .contains(
                                                search.text.toString()
                                                    .lowercase(Locale.getDefault()))
                                    }, context, this@InterviewPage)
                                }
                                recyclerView?.adapter?.notifyDataSetChanged()
                            } else {
                                recyclerView?.apply {
                                    layoutManager = LinearLayoutManager(activity)
                                    adapter = jobseeker_interview_adapter(it.data, context, Context)
                                }
                                recyclerView?.adapter?.notifyDataSetChanged()
                            }
                        }
                    })
                }
            }
        } else if (user == null) {
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(id, Login(this, "interview"))
            fragmentTransaction.commit()
        }
    }


    override fun companyInterviewClick(SectionDetail: company_interview_list, jobNo: Long?) {
        hubConnection.stop()
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.replace(id, company_interview_byjob(SectionDetail, jobNo), "ChatFragment")
//        ft.addToBackStack("interview_perjob")
        ft.commit()
    }

    override fun goToChatPage(
        sectionName: String,
        sectionNo: Int?,
        jobNo: Long?,
        receiver: Long,
        logo: String?,
        jobPosition: String?
    ) {
        hubConnection.stop()
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.replace(
            id,
            ChatPage(sectionName, sectionNo, jobNo, receiver, logo, jobPosition),
            "ChatFragment"
        )
//        ft.addToBackStack("SectionMessage")
        ft.commit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_interview, container, false)
    }
}

interface CellClickListener {
    fun goToChatPage(
        sectionName: String,
        sectionNo: Int?,
        jobNo: Long?,
        receiver: Long,
        logo: String?,
        jobPosition: String?
    )

    fun companyInterviewClick(SectionDetail: company_interview_list, jobNo: Long?)
}