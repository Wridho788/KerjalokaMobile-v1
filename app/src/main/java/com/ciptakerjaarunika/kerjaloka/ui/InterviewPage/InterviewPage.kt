package com.ciptakerjaarunika.kerjaloka.ui.InterviewPage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.InterviewAPI
import com.ciptakerjaarunika.kerjaloka.api.JobAPI
import com.ciptakerjaarunika.kerjaloka.model.Interview.Messages
import com.ciptakerjaarunika.kerjaloka.model.Interview.chat_model
import com.ciptakerjaarunika.kerjaloka.model.Job.jobHomeListData
import com.ciptakerjaarunika.kerjaloka.ui.InterviewPage.ChatPage.ChatPage
import java.util.*

class InterviewPage : Fragment(), CellClickListener{
    // TODO: Rename and change types of parameters
    private var isCompany : Boolean = true
    private var isLoading : Boolean = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    private fun getData(): List<chat_model> {
        var list = listOf(
            chat_model(
                "PT. Pergi Hilang dan Lupakan",
                5,
                listOf(
                    Messages(
                    "Hai",
                    2918310239,
                        Date(2022,8, 10,14,13),
                    false,
                    listOf())
                )
            ),
            chat_model(
                "PT. Suka Suka",
                12,
                listOf(
                    Messages(
                        "Selamat siang, terimakasih telah melamar di PT. Suka Suka",
                        2918310239,
                        Date(2022,8, 18,10,13),
                        false,
                        listOf())
                )
            ),
            chat_model(
                "Sayang 1",
                1,
                listOf(
                    Messages(
                        "Semangat kerjanya :-*",
                        2918310239,
                        Date(2022,8, 20,15,1),
                        false,
                        listOf()),
                    Messages(
                        "jngn telat makan siang ya",
                        2918310239,
                        Date(2022,8, 20,15,1),
                        false,
                        listOf()),
                    Messages(
                        "Iya kamu juga ya",
                        2022,
                        Date(2022,8, 20,15,2),
                        false,
                        listOf()),
                    Messages(
                        "Nanti malam keluar yuk?",
                        2022,
                        Date(2022,8, 20,15,3),
                        false,
                        listOf()),
                    Messages(
                        "Ada Cafe baru kemrin aku liat, kyknya enak tempatnya",
                        2022,
                        Date(2022,8, 20,15,3),
                        false,
                        listOf()),
                    Messages(
                        "Okey.. Jam berapa ?",
                        2918310239,
                        Date(2022,8, 20,15,3),
                        false,
                        listOf()),
                    Messages(
                        "Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint. Velit officia consequat duis enim velit mollit. Exercitation veniam consequat sunt nostrud amet.",
                        2022,
                        Date(2022,8, 20,15,3),
                        false,
                        listOf()),
                    Messages(
                        "P",
                        2918310239,
                        Date(2022,8, 20,15,3),
                        false,
                        listOf()),
                    Messages(
                        "P",
                        2022,
                        Date(2022,8, 20,15,3),
                        false,
                        listOf()),
                    Messages(
                        "P",
                        2918310239,
                        Date(2022,8, 20,15,3),
                        false,
                        listOf()),
                    Messages(
                        "P",
                        2022,
                        Date(2022,8, 20,15,3),
                        false,
                        listOf()),
                )
            ),
            chat_model(
                "Sayang 2",
                1,
                listOf(
                    Messages(
                        "Semangat kerjanya :-*",
                        2918310239,
                        Date(2022,8, 20,10,1),
                        false,
                        listOf())
                )
            ),
            chat_model(
                "Sayang 3",
                1,
                listOf(
                    Messages(
                        "Semangat kerjanya :-*",
                        2918310239,
                        Date(2022,8, 20,9,1),
                        false,
                        listOf())
                )
            ),
            chat_model(
                "Sayang 4",
                1,
                listOf(
                    Messages(
                        "Semangat kerjanya :-*",
                        2918310239,
                        Date(2022,8, 20,11,1),
                        false,
                        listOf())
                )
            ),
            chat_model(
                "Sayang 5",
                1,
                listOf(
                    Messages(
                        "Semangat kerjanya :-*",
                        2918310239,
                        Date(2022,8, 20,12,1),
                        false,
                        listOf())
                )
            ),
            chat_model(
                "Sayang 6",
                1,
                listOf(
                    Messages(
                        "Semangat kerjanya :-*",
                        2918310239,
                        Date(2022,8, 20,13,1),
                        false,
                        listOf())
                )
            )
        );

        return list;
    }
    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
//        val toolbar = itemView.findViewById<MaterialToolbar>(R.id.mainToolbar) as MaterialToolbar
//        toolbar.setTitle("Lamaran Saya")

        val recyclerView = itemView.findViewById<RecyclerView>(R.id.recyclerView) as RecyclerView;
        val Context = this;
        if(isCompany) {
            itemView.findViewById<EditText>(R.id.searchInput).hint= "Cari Lowongan"
            InterviewAPI().CompanyGetInterviewList {
                Log.d("Response", it.toString())
                if(it!=null) {
                    isLoading = false
                    recyclerView.apply {
                        layoutManager = LinearLayoutManager(activity)
                        adapter = company_interview_adapter(it.data, Context)
                    }
                }
            }
        }
        else{
            recyclerView.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = jobseeker_interview_adapter(getData(), Context)
            }
        }
    }
    override fun onCellClickListener(data: chat_model) {
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.replace(id, ChatPage(data), "ChatFragment")
        ft.addToBackStack("ChatFragment")
        ft.commit()
    }

    override fun companyInterviewClick() {
//        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
//        ft.replace(id, ChatPage(data), "ChatFragment")
//        ft.addToBackStack("ChatFragment")
//        ft.commit()
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
    fun onCellClickListener(data : chat_model)
    fun companyInterviewClick()
}