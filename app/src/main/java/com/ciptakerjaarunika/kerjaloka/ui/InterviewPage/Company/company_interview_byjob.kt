package com.ciptakerjaarunika.kerjaloka.ui.InterviewPage.Company

import android.os.Bundle
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
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.InterviewAPI
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.model.Interview.chat_data
import com.ciptakerjaarunika.kerjaloka.model.Interview.company_interview_list
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.InterviewPage.ChatPage
import com.ciptakerjaarunika.kerjaloka.ui.InterviewPage.InterviewPage
import com.ciptakerjaarunika.kerjaloka.ui.InterviewPage.Jobseeker.jobseeker_interview_adapter
import com.microsoft.signalr.Action1
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import com.microsoft.signalr.HubConnectionState

class company_interview_byjob(val SectionDetail : company_interview_list, val jobNo : Long?) : Fragment(), CellClickListener{
    // TODO: Rename and change types of parameters
    private var isCompany : Boolean = true
    private var isLoading : Boolean = true
    private lateinit var recyclerView : RecyclerView;
    private var Context = this;
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
//        val toolbar = itemView.findViewById<MaterialToolbar>(R.id.mainToolbar) as MaterialToolbar
//        toolbar.setTitle("Lamaran Saya")

        hubConnection.on("connected",
            { res ->
                Log.d("Websocket Response : ", res.toString())
                val userNo = SessionManager(context).user!!.userNo.toString()
                hubConnection.send("Connecting", userNo, SessionManager(context).deviceId)
            }, String::class.java)

        var spinner = itemView.findViewById<LinearLayout>(R.id.spinnerInterviewByJob)
        recyclerView = itemView.findViewById<RecyclerView>(R.id.recyclerViewSection) as RecyclerView;
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = company_interview_byjob_adapter(SectionDetail, Context, jobNo, context)
        }

        hubConnection.on<chat_data>(
            "getmessage",
            Action1<chat_data> { res: chat_data ->
                SessionManager(context).chatData = res
                activity?.runOnUiThread(Runnable {
                    spinner.visibility = GONE;
                    recyclerView?.visibility = VISIBLE;

                    recyclerView.adapter?.notifyDataSetChanged()
                })
            },
            chat_data::class.java
        )


        view?.findViewById<EditText>(R.id.searchInput)!!.hint= "Cari Pelamar"
        view?.findViewById<TextView>(R.id.titleToolbar)!!.text = SectionDetail.jobPosition;
        var backButton = view?.findViewById<ImageButton>(R.id.backButton) as ImageButton;
        backButton.visibility = VISIBLE;

        backButton.setOnClickListener{
            hubConnection.stop()
            val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
            ft.replace(id,  InterviewPage(), "InterviewPage")
            ft.commit()
        }
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            hubConnection.stop()
            val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
            ft.replace(id,  InterviewPage(), "InterviewPage")
            ft.commit()
        }
    }

    override fun goToChatPage(sectionName: String, sectionNo:Int?, jobNo : Long?, receiver : Long, logo:String?) {
        hubConnection.stop();
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.replace(id,  ChatPage(sectionName, sectionNo, jobNo, receiver, logo), "ChatFragment")
        ft.commit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_company_interview_perjob, container, false)
    }
}
interface CellClickListener {
    fun goToChatPage(sectionName: String, sectionNo: Int?, jobNo : Long?, receiver : Long, logo : String?)
}