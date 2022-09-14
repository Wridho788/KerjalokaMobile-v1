package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.JobApplicant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.databinding.ActivityMainBinding
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.JobApplicant.Adapter.ApplicantAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.JobApplicant.Model.applicantModel

class JobApplicantFragment : Fragment() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_job_applicant, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val list = ArrayList<applicantModel>()
        val listApplicant1 = applicantModel(
            1,
            "Jhon doe",
            "Palembang, Sumatera Selatan",
            "https://www.dmarge.com/wp-content/uploads/2021/01/dwayne-the-rock-.jpg",
            true
        )
        val listApplicant2 = applicantModel(
            2,
            "Jhon doe",
            "Palembang, Sumatera Selatan",
            "https://www.dmarge.com/wp-content/uploads/2021/01/dwayne-the-rock-.jpg",
            true
        )
        val listApplicant3 = applicantModel(
            3,
            "Jhon doe",
            "Palembang, Sumatera Selatan",
            "https://www.dmarge.com/wp-content/uploads/2021/01/dwayne-the-rock-.jpg",
            true
        )
        val listApplicant4 = applicantModel(
            4,
            "Jhon doe",
            "Palembang, Sumatera Selatan",
            "https://www.dmarge.com/wp-content/uploads/2021/01/dwayne-the-rock-.jpg",
            true
        )
        val listApplicant5 = applicantModel(
            5,
            "Jhon doe",
            "Palembang, Sumatera Selatan",
            "https://www.dmarge.com/wp-content/uploads/2021/01/dwayne-the-rock-.jpg",
            true
        )
        list.add(listApplicant1)
        list.add(listApplicant2)
        list.add(listApplicant3)
        list.add(listApplicant4)
        list.add(listApplicant5)

        val rv_applicant = view.findViewById<RecyclerView>(R.id.rv_list_applicant)
        rv_applicant.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = ApplicantAdapter(list)
        }

    }
}