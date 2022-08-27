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
import com.ciptakerjaarunika.kerjaloka.model.Interview.Messages
import com.ciptakerjaarunika.kerjaloka.model.Interview.chat_model
import com.ciptakerjaarunika.kerjaloka.model.Interview.company_interview_list
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.InterviewPage.Company.company_interview_adapter
import com.ciptakerjaarunika.kerjaloka.ui.InterviewPage.Company.company_interview_byjob
import com.ciptakerjaarunika.kerjaloka.ui.InterviewPage.Jobseeker.jobseeker_interview_adapter
import com.microsoft.signalr.HubConnection
import java.util.*

class InterviewPage(val hubConnection: HubConnection) : Fragment(), CellClickListener{
    // TODO: Rename and change types of parameters
    private var isCompany : Boolean = true
    private var isLoading : Boolean = true
    private var Context = this;



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
//    private fun getData(): List<chat_model> {
//        var list = listOf(
//            chat_model(
//                "PT. Pergi Hilang dan Lupakan",
//                1,
//                5,
//                listOf(
//                    Messages(
//                    "Hai",
//                    2918310239,
//                        Date(2022,8, 10,14,13),
//                    false,
//                    listOf())
//                )
//            ),
//            chat_model(
//                "PT. Suka Suka",
//                2,
//                12,
//                listOf(
//                    Messages(
//                        "Selamat siang, terimakasih telah melamar di PT. Suka Suka",
//                        2918310239,
//                        Date(2022,8, 18,10,13),
//                        false,
//                        listOf())
//                )
//            ),
//            chat_model(
//                "Sayang 1",
//                3,
//                1,
//                listOf(
//                    Messages(
//                        "Semangat kerjanya :-*",
//                        2918310239,
//                        Date(2022,8, 20,15,1),
//                        false,
//                        listOf()),
//                    Messages(
//                        "jngn telat makan siang ya",
//                        2918310239,
//                        Date(2022,8, 20,15,1),
//                        false,
//                        listOf()),
//                    Messages(
//                        "Iya kamu juga ya",
//                        2022,
//                        Date(2022,8, 20,15,2),
//                        false,
//                        listOf()),
//                    Messages(
//                        "Nanti malam keluar yuk?",
//                        2022,
//                        Date(2022,8, 20,15,3),
//                        false,
//                        listOf()),
//                    Messages(
//                        "Ada Cafe baru kemrin aku liat, kyknya enak tempatnya",
//                        2022,
//                        Date(2022,8, 20,15,3),
//                        false,
//                        listOf()),
//                    Messages(
//                        "Okey.. Jam berapa ?",
//                        2918310239,
//                        Date(2022,8, 20,15,3),
//                        false,
//                        listOf()),
//                    Messages(
//                        "Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint. Velit officia consequat duis enim velit mollit. Exercitation veniam consequat sunt nostrud amet.",
//                        2022,
//                        Date(2022,8, 20,15,3),
//                        false,
//                        listOf()),
//                    Messages(
//                        "P",
//                        2918310239,
//                        Date(2022,8, 20,15,3),
//                        false,
//                        listOf()),
//                    Messages(
//                        "P",
//                        2022,
//                        Date(2022,8, 20,15,3),
//                        false,
//                        listOf()),
//                    Messages(
//                        "P",
//                        2918310239,
//                        Date(2022,8, 20,15,3),
//                        false,
//                        listOf()),
//                    Messages(
//                        "P",
//                        2022,
//                        Date(2022,8, 20,15,3),
//                        false,
//                        listOf()),
//                )
//            ),
//            chat_model(
//                "Sayang 2",
//                3,
//                1,
//                listOf(
//                    Messages(
//                        "Semangat kerjanya :-*",
//                        2918310239,
//                        Date(2022,8, 20,10,1),
//                        false,
//                        listOf())
//                )
//            ),
//            chat_model(
//                "Sayang 3",
//                4,
//                1,
//                listOf(
//                    Messages(
//                        "Semangat kerjanya :-*",
//                        2918310239,
//                        Date(2022,8, 20,9,1),
//                        false,
//                        listOf())
//                )
//            ),
//            chat_model(
//                "Sayang 4",
//                5,
//                1,
//                listOf(
//                    Messages(
//                        "Semangat kerjanya :-*",
//                        2918310239,
//                        Date(2022,8, 20,11,1),
//                        false,
//                        listOf())
//                )
//            ),
//            chat_model(
//                "Sayang 5",
//                6,
//                1,
//                listOf(
//                    Messages(
//                        "Semangat kerjanya :-*",
//                        2918310239,
//                        Date(2022,8, 20,12,1),
//                        false,
//                        listOf())
//                )
//            ),
//            chat_model(
//                "Sayang 6",
//                7,
//                1,
//                listOf(
//                    Messages(
//                        "Semangat kerjanya :-*",
//                        2918310239,
//                        Date(2022,8, 20,13,1),
//                        false,
//                        listOf())
//                )
//            )
//        );
//
//        return list;
//    }

    private fun getCompanyData(){
        view?.findViewById<TextView>(R.id.titleToolbar)!!.text = "Interview"
        view?.findViewById<ImageButton>(R.id.backButton)!!.visibility = GONE;

        view?.findViewById<EditText>(R.id.searchInput)!!.hint= "Cari Lowongan"
        InterviewAPI().CompanyGetInterviewList(context) {
            if(it!=null) {
                isLoading = false
                val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerView) as RecyclerView;

                recyclerView.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = company_interview_adapter(it.data, Context, hubConnection, SessionManager(context).chatData, context)
                }
            }
        }
    }
    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
//        val toolbar = itemView.findViewById<MaterialToolbar>(R.id.mainToolbar) as MaterialToolbar
//        toolbar.setTitle("Lamaran Saya")

        var user = SessionManager(context).user
        isCompany = user != null && user.roleNo == 2
        println(user)
        println(isCompany)
        if(isCompany) {
            this.getCompanyData()
        }
        else{
            val recyclerView = itemView.findViewById<RecyclerView>(R.id.recyclerView) as RecyclerView;

            recyclerView.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = jobseeker_interview_adapter(SessionManager(context).chatData, Context, hubConnection)
            }
        }
    }


    override fun companyInterviewClick(SectionDetail : company_interview_list, hubConnection: HubConnection, jobNo : Long?) {
        view?.findViewById<TextView>(R.id.titleToolbar)!!.text = SectionDetail.jobPosition;
        var backButton = view?.findViewById<ImageButton>(R.id.backButton) as ImageButton;

        backButton.visibility = VISIBLE;
        view?.findViewById<EditText>(R.id.searchInput)!!.hint= "Cari Pelamar"
        backButton.setOnClickListener{
            getCompanyData()
        }

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            getCompanyData()
        }

        val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerView) as RecyclerView;

        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = company_interview_byjob(SectionDetail, Context, hubConnection, jobNo, SessionManager(context).chatData.sections)
        }
    }
    override fun goToChatPage(sectionName: String, sectionNo:Int?, hubConnection: HubConnection, jobNo : Long?, receiver : List<Long>) {
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.replace(id,  ChatPage(sectionName, sectionNo, hubConnection, jobNo, receiver), "ChatFragment")
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
    fun goToChatPage(sectionName: String, sectionNo: Int?, hubConnection: HubConnection, jobNo : Long?, receiver : List<Long>)
    fun companyInterviewClick(SectionDetail : company_interview_list, hubConnection: HubConnection, jobNo : Long?)
}