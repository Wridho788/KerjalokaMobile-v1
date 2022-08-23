package com.ciptakerjaarunika.kerjaloka.ui.InterviewPage.ChatPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.model.Interview.chat_model


class ChatPage(val section: chat_model) : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        val titlePage = itemView.findViewById<TextView>(R.id.title)
        val backButton = itemView.findViewById<ImageButton>(R.id.backButton)
        backButton.setOnClickListener{
            parentFragmentManager.popBackStack()
        }


        titlePage.text = section.SectionName
        var LinearLayoutManager = LinearLayoutManager(activity)
        val recyclerView = itemView.findViewById<RecyclerView>(R.id.recyclerView) as RecyclerView;
        recyclerView.scrollToPosition(section.Messages.size-1)
        recyclerView.apply {
            layoutManager = LinearLayoutManager
            adapter = chat_adapter(section.Messages)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.chat_page, container, false)
    }
}