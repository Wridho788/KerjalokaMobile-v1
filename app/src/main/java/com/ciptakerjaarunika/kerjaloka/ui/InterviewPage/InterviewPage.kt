package com.ciptakerjaarunika.kerjaloka.ui.InterviewPage

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
import com.ciptakerjaarunika.kerjaloka.ui.InterviewPage.Company.company_interview_adapter
import com.ciptakerjaarunika.kerjaloka.ui.InterviewPage.Company.company_interview_byjob
import com.ciptakerjaarunika.kerjaloka.ui.InterviewPage.Jobseeker.jobseeker_interview_adapter
import com.microsoft.signalr.Action1
import com.microsoft.signalr.HubConnection

class InterviewPage(val hubConnection: HubConnection) : Fragment(), CellClickListener{
    // TODO: Rename and change types of parameters
    private var isCompany : Boolean = true
    private var isLoading : Boolean = true
    private var Context = this;
    private lateinit var recyclerView : RecyclerView;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    private fun getCompanyData(){
        view?.findViewById<TextView>(R.id.titleToolbar)!!.text = "Interview"
        view?.findViewById<ImageButton>(R.id.backButton)!!.visibility = GONE;

        view?.findViewById<EditText>(R.id.searchInput)!!.hint= "Cari Lowongan"
        InterviewAPI().CompanyGetInterviewList(context) {
            if(it!=null) {
                isLoading = false

                recyclerView.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = company_interview_adapter(it.data, Context, hubConnection, context)
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
        }
    }
    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
//        val toolbar = itemView.findViewById<MaterialToolbar>(R.id.mainToolbar) as MaterialToolbar
//        toolbar.setTitle("Lamaran Saya")

        SessionManager(context).refreshChat(hubConnection);

        recyclerView = itemView?.findViewById<RecyclerView>(R.id.recyclerViewSection) as RecyclerView;

        var user = SessionManager(context).user
        isCompany = user != null && user.roleNo == 2

        if(isCompany) {
            this.getCompanyData()
        }
        else if(user!= null){
            view?.findViewById<TextView>(R.id.titleToolbar)!!.text = "Interview"
            view?.findViewById<ImageButton>(R.id.backButton)!!.visibility = GONE;

            view?.findViewById<EditText>(R.id.searchInput)!!.hint= "Cari Perusahaan"

            recyclerView.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = jobseeker_interview_adapter(SessionManager(context).chatData, Context, hubConnection)
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
    }


    override fun companyInterviewClick(SectionDetail : company_interview_list, hubConnection: HubConnection, jobNo : Long?) {
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.replace(id,  company_interview_byjob(SectionDetail, hubConnection, jobNo), "ChatFragment")
        ft.addToBackStack("interview_perjob")
        ft.commit()
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_interview, container, false)
    }
}
interface CellClickListener {
    fun goToChatPage(sectionName: String, sectionNo: Int?, hubConnection: HubConnection, jobNo : Long?, receiver : Long, logo : String?)
    fun companyInterviewClick(SectionDetail : company_interview_list, hubConnection: HubConnection, jobNo : Long?)
}