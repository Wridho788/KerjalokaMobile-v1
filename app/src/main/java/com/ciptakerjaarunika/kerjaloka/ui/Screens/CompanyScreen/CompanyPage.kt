package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.databinding.ActivityMainBinding
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyScreen.Adapter.CompanyFollowedAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyScreen.Adapter.CompanyVacanciesAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyScreen.Adapter.CompanyWantToKnowAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyScreen.Model.companyModel
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyScreen.Model.companyVacanciesModel
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyScreen.Model.companyWantToKnowModel
import com.google.android.material.appbar.MaterialToolbar

class CompanyPage : Fragment() {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var layoutManager2: RecyclerView.LayoutManager? = null
    private var layoutManager3: RecyclerView.LayoutManager? = null

    private var adapter: RecyclerView.Adapter<CompanyFollowedAdapter.ViewHolder>? = null
    private var adapter2: RecyclerView.Adapter<CompanyWantToKnowAdapter.ViewHolder>? = null
    private var adapter3: RecyclerView.Adapter<CompanyVacanciesAdapter.ViewHolder>? = null

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
    }

    private fun setContentView(root: ConstraintLayout) {
        TODO("Not yet implemented")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_company_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar) as MaterialToolbar

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        val list = ArrayList<companyModel>()
        val companyJob1 = companyModel(
            1,
            "Software Engineer",
            "PT. KerjaLoka",
            "Jakarta",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png"
        )
        val companyJob2 = companyModel(
            2,
            "Software Engineer",
            "PT. KerjaLoka",
            "Jakarta",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png"
        )
        val companyJob3 = companyModel(
            3,
            "Software Engineer",
            "PT. KerjaLoka",
            "Jakarta",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png"
        )
        val companyJob4 = companyModel(
            4,
            "Software Engineer",
            "PT. KerjaLoka",
            "Jakarta",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png"
        )
        val companyJob5 = companyModel(
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

        val list_want_to_know_company = ArrayList<companyWantToKnowModel>()
        val companyJob_1 = companyWantToKnowModel(
            1,
            "Software Engineer",
            "PT. KerjaLoka",
            "Jakarta",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png"
        )
        val companyJob_2 = companyWantToKnowModel(
            2,
            "Software Engineer",
            "PT. KerjaLoka",
            "Jakarta",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png"
        )
        val companyJob_3 = companyWantToKnowModel(
            3,
            "Software Engineer",
            "PT. KerjaLoka",
            "Jakarta",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png"
        )
        val companyJob_4 = companyWantToKnowModel(
            4,
            "Software Engineer",
            "PT. KerjaLoka",
            "Jakarta",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png"
        )
        val companyJob_5 = companyWantToKnowModel(
            5,
            "Software Engineer",
            "PT. KerjaLoka",
            "Jakarta",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png"
        )
        list_want_to_know_company.add(companyJob_1)
        list_want_to_know_company.add(companyJob_2)
        list_want_to_know_company.add(companyJob_3)
        list_want_to_know_company.add(companyJob_4)
        list_want_to_know_company.add(companyJob_5)

        val list_vancancies_company = ArrayList<companyVacanciesModel>()
        val companyVacancies_1 = companyVacanciesModel(
            1,
            "Software Engineer",
            "PT. KerjaLoka",
            "Jakarta",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png"
        )
        val companyVacancies_2 = companyVacanciesModel(
            2,
            "Software Engineer",
            "PT. KerjaLoka",
            "Jakarta",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png"
        )
        val companyVacancies_3 = companyVacanciesModel(
            3,
            "Software Engineer",
            "PT. KerjaLoka",
            "Jakarta",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png"
        )
        val companyVacancies_4 = companyVacanciesModel(
            4,
            "Software Engineer",
            "PT. KerjaLoka",
            "Jakarta",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png"
        )
        val companyVacancies_5 = companyVacanciesModel(
            5,
            "Software Engineer",
            "PT. KerjaLoka",
            "Jakarta",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png"
        )
        list_vancancies_company.add(companyVacancies_1)
        list_vancancies_company.add(companyVacancies_2)
        list_vancancies_company.add(companyVacancies_3)
        list_vancancies_company.add(companyVacancies_4)
        list_vancancies_company.add(companyVacancies_5)

        val recyclerViewFollowedCompany = view.findViewById<RecyclerView>(R.id.rv_followed_company)
        val recyclerViewWantToKnoewCompany = view.findViewById<RecyclerView>(R.id.rv_want_to_know_company)
        val recyclerViewVacanciesCompany = view.findViewById<RecyclerView>(R.id.rv_vacancies_company)

        layoutManager = LinearLayoutManager(activity)
        layoutManager2 = LinearLayoutManager(activity)
        layoutManager3 = LinearLayoutManager(activity)

        recyclerViewFollowedCompany.layoutManager = layoutManager
        recyclerViewWantToKnoewCompany.layoutManager = layoutManager2
        recyclerViewVacanciesCompany.layoutManager = layoutManager3

        adapter = CompanyFollowedAdapter(list)
        adapter2 = CompanyWantToKnowAdapter(list_want_to_know_company)
        adapter3 = CompanyVacanciesAdapter(list_vancancies_company)

        recyclerViewFollowedCompany.adapter = adapter
        recyclerViewWantToKnoewCompany.adapter = adapter2
        recyclerViewVacanciesCompany.adapter = adapter3
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CompanyPage().apply {

            }
    }
}