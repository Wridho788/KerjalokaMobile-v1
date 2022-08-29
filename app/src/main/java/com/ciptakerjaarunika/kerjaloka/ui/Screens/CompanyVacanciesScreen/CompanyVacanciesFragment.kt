package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyVacanciesScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.databinding.ActivityMainBinding
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyVacanciesScreen.Adapter.CompanyVacanciesAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyVacanciesScreen.Model.companyVacanciesModel
import com.google.android.material.appbar.MaterialToolbar


class CompanyVacanciesFragment : Fragment() {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<CompanyVacanciesAdapter.ViewHolder>? = null
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_company_vacancies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar) as MaterialToolbar

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        val list = ArrayList<companyVacanciesModel>()
        val companyJob1 = companyVacanciesModel(
            1,
            "Software Engineer",
            "PT. KerjaLoka",
            "Jakarta",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png"
        )
        val companyJob2 = companyVacanciesModel(
            2,
            "Software Engineer",
            "PT. KerjaLoka",
            "Jakarta",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png"
        )
        val companyJob3 = companyVacanciesModel(
            3,
            "Software Engineer",
            "PT. KerjaLoka",
            "Jakarta",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png"
        )
        val companyJob4 = companyVacanciesModel(
            4,
            "Software Engineer",
            "PT. KerjaLoka",
            "Jakarta",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png"
        )
        val companyJob5 = companyVacanciesModel(
            5,
            "Software Engineer",
            "PT. KerjaLoka",
            "Jakarta",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png"
        )
        list.add(companyJob1)
        list.add(companyJob2)
        list.add(companyJob3)
        list.add(companyJob4)
        list.add(companyJob5)

        val rv_companyVacancies = view.findViewById<RecyclerView>(R.id.rv_vacancies_company_job)
        layoutManager = LinearLayoutManager(activity)
        rv_companyVacancies.layoutManager = layoutManager
        adapter = CompanyVacanciesAdapter(list)
        rv_companyVacancies.adapter = adapter

    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CompanyVacanciesFragment().apply {

            }
    }
}