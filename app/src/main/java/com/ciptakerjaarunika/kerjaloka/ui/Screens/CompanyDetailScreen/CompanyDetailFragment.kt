package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyDetailScreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.CompanyDetailAPI
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.databinding.ActivityMainBinding
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyDetailScreen.Adapter.RelatedCompanyJobAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobDetailScreen.JobDetailFragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton

class CompanyDetailFragment(private val CompanyNo: Long) : Fragment(), OnFragmentCompanyDetailListener {

//    private var companyDetailList : List<company_detail_list>? = null

//    private var layoutManager: RecyclerView.LayoutManager? = null
//    private var layoutManager2: RecyclerView.LayoutManager? = null
//
//    private var adapterRelatedJob: RecyclerView.Adapter<RelatedCompanyJobAdapter.ViewHolder>? = null
//    private var adapterOtherCompanyJob: RecyclerView.Adapter<RelatedOtherCompanyJobAdapter.ViewHolder>? =
//        null

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
        val view = inflater.inflate(R.layout.fragment_company_detail, container, false)
        val btn_follow = view.findViewById<MaterialButton>(R.id.follow_button)
        val btn_review = view.findViewById<MaterialButton>(R.id.review_button)

        val company_logo = view.findViewById<ImageView>(R.id.logo_company)
        val txt_follower = view.findViewById<TextView>(R.id.txt_follower)
        val txt_rating_company = view.findViewById<TextView>(R.id.txt_rating_company)
        val company_name = view.findViewById<TextView>(R.id.company_name)
        val company_type = view.findViewById<TextView>(R.id.company_type)
        val company_location = view.findViewById<TextView>(R.id.company_location)
        val company_about = view.findViewById<TextView>(R.id.company_about)
        val company_workers = view.findViewById<TextView>(R.id.company_worker)
        val company_phone = view.findViewById<TextView>(R.id.company_phone)

        btn_follow.setOnClickListener {
            Toast.makeText(activity, "follow", Toast.LENGTH_SHORT).show()
        }
        btn_review.setOnClickListener {
            Toast.makeText(activity, "review", Toast.LENGTH_SHORT).show()
        }

        val rv_recommendations_job =
            view.findViewById<RecyclerView>(R.id.recycler_view_company_recommendation_jobs)

        val Context = this
        CompanyDetailAPI().getCompanyDetailAsync(context, CompanyNo) {
            if (it != null) {
               Log.d("response company detail", it.toString())
                company_name.text = it.data.companyName
                company_phone.text = it.data.phone
                Glide.with(this)
                    .load(config().portAddress + "/photo/Profile/" + it.data.logo)
                    .fitCenter().into(company_logo)
                company_about.text = it.data.companyDescription
                company_location.text = it.data.companyAddress
                company_workers.text = it.data.size
                company_type.text = it.data.field
                txt_rating_company.text = it.data.rating.ratingValue.toString()

                rv_recommendations_job.apply {
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    adapter = RelatedCompanyJobAdapter(it.data.job)
                }

            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar) as MaterialToolbar
        val toolbarShare = view.findViewById<ImageView>(R.id.toolbar_share) as ImageView
        Log.d("company No ${CompanyNo}", CompanyNo.toString())


        val rv_related_job =
            view.findViewById<RecyclerView>(R.id.recycler_view_company_other_job)

//        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//        layoutManager2 = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//
//        rv_recommendations_job.layoutManager = layoutManager
//        rv_related_job.layoutManager = layoutManager2
//
//        adapterRelatedJob = RelatedCompanyJobAdapter()
//        adapterOtherCompanyJob = RelatedOtherCompanyJobAdapter()
//
//        rv_recommendations_job.adapter = adapterOtherCompanyJob
//        rv_related_job.adapter = adapterRelatedJob


        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        toolbarShare.setOnClickListener {
            Toast.makeText(context, "Share", Toast.LENGTH_SHORT).show()
        }

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onRelatedJobFragment(CompanyNo: Long){
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.replace(id, CompanyDetailFragment(CompanyNo), "CompanyDetailFragment")
        ft.addToBackStack("CompanyDetailFragment")
        ft.commit()
    }

    companion object
}

interface OnFragmentCompanyDetailListener {
    fun onRelatedJobFragment(CompanyNo: Long)
}