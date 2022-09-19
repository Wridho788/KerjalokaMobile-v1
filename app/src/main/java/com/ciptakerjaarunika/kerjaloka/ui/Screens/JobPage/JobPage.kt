package com.ciptakerjaarunika.kerjaloka.ui.Screens.JobPage;

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_DENIED
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
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.JobAPI
import com.ciptakerjaarunika.kerjaloka.model.Job.RecommendationJob
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobPage.Adapter.BookmarkedJobAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobPage.Adapter.NearMeJobAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobPage.Adapter.RecommendationJobAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobSearch.SearchJob
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton


class JobPage: Fragment(){
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

        val btn_search = view.findViewById<LinearLayout>(R.id.search_job_btn)
        val btn_seeBookmarkedJob = view.findViewById<MaterialButton>(R.id.btnSeeBookmarked)
        val btn_seeNearMeJob = view.findViewById<MaterialButton>(R.id.btnSeeNearMe)
        val btn_seeRecommendJob = view.findViewById<MaterialButton>(R.id.seeRecommend)

        var rcylRecommendation = view.findViewById<RecyclerView>(R.id.recommenJob)
        if(SessionManager(context).user != null) {
            JobAPI().getJobRecommendation(true, context) {
                recommendationDone()
                if (it != null) {
                    if(it.data.size <= 5){
                        btn_seeRecommendJob.visibility = GONE
                    }
                    rcylRecommendation.apply {
                        adapter = RecommendationJobAdapter(it.data.take(5), null,context)
                        layoutManager = LinearLayoutManager(activity)
                    }
                }
            }

            var rcylBookmarked = view.findViewById<RecyclerView>(R.id.bookmaredJob)
            JobAPI().getBookmarkedJob(context){
                bookmarkedDone()
                if (it != null) {
                    if(it.data.size <= 5){
                        btn_seeBookmarkedJob.visibility = GONE
                    }
                    rcylBookmarked.apply {
                        adapter = BookmarkedJobAdapter(it.data.take(5), context)
                        layoutManager = LinearLayoutManager(activity)
                    }
                }
            }
        }
        else{
            JobAPI().getJobHomeAsync(context){
                recommendationDone()

                if(it != null) {
                    rcylRecommendation.apply {
                        adapter = RecommendationJobAdapter(null, it.data.take(5),context)
                        layoutManager = LinearLayoutManager(activity)
                    }
                }
            }
            view.findViewById<ConstraintLayout>(R.id.bookmark_container).visibility = GONE
        }


        btn_search.setOnClickListener {
            val intent = Intent(activity, SearchJob::class.java)
            startActivity(intent)
        }

        btn_seeBookmarkedJob.setOnClickListener {
            Toast.makeText(context, "Bookmarker Job Need API", Toast.LENGTH_SHORT).show()
        }

        btn_seeNearMeJob.setOnClickListener {
            Toast.makeText(context, "near me Job Need API", Toast.LENGTH_SHORT).show()

        }

        btn_seeRecommendJob.setOnClickListener {
            Toast.makeText(context, "recommendation Job Need API", Toast.LENGTH_SHORT).show()

        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar_job)
        val layout_search_job = view.findViewById<LinearLayout>(R.id.search_job_btn)


        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        layout_search_job.setOnClickListener{
            val intent = Intent(activity, SearchJob::class.java)
            startActivity(intent)
        }

    }
    fun getNearJob(){
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)
        fusedLocationClient.getCurrentLocation(102, null).addOnSuccessListener {
            val latitude =  it.latitude.toString()
            val longtitude = it.longitude.toString()
            JobAPI().getNearJob(latitude, longtitude, context){
                nearJobDone()
                if(it != null && it.data.size != 0) {
                    if (it?.data?.size!! <= 5) {
                        view?.findViewById<LinearLayout>(R.id.btnSeeNearMe)?.visibility = GONE
                    }
                    val recyclerView = view?.findViewById<RecyclerView>(R.id.nearmeJob)
                    recyclerView?.apply {
                        adapter = NearMeJobAdapter(it.data.take(5),context)
                        layoutManager = LinearLayoutManager(activity)
                    }
                }
                else{
                    view?.findViewById<LinearLayout>(R.id.empty_near_job)?.visibility = VISIBLE
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
