package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyDetail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.CompanyBrowseAPI
import com.ciptakerjaarunika.kerjaloka.api.CompanyDetailAPI
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentCompanyDetailBinding
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyDetail.Adapter.OtherCompanyAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyDetail.Adapter.RelatedCompanyJobAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyReview.CompanyReviewFragment
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyScreen.OnFragmentClickListener
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobDetailScreen.JobDetailFragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton

class CompanyDetailFragment(private val CompanyNo: Long) : Fragment(),
    OnFragmentCompanyDetailListener, OnFragmentClickListener {
    private lateinit var binding: FragmentCompanyDetailBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCompanyDetailBinding.inflate(layoutInflater)
        val view = binding.root


        val btn_follow = view.findViewById<MaterialButton>(R.id.follow_button)
        val btn_review = view.findViewById<MaterialButton>(R.id.review_button)
        val company_logo = view.findViewById<ImageView>(R.id.logo_company)
        val txt_rating_company = view.findViewById<TextView>(R.id.txt_rating_company)
        val txt_follower = view.findViewById<TextView>(R.id.txt_follower)
        val company_name = view.findViewById<TextView>(R.id.company_name)
        val company_type = view.findViewById<TextView>(R.id.company_type)
        val company_location = view.findViewById<TextView>(R.id.company_location)
        val company_about = view.findViewById<TextView>(R.id.company_about)
        val company_workers = view.findViewById<TextView>(R.id.company_worker)
        val company_phone = view.findViewById<TextView>(R.id.company_phone)
        val toolbarShare = view.findViewById<ImageView>(R.id.toolbar_share)

        if(SessionManager(context).user == null){
            btn_follow.visibility = GONE
        }

        val rv_recommendations_job =
            view.findViewById<RecyclerView>(R.id.recycler_view_company_recommendation_jobs)

        val Context = this

        CompanyDetailAPI().getCompanyDetailAsync(context, CompanyNo) {it->
            binding.spinner.visibility = GONE
            binding.contentContainer.visibility = VISIBLE
            if (it != null) {
                if(it.data.followed){
                    binding.followButton.text = "Batal Ikuti"
                }
                else{
                    binding.followButton.text = "Ikuti"
                }
                btn_follow.setOnClickListener { btn->
                    CompanyDetailAPI().ManageFollowCompany(it.data.followed, CompanyNo, context){ res->
                        if(res != null && (res.code == "210" || res.code == 210)){
                            Toast.makeText(activity, res.message, Toast.LENGTH_SHORT).show()

                            it.data.followed = !it.data.followed
                            if(it.data.followed){
                                binding.followButton.text = "Batal Ikuti"
                            }
                            else{
                                binding.followButton.text = "Ikuti"
                            }
                        }
                    }
                }


                company_name.text = it.data.companyName
                company_phone.text = it.data.phone
                Glide.with(this)
                    .load(config().portAddress + "/photo/Profile/" + it.data.logo)
                    .fitCenter().into(company_logo)
                company_about.text = it.data.companyDescription
                company_location.text = "${it.data.location.city}, ${it.data.location.province}"
                company_workers.text = it.data.size
                company_type.text = it.data.field

                txt_rating_company.text = if(it.data.rating.ratingList.size == 0) "-" else  it.data.rating.ratingValue.toString()
                txt_follower.text = it.data.followers.toString()
                rv_recommendations_job.apply {
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    adapter = RelatedCompanyJobAdapter(it.data.job, Context)
                }
                val title = it.data.companyName
                val link = it.data.link
                toolbarShare.setOnClickListener {
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TITLE, title)
//                        putExtra(Intent.EXTRA_TEXT, link)
                        type = "text/plain"
                    }

                    val shareIntent = Intent.createChooser(sendIntent, null)
                    startActivity(shareIntent)
                }
            }

        }

        btn_review.setOnClickListener{
            goToCompanyReview(CompanyNo)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)
        var recyclerView = view.findViewById(R.id.recycler_view_company_other_job) as RecyclerView

        binding.toolbar.setNavigationOnClickListener {
            fragmentManager?.popBackStack()
        }
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            fragmentManager?.popBackStack()
        }

        CompanyBrowseAPI().CompanyGetBrowserJob(context){
            Log.d("response 119", it.toString())
            if(it != null){
                val companyList = it.data.filter { com -> com.companyNo != CompanyNo }
                if(companyList.size == 0){
                    view.findViewById<LinearLayout>(R.id.otherCompanyContainer).visibility = GONE
                }

//                recyclerView?.apply {
//                    layoutManager = LinearLayoutManager(activity)
//                    adapter = CompanyVacanciesAdapter(context, companyList, this@CompanyDetailFragment)
//                }
                recyclerView?.apply {
                    layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                    adapter = OtherCompanyAdapter(context, companyList, this@CompanyDetailFragment)
                }
            }
        }

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onRelatedCompanyFragment(CompanyNo: Long) {
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.replace(id, CompanyDetailFragment(CompanyNo), "CompanyDetailFragment")
        ft.addToBackStack("CompanyDetailFragment")
        ft.commit()
    }

    override fun goToJobDetail(jobNo: Long, companyNo: Long) {
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.replace(id, JobDetailFragment(jobNo, CompanyNo), "jobDetailFragment")
        ft.addToBackStack("jobDetailFragment")
        ft.commit()
    }
    override fun onCompanyDetailPage(CompanyNo: Long){
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.replace(id, CompanyDetailFragment(CompanyNo), "company detail page")
        ft.addToBackStack("CompanyPage")
        ft.commit()
    }

    override fun goToCompanyReview(CompanyNo: Long) {
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.replace(id, CompanyReviewFragment(CompanyNo), "CompanyReviewFragment")
        ft.addToBackStack("CompanyReviewFragment")
        ft.commit()
    }
}

interface OnFragmentCompanyDetailListener {
    fun onRelatedCompanyFragment(CompanyNo: Long)
    fun goToJobDetail(JobNo: Long, CompanyNo: Long)
    fun goToCompanyReview(CompanyNo: Long)
    fun onCompanyDetailPage(companyNo: Long)
}