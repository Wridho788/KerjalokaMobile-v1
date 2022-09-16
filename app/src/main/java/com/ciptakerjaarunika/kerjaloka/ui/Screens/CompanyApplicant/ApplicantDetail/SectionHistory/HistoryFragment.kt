package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail.SectionHistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.databinding.ActivityMainBinding
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail.SectionHistory.Adapter.HistoryAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail.SectionHistory.Model.HistoryModel

class HistoryFragment : Fragment() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val list = ArrayList<HistoryModel>()
        val list1 = HistoryModel(
            1,
            "Leannon, Ruecker and Hilll",
            "Interview",
            "12 Agustus 2021 pada 16:57",

        )
        val list2 = HistoryModel(
            2,
            "Leannon, Ruecker and Hilll",
            "dalam tes",
            "12 Agustus 2021 pada 16:57"
        )
        val list3 = HistoryModel(
            3,
            "Leannon, Ruecker and Hilll",
            "Interview",
            "12 Agustus 2021 pada 16:57"
        )
        val list4 = HistoryModel(
            4,
            "Leannon, Ruecker and Hilll",
            "Interview",
            "12 Agustus 2021 pada 16:57"
        )
        val list5 = HistoryModel(
            5,
            "Leannon, Ruecker and Hilll",
            "Interview",
            "12 Agustus 2021 pada 16:57"
        )

        list.add(list1)
        list.add(list2)
        list.add(list3)
        list.add(list4)
        list.add(list5)

        val toolbar = view.findViewById<ImageView>(R.id.btn_back_history)
        toolbar.setOnClickListener {
            activity?.onBackPressed()
        }
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)

        val rv_history = view.findViewById<RecyclerView>(R.id.rv_history_applicant)
        rv_history.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = HistoryAdapter(list)
        }
    }
}