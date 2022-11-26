package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyFollowedScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.CompanyFollowedAPI
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentFollowedCompanyBinding
import com.ciptakerjaarunika.kerjaloka.model.CompanyPage.company_followed_list
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyDetail.CompanyDetailFragment
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyScreen.Adapter.CompanyFollowedAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyScreen.OnFragmentClickListener


class CompanyFollowedFragment : Fragment(), OnFragmentClickListener {
    private lateinit var binding: FragmentFollowedCompanyBinding
    private var listFollowedJob: List<company_followed_list>? = null
    private var isLoading: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowedCompanyBinding.inflate(layoutInflater)
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

        getFollowedJobData()
    }

    fun getFollowedJobData() {
        CompanyFollowedAPI().CompanyGetFollowedJob(context) {
            binding.spinner.visibility = View.GONE
            if (it != null) {
                isLoading = false
                listFollowedJob = it.data
                binding.rvFollowedCompanyJob.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter =
                        CompanyFollowedAdapter(
                            context,
                            listFollowedJob!!,
                            this@CompanyFollowedFragment
                        )
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