package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyScreen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.CompanyBrowseAPI
import com.ciptakerjaarunika.kerjaloka.api.CompanyFollowedAPI
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentCompanyPageBinding
import com.ciptakerjaarunika.kerjaloka.model.CompanyPage.company_browse_list
import com.ciptakerjaarunika.kerjaloka.model.CompanyPage.company_followed_list
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.HomePage.HomePage
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ManageCV.fragment_manage_cv_edit_education_page
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyDetail.CompanyDetailFragment
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyScreen.Adapter.CompanyBrowseAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyScreen.Adapter.CompanyFollowedAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyScreen.Adapter.CompanyVacanciesAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.CompanySearchActivity
import com.google.android.material.appbar.MaterialToolbar

class CompanyPage : Fragment(), OnFragmentClickListener{
    private lateinit var binding : FragmentCompanyPageBinding
    private var isLoading: Boolean = true
    private var isFollowed: Boolean = true
    private val Context = this
    private var listFollowedJob : List<company_followed_list>?= null
    private var listSearchJob : List<company_browse_list>?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun getFollowedJobData() {
        CompanyFollowedAPI().CompanyGetFollowedJob(context) {
            view?.findViewById<LinearLayout>(R.id.spinnerFollowed)?.visibility = GONE

            if (it != null) {
                isLoading = false
                listFollowedJob = it.data
                val recyclerViewFollowedCompany =
                    view?.findViewById<RecyclerView>(R.id.rv_followed_company)
                val btn_see_more_job = view?.findViewById<LinearLayout>(R.id.btn_see_more_followed)

                if (it != null && it.data.size > 5) {
                    btn_see_more_job?.visibility = VISIBLE
                }
//                if (listFollowedJob?.size == 1) {
//                    btn_see_more_job?.visibility = View.GONE
//                }
                recyclerViewFollowedCompany?.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter =
                        CompanyFollowedAdapter(context, listFollowedJob!!.take(5), this@CompanyPage);
                }

            }
        }
    }

    private fun getBrowserJobData() {
        CompanyBrowseAPI().CompanyGetBrowserJob(context){
            view?.findViewById<LinearLayout>(R.id.spinnerBrowse)?.visibility = GONE
            if(it != null){
                isLoading = false
                listSearchJob = it.data
                Log.d("response browse api", it.toString())
                val recyclerViewCompanyBrowse =
                    view?.findViewById<RecyclerView>(R.id.rv_browse_company)
                val btn_see_more = view?.findViewById<LinearLayout>(R.id.btn_see_more_browse)
                if (it != null && it.data.size > 5) {
                    btn_see_more?.visibility = VISIBLE
                }
//                if (listFollowedJob?.size == 0 && listFollowedJob?.size == 1) {
//                    btn_see_more?.visibility = View.GONE
//                }
                recyclerViewCompanyBrowse?.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = CompanyBrowseAdapter(context, listSearchJob!!.take(5), this@CompanyPage)
                }
            }
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCompanyPageBinding.inflate(layoutInflater)
        return  binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backButton.setOnClickListener {
            fragmentManager?.popBackStack()
        }
//        activity?.onBackPressedDispatcher?.addCallback(this) {
//            fragmentManager?.popBackStack()
//        }
        var user = SessionManager(context).user
        val layout_followed_company = view.findViewById<LinearLayout>(R.id.layout_followed_company)
        val layout_search_company = view.findViewById<LinearLayout>(R.id.search_company_btn)
        val recyclerViewVacanciesCompany =
            view.findViewById<RecyclerView>(R.id.rv_vacancies_company)

        if (user == null) {
            layout_followed_company.visibility = View.GONE
        }
        isFollowed = user != null && user.roleNo == 4
        if (isFollowed) {
            getFollowedJobData()
        }
        getBrowserJobData()

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)


        layout_search_company.setOnClickListener{
            val intent = Intent(activity, CompanySearchActivity::class.java)
            startActivity(intent)
        }

        CompanyBrowseAPI().CompanyActiveHire(context){
            view?.findViewById<LinearLayout>(R.id.spinnerVacancies)?.visibility = GONE
            if(it != null){
                val recyclerView = view?.findViewById<RecyclerView>(R.id.rv_vacancies_company)
                val btn_see_more = view?.findViewById<LinearLayout>(R.id.btn_see_more)
                if (it != null && it.data.size > 5) {
                    btn_see_more?.visibility = VISIBLE
                }
                recyclerView?.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = CompanyVacanciesAdapter(context, it.data.take(5), this@CompanyPage)
                }
            }
        }

    }
    private fun back(){
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction?.replace(id, HomePage(), "Home Page")
        fragmentTransaction?.commit()
    }
    override fun onCompanyDetailPage(CompanyNo: Long){
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_container, CompanyDetailFragment(CompanyNo))
        ft.addToBackStack("companyPage")
        ft.commit()
    }
}

open interface OnFragmentClickListener {
    fun onCompanyDetailPage(CompanyNo: Long)
}