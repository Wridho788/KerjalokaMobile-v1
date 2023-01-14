package com.ciptakerjaarunika.kerjaloka.viewmodel.Company.CompanyApplicant.ApplicantDetail.SectionHistory

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
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.CompanyApplicant.ApplicantDetail.SectionHistory.Adapter.HistoryAdapter
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.CompanyApplicant.JobApplicant.Model.jobApplicantHistory

class HistoryFragment(private val jobApplicationHistory: List<List<jobApplicantHistory>>) : Fragment() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val toolbar = view.findViewById<ImageView>(R.id.btn_back_history)
        toolbar.setOnClickListener {
            activity?.onBackPressed()
        }
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)

        val rv_history = view.findViewById<RecyclerView>(R.id.rv_history_applicant)

        rv_history.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = HistoryAdapter(jobApplicationHistory)
        }
    }
}