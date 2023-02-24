package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.SearchMoreCompany

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ciptakerjaarunika.kerjaloka.api.Search_Api
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentSearchMoreCompanyBinding
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.SearchMoreCompany.Adapter.SearchMoreCompanyAdapter
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.SearchScreen.Model.companyList

class SearchMoreCompanyFragment(var query: String) : Fragment(), OnFragmentClickListener {
    private lateinit var binding: FragmentSearchMoreCompanyBinding
    private var isLoading: Boolean = true
    private var list: List<companyList>? = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchMoreCompanyBinding.inflate(layoutInflater)
        val view = binding.root

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
        if (query.isNotEmpty() == true) {
            Search_Api().getGeneralSearchAsync(context, query) {
                binding.spinner.visibility = View.GONE
                if (it != null) {
                    isLoading = false
                    list = it.data.companyList
                    binding.rvMoreCompany.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter = SearchMoreCompanyAdapter(
                            list!!,
                            context,
                            this@SearchMoreCompanyFragment
                        )
                    }

                }
            }

        }


        return view
    }

    fun replaceFragment(fragment: Fragment) {
        val fragmentManager = parentFragmentManager
        val ft = fragmentManager.beginTransaction()
        ft.replace(id, fragment)
        ft.addToBackStack("")
        ft.commit()
    }


    override fun onCompanyDetailPage(companyNo: Long) {

    }

    override fun BookmarkJob(jobNo: Long, Index: Int) {

    }


}

interface OnFragmentClickListener {
    fun onCompanyDetailPage(companyNo: Long)
    fun BookmarkJob(jobNo: Long, Index: Int)
}