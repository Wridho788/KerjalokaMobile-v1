package com.ciptakerjaarunika.kerjaloka.ui.Screens.JobPage;

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.JobAPI
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.LamaranPage.viewJobDetail
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobDetailScreen.JobDetailFragment
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobPage.Adapter.JobAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobPage.Adapter.RecommendationJobAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobSearch.SearchJob
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton


class JobPage: Fragment(), IJobPage{
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var activityResultLauncher : ActivityResultLauncher<Array<String>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_job_page, container, false)

        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) ||
                        permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false)
                -> {
                   getNearJob()
                }
                 else -> {
                     Toast.makeText(context, "Memerlukan akses lokasi untuk mendapatkan pekerjaan terdekat", Toast.LENGTH_LONG).show()
                     view.findViewById<ConstraintLayout>(R.id.near_container).visibility = GONE
                }
            }
        }
        activityResultLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
//        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED {
//
//        }else{
//
//        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar_job)
        val layout_search_job = view.findViewById<LinearLayout>(R.id.search_job_btn)

        RefreshData()
        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        layout_search_job.setOnClickListener{
            val intent = Intent(activity, SearchJob::class.java)
            startActivity(intent)
        }

    }
    override fun RefreshData(){
        val btn_search = view?.findViewById<LinearLayout>(R.id.search_job_btn)
        val btn_seeBookmarkedJob = view?.findViewById<MaterialButton>(R.id.btnSeeBookmarked)
        val btn_seeNearMeJob = view?.findViewById<MaterialButton>(R.id.btnSeeNearMe)
        val btn_seeRecommendJob = view?.findViewById<MaterialButton>(R.id.seeRecommend)

        var rcylRecommendation = view?.findViewById<RecyclerView>(R.id.recommenJob)

        view?.findViewById<ConstraintLayout>(R.id.bookmark_container)?.visibility = VISIBLE

        if(SessionManager(context).user != null) {
            JobAPI().getJobRecommendation(true, context) {
                recommendationDone()
                if (it != null) {
                    btn_seeRecommendJob?.visibility = if(it.data.size <= 5) GONE else VISIBLE
                    rcylRecommendation?.apply {
                        adapter = RecommendationJobAdapter(it.data.take(5), null,context, this@JobPage)
                        layoutManager = LinearLayoutManager(activity)
                    }
                    rcylRecommendation?.adapter?.notifyDataSetChanged()
                }
            }

            var rcylBookmarked = view?.findViewById<RecyclerView>(R.id.bookmaredJob)
            JobAPI().getBookmarkedJob(context){
                bookmarkedDone()
                if (it != null) {
                    btn_seeBookmarkedJob?.visibility = if(it.data.size <= 5) GONE else VISIBLE
                    rcylBookmarked?.apply {
                        adapter = JobAdapter(it.data.take(5), context, this@JobPage)
                        layoutManager = LinearLayoutManager(activity)
                    }
                    rcylBookmarked?.adapter?.notifyDataSetChanged()
                }
            }
        }
        else{
            JobAPI().getJobHomeAsync(context){
                recommendationDone()
                btn_seeRecommendJob?.visibility = if(it == null || it.data.size <= 5) GONE else VISIBLE

                if(it != null) {
                    rcylRecommendation?.apply {
                        adapter = RecommendationJobAdapter(null, it.data.take(5),context, this@JobPage)
                        layoutManager = LinearLayoutManager(activity)
                    }
                    rcylRecommendation?.adapter?.notifyDataSetChanged()
                }
            }
            view?.findViewById<ConstraintLayout>(R.id.bookmark_container)?.visibility = GONE
        }


        btn_search?.setOnClickListener {
            val intent = Intent(activity, SearchJob::class.java)
            startActivity(intent)
        }

        btn_seeBookmarkedJob?.setOnClickListener {
            Toast.makeText(context, "Bookmarker Job Need API", Toast.LENGTH_SHORT).show()
        }

        btn_seeNearMeJob?.setOnClickListener {
            Toast.makeText(context, "near me Job Need API", Toast.LENGTH_SHORT).show()

        }

        btn_seeRecommendJob?.setOnClickListener {
            Toast.makeText(context, "recommendation Job Need API", Toast.LENGTH_SHORT).show()

        }
    }

    override fun GoToJobDetail(JobNo: Long, CompanyNo: Long) {
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.replace(id, JobDetailFragment(JobNo, CompanyNo), "JobDetailFragment")
        ft.addToBackStack("Job Page")
        ft.commit()
    }

    fun getNearJob(){
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)
        fusedLocationClient.getCurrentLocation(102, null).addOnSuccessListener {
            nearJobDone()
            if(it == null){
                view?.findViewById<android.widget.TextView>(R.id.empty_near_job)?.visibility =
                    VISIBLE
                view?.findViewById<MaterialButton>(R.id.btnSeeNearMe)?.visibility = GONE
            }
            else {
                view?.findViewById<android.widget.TextView>(R.id.empty_near_job)?.visibility = GONE

                val latitude = it.latitude.toString()
                val longtitude = it.longitude.toString()
                JobAPI().getNearJob(latitude, longtitude, context) {
                    if (it != null && it.data.size != 0) {
                        view?.findViewById<MaterialButton>(R.id.btnSeeNearMe)?.visibility = if(it.data.size <= 5) GONE else VISIBLE
                        val recyclerView = view?.findViewById<RecyclerView>(R.id.nearmeJob)
                        recyclerView?.apply {
                            adapter = JobAdapter(it.data.take(5), context, this@JobPage)
                            layoutManager = LinearLayoutManager(activity)
                        }
                        recyclerView?.adapter?.notifyDataSetChanged()
                    } else {
                        view?.findViewById<android.widget.TextView>(R.id.empty_near_job)?.visibility =
                            VISIBLE
                        view?.findViewById<MaterialButton>(R.id.btnSeeNearMe)?.visibility = GONE
                    }
                }
            }
        }
    }


    fun recommendationDone() {
        view?.findViewById<LinearLayout>(R.id.recommendation_job_container)?.visibility = VISIBLE
        view?.findViewById<LinearLayout>(R.id.spinnerRecommendation)?.visibility = GONE
    }
    fun bookmarkedDone() {
        view?.findViewById<LinearLayout>(R.id.bookmark_job_container)?.visibility = VISIBLE
        view?.findViewById<LinearLayout>(R.id.spinnerBookmark)?.visibility = GONE
    }
    fun nearJobDone() {
        view?.findViewById<LinearLayout>(R.id.near_job_container)?.visibility = VISIBLE
        view?.findViewById<LinearLayout>(R.id.spinnerNear)?.visibility = GONE
    }
}
interface IJobPage{
    fun RefreshData()
    fun GoToJobDetail(JobNo : Long, CompanyNo: Long)
}
