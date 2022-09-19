package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ListApplicant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.databinding.ActivityMainBinding
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.JobApplicant.JobApplicantFragment
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ListApplicant.Adapter.ListApplicantAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ListApplicant.Model.listApplicantJobModel

class CompanyListApplicantFragment : Fragment(), OnFragmentClickListener {


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_company_list_applicant, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val list = ArrayList<listApplicantJobModel>()
        val listJob1 = listApplicantJobModel(
            1,
            "Business Development Representative",
        "26 Mei 2022 pada 15:23",
            true,
        )
        val listJob2 = listApplicantJobModel(
            2,
            "Territory Manager",
            "26 Mei 2022 pada 15:23",
            true,
        )
        val listJob3 = listApplicantJobModel(
            3,
            "Industry Representative",
            "26 Mei 2022 pada 15:23",
            true,
        )
        val listJob4 = listApplicantJobModel(
            4,
            "Executive Vice President of Sales",
            "26 Mei 2022 pada 15:23",
            true,
        )
        val listJob5 = listApplicantJobModel(
            5,
            "Director of National Sales",
            "26 Mei 2022 pada 15:23",
            false,
        )
        list.add(listJob1)
        list.add(listJob2)
        list.add(listJob3)
        list.add(listJob4)
        list.add(listJob5)

        val rv_applicantJob = view.findViewById<RecyclerView>(R.id.rv_list_applicant_job)
        rv_applicantJob.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = ListApplicantAdapter(list, this@CompanyListApplicantFragment)
        }
    }

    override fun goToListJobApplicant() {
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.replace(id, JobApplicantFragment(), "CompanyApplicant")
        ft.addToBackStack("CompanyApplicant")
        ft.commit()
    }
}

interface OnFragmentClickListener {
    fun goToListJobApplicant()
}