package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.InterviewPage.Company

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentCompanyInterviewPerjobBinding
import com.ciptakerjaarunika.kerjaloka.model.Interview.chat_data
import com.ciptakerjaarunika.kerjaloka.model.Interview.company_interview_list
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.InterviewPage.ChatPage
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.InterviewPage.InterviewPage
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import com.microsoft.signalr.HubConnectionState
import java.util.*

class company_interview_byjob(val SectionDetail: company_interview_list, val jobNo: Long?) :
    Fragment(), CellClickListener {
    private var isCompany: Boolean = true

    //    private var isLoading: Boolean = true
    private lateinit var recyclerView: RecyclerView
    private var Context = this
    private lateinit var hubConnection: HubConnection
    private lateinit var binding: FragmentCompanyInterviewPerjobBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        hubConnection = HubConnectionBuilder.create(config().portAddress + "/ws/chat").build()
        if (SessionManager(context).user != null && hubConnection.connectionState != HubConnectionState.CONNECTED) {
            hubConnection.start()
            hubConnection.on(
                "connected", { res ->
                    binding.spinnerInterviewByJob.visibility = View.GONE
                    val userNo = SessionManager(context).user!!.userNo.toString()
                    hubConnection.send("Connecting", userNo, SessionManager(context).deviceId)
                }, String::class.java
            )
        }
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        hubConnection.on(
            "getmessage", { res: chat_data ->
                SessionManager(context).chatData = res
                activity?.runOnUiThread {
                    binding.spinnerInterviewByJob.visibility = View.GONE
                    binding.recyclerViewSection.visibility = VISIBLE

                    binding.recyclerViewSection.adapter?.notifyDataSetChanged()
                }
            }, chat_data::class.java
        )
        binding.recyclerViewSection.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = company_interview_byjob_adapter(
                SectionDetail, Context, jobNo, context, SectionDetail.jobPosition
            )
        }
        val search = view?.findViewById<EditText>(R.id.searchInput)
        search!!.hint = "Cari Pelamar"

        search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            @SuppressLint("NotifyDataSetChanged")
            override fun afterTextChanged(s: Editable) {
                if (!search.text.toString().isNullOrEmpty() && !search.text.toString()
                        .isNullOrBlank() && search.text.toString() != ""
                ) {
                    val temp = SectionDetail.interviewer.filter { item ->
                        item.jobseekerName.lowercase(Locale.getDefault())
                            .contains(search.text.toString().lowercase(Locale.getDefault()))
                    }
                    recyclerView.apply {
                        layoutManager = LinearLayoutManager(activity)
                        adapter = company_interview_byjob_adapter(
                            company_interview_list(
                                SectionDetail.jobPosition, SectionDetail.jobNo, temp
                            ), Context, jobNo, context, SectionDetail.jobPosition
                        )
                    }
                    recyclerView.adapter?.notifyDataSetChanged()
                } else {
                    recyclerView.apply {
                        layoutManager = LinearLayoutManager(activity)
                        adapter = company_interview_byjob_adapter(
                            SectionDetail, Context, jobNo, context, SectionDetail.jobPosition
                        )
                    }
                    recyclerView.adapter?.notifyDataSetChanged()
                }
            }
        })

        view?.findViewById<TextView>(R.id.titleToolbar)!!.text = SectionDetail.jobPosition
        val backButton = view?.findViewById<ImageButton>(R.id.backButton)
        backButton?.visibility = VISIBLE

        backButton?.setOnClickListener {
            hubConnection.stop()
            val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
            ft.replace(id, InterviewPage(), "InterviewPage")
            ft.commit()
        }
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            hubConnection.stop()
            val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
            ft.replace(id, InterviewPage(), "InterviewPage")
            ft.commit()
        }
    }

    override fun goToChatPage(
        sectionName: String,
        sectionNo: Int?,
        jobNo: Long?,
        receiver: Long,
        logo: String?,
        jobPosition: String?
    ) {
//        hubConnection.stop()
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.replace(
            id, ChatPage(sectionName, sectionNo, jobNo, receiver, logo, jobPosition), "ChatFragment"
        )
        ft.commit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCompanyInterviewPerjobBinding.inflate(layoutInflater)
        val view = binding.root
        return view
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
}