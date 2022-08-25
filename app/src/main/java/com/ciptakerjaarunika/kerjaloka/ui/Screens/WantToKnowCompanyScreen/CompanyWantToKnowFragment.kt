package com.ciptakerjaarunika.kerjaloka.ui.Screens.WantToKnowCompanyScreen

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
import com.ciptakerjaarunika.kerjaloka.ui.Screens.WantToKnowCompanyScreen.Adapter.CompanyWantToKnowAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.WantToKnowCompanyScreen.Model.companyWantToKnowModel
import com.google.android.material.appbar.MaterialToolbar


class CompanyWantToKnowFragment : Fragment() {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<CompanyWantToKnowAdapter.ViewHolder>? = null
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
        return inflater.inflate(R.layout.fragment_company_want_to_know, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar) as MaterialToolbar

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        val list = ArrayList<companyWantToKnowModel>()
        val companyJob1 = companyWantToKnowModel(
            1,
            "Software Engineer",
            "PT. KerjaLoka",
            "Jakarta",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png"
        )
        val companyJob2 = companyWantToKnowModel(
            2,
            "Software Engineer",
            "PT. KerjaLoka",
            "Jakarta",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png"
        )
        val companyJob3 = companyWantToKnowModel(
            3,
            "Software Engineer",
            "PT. KerjaLoka",
            "Jakarta",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png"
        )
        val companyJob4 = companyWantToKnowModel(
            4,
            "Software Engineer",
            "PT. KerjaLoka",
            "Jakarta",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png"
        )
        val companyJob5 = companyWantToKnowModel(
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

        val rv_WantToKnowCompany = view.findViewById<RecyclerView>(R.id.rv_browse_company_job)
        layoutManager = LinearLayoutManager(activity)
        rv_WantToKnowCompany.layoutManager = layoutManager
        adapter = CompanyWantToKnowAdapter(list)
        rv_WantToKnowCompany.adapter = adapter

    }

    companion object {

        fun newInstance(param1: String, param2: String) =
            CompanyWantToKnowFragment().apply {

            }
    }
}