package com.ciptakerjaarunika.kerjaloka.ui.InterviewPage.Company

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
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
import com.ciptakerjaarunika.kerjaloka.api.InterviewAPI
import com.ciptakerjaarunika.kerjaloka.model.Interview.chat_data
import com.ciptakerjaarunika.kerjaloka.model.Interview.company_interview_list
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.InterviewPage.ChatPage
import com.ciptakerjaarunika.kerjaloka.ui.InterviewPage.InterviewPage
import com.ciptakerjaarunika.kerjaloka.ui.InterviewPage.Jobseeker.jobseeker_interview_adapter
import com.microsoft.signalr.Action1
import com.microsoft.signalr.HubConnection

class company_interview_byjob(val SectionDetail : company_interview_list,val hubConnection: HubConnection,val jobNo : Long?) : Fragment(), CellClickListener{
    // TODO: Rename and change types of parameters
    private var isCompany : Boolean = true
    private var isLoading : Boolean = true
    private lateinit var recyclerView : RecyclerView;
    private var Context = this;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
//        val toolbar = itemView.findViewById<MaterialToolbar>(R.id.mainToolbar) as MaterialToolbar
//        toolbar.setTitle("Lamaran Saya")

        SessionManager(context).refreshChat(hubConnection);

        view?.findViewById<TextView>(R.id.titleToolbar)!!.text = SectionDetail.jobPosition;
        var backButton = view?.findViewById<ImageButton>(R.id.backButton) as ImageButton;

        backButton.visibility = VISIBLE;
        view?.findViewById<EditText>(R.id.searchInput)!!.hint= "Cari Pelamar"

        backButton.setOnClickListener{
            val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
            ft.replace(id,  InterviewPage(hubConnection), "InterviewPage")
            ft.commit()
        }
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
            ft.replace(id,  InterviewPage(hubConnection), "InterviewPage")
            ft.commit()
        }

        recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerViewSection) as RecyclerView;
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = company_interview_byjob_adapter(SectionDetail, Context, hubConnection, jobNo, context)
        }
        hubConnection.on<chat_data>(
            "getMessage",
            Action1<chat_data> { res: chat_data ->
                SessionManager(context).chatData = res
                activity?.runOnUiThread(Runnable {
                    recyclerView.adapter?.notifyDataSetChanged()
                })
            },
            chat_data::class.java
        )

    }

    override fun goToChatPage(sectionName: String, sectionNo:Int?, hubConnection: HubConnection, jobNo : Long?, receiver : Long, logo:String?) {
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.replace(id,  ChatPage(sectionName, sectionNo, hubConnection, jobNo, receiver, logo), "ChatFragment")
        ft.addToBackStack("SectionMessage")
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
    fun goToChatPage(sectionName: String, sectionNo: Int?, hubConnection: HubConnection, jobNo : Long?, receiver : Long, logo : String?)
}