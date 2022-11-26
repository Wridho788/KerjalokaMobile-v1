package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyVacanciesScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.CompanyBrowseAPI
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentCompanyVacanciesBinding
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyDetail.CompanyDetailFragment
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyScreen.Adapter.CompanyVacanciesAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyScreen.OnFragmentClickListener


class CompanyVacanciesFragment : Fragment(), OnFragmentClickListener {
    private lateinit var binding: FragmentCompanyVacanciesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentCompanyVacanciesBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCompanyVacanciesBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        CompanyBrowseAPI().CompanyActiveHire(context) {
            binding.spinner.visibility = View.GONE
            if (it != null) {
                binding.rvVacanciesCompanyJob.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter =
                        CompanyVacanciesAdapter(context, it.data, this@CompanyVacanciesFragment)
                }

            }
        }

    }

    override fun onCompanyDetailPage(CompanyNo: Long) {
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_container, CompanyDetailFragment(CompanyNo))
        ft.addToBackStack("companyPage")
        ft.commit()
    }

}

open interface OnFragmentClickListener {
    fun onCompanyDetailPage(CompanyNo: Long)
}