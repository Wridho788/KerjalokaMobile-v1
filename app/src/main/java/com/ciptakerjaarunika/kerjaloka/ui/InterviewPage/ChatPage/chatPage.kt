package com.ciptakerjaarunika.kerjaloka.ui.InterviewPage.ChatPage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.InterviewPage.InterviewPage
import com.ciptakerjaarunika.kerjaloka.ui.InterviewPage.Messages
import com.ciptakerjaarunika.kerjaloka.ui.InterviewPage.chat_model
import com.ciptakerjaarunika.kerjaloka.ui.InterviewPage.interview_adapter
import com.google.android.material.appbar.MaterialToolbar
import java.nio.file.Files.size

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LamaranPage.newInstance] factory method to
 * create an instance of this fragment.
 */
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.chat_page, container, false)
    }
}