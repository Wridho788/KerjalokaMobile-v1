package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyScreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.CompanyBrowseAPI
import com.ciptakerjaarunika.kerjaloka.api.CompanyFollowedAPI
import com.ciptakerjaarunika.kerjaloka.databinding.ActivityMainBinding
import com.ciptakerjaarunika.kerjaloka.model.CompanyPage.company_browse_list
import com.ciptakerjaarunika.kerjaloka.model.CompanyPage.company_followed_list
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyDetailScreen.CompanyDetailFragment
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyScreen.Adapter.CompanyBrowseAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyScreen.Adapter.CompanyFollowedAdapter
import com.google.android.material.appbar.MaterialToolbar

class CompanyPage : Fragment(), OnFragmentClickListener{
    private var isLoading: Boolean = true
    private var isFollowed: Boolean = true
    private lateinit var binding: ActivityMainBinding
    private val Context = this
    private var listFollowedJob : List<company_followed_list>?= null
    private var listSearchJob : List<company_browse_list>?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
    }

    private fun getFollowedJobData() {
        CompanyFollowedAPI().CompanyGetFollowedJob(context) {
            if (it != null) {
                isLoading = false
                listFollowedJob = it.data
                Log.d("response followed api", it.toString())
                val recyclerViewFollowedCompany =
                    view?.findViewById<RecyclerView>(R.id.rv_followed_company)
                val btn_see_more_job = view?.findViewById<LinearLayout>(R.id.btn_see_more_followed)

                if (listFollowedJob?.size == 0) {
                    btn_see_more_job?.visibility = View.GONE
                }
                if (listFollowedJob?.size == 1) {
                    btn_see_more_job?.visibility = View.GONE
                }
                recyclerViewFollowedCompany?.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter =
                        CompanyFollowedAdapter(context, listFollowedJob!!, this@CompanyPage);
                }

            }
        }
    }

    private fun getBrowserJobData() {
        CompanyBrowseAPI().CompanyGetBrowserJob(context){
            Log.d("response", it.toString())
            if(it != null){
                isLoading = false
                listSearchJob = it.data
                Log.d("response browse api", it.toString())
                val recyclerViewCompanyBrowse =
                    view?.findViewById<RecyclerView>(R.id.rv_browse_company)
                val btn_see_more = view?.findViewById<LinearLayout>(R.id.btn_see_more_browse)
                if (listFollowedJob?.size == 0) {
                    btn_see_more?.visibility = View.GONE
                }
                if (listFollowedJob?.size == 0 && listFollowedJob?.size == 1) {
                    btn_see_more?.visibility = View.GONE
                }
                recyclerViewCompanyBrowse?.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = CompanyBrowseAdapter(context, listSearchJob!!, this@CompanyPage)
                }
            }
        }
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
        var user = SessionManager(context).user
        Log.d("response token", user.toString())

        val layout_followed_company = view.findViewById<LinearLayout>(R.id.layout_followed_company)

        if (user == null) {
            layout_followed_company.visibility = View.GONE
        }
        isFollowed = user != null && user.roleNo == 4
//        println(user)
//        println(isFollowed)

        if (isFollowed) {
            getFollowedJobData()
        }
        getBrowserJobData()
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        val recyclerViewVacanciesCompany =
            view.findViewById<RecyclerView>(R.id.rv_vacancies_company)

    }

    override fun onCompanyDetailPage(CompanyNo: Long){
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.replace(id, CompanyDetailFragment(CompanyNo), "company detail page")
        ft.addToBackStack("CompanyPage")
        ft.commit()
    }
}

interface OnFragmentClickListener {
    fun onCompanyDetailPage(CompanyNo: Long)
}