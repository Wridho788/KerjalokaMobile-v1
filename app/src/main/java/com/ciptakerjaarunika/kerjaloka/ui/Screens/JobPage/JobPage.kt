package com.ciptakerjaarunika.kerjaloka.ui.Screens.JobPage

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
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.JobAPI
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentJobPageBinding
import com.ciptakerjaarunika.kerjaloka.model.Job.SearchJobModel
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobBookmark.JobBookmarkFragment
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobDetailScreen.JobDetailFragment
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobNearMe.JobNearmeFragment
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobPage.Adapter.JobAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobRecommendation.JobRecommendationFragment
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobSearch.SearchJob
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.appbar.MaterialToolbar


class JobPage : Fragment(), IJobPage {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var activityResultLauncher: ActivityResultLauncher<Array<String>>
    private var listRecommendation: List<SearchJobModel> = listOf()
    private var listNear: List<SearchJobModel> = listOf()
    private var listBookmark: List<SearchJobModel> = listOf()
    private lateinit var binding: FragmentJobPageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentJobPageBinding.inflate(layoutInflater)
        val view = binding.root

        binding.backButton.setOnClickListener {
            fragmentManager?.popBackStack()
        }
//        val callback: OnBackPressedCallback =
//            object : OnBackPressedCallback(true /* enabled by default */) {
//                override fun handleOnBackPressed() {
//                    fragmentManager?.popBackStack()
//                }
//            }
//        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

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
                    Toast.makeText(
                        context,
                        "Memerlukan akses lokasi untuk mendapatkan pekerjaan terdekat",
                        Toast.LENGTH_LONG
                    ).show()
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

        layout_search_job.setOnClickListener {
            changeFragment(SearchJob())
        }

    }

    override fun RefreshData() {
        binding.bookmarkContainer.visibility = VISIBLE
        JobAPI().getJobRecommendation(true, context) {
            recommendationDone()
            if (it != null) {
                binding.recommenJob.visibility = VISIBLE
                listRecommendation = it.data.take(5)
                binding.seeRecommend.visibility = if (it.data.size <= 5) GONE else VISIBLE
                binding.recommenJob.apply {
                    adapter = JobAdapter(1, listRecommendation, context, this@JobPage, null)
                    layoutManager = LinearLayoutManager(activity)
                }
            }
        }

        getNearJob()

        if (SessionManager(context).user != null) {
            JobAPI().getBookmarkedJob(context) {
                bookmarkedDone()
                if (it?.data != null) {
                    Log.d("bookmarked", it.data.toString())
                    listBookmark = it.data.take(5)
                    binding.btnSeeBookmarked.visibility = if (it.data.size <= 5) GONE else VISIBLE
                    binding.bookmaredJob.apply {
                        adapter = JobAdapter(3, listBookmark, context, this@JobPage, null)
                        layoutManager = LinearLayoutManager(activity)
                    }
                } else if (it?.data?.size != 0) {
                    binding.emptyBookmarkJob.visibility = VISIBLE
                } else {
                    binding.emptyBookmarkJob.visibility = View.GONE
                }
            }
        } else {
            //Get Search Job
            view?.findViewById<ConstraintLayout>(R.id.bookmark_container)?.visibility = GONE
        }


        binding.searchJobBtn.setOnClickListener {
            val intent = Intent(activity, SearchJob::class.java)
            startActivity(intent)
        }

        binding.btnSeeBookmarked.setOnClickListener {
            changeFragment(JobBookmarkFragment())
        }

        binding.btnSeeNearMe.setOnClickListener {
            changeFragment(JobNearmeFragment())
        }

        binding.seeRecommend.setOnClickListener {
            changeFragment(JobRecommendationFragment())
        }
    }

    override fun GoToJobDetail(JobNo: Long, CompanyNo: Long?) {
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.replace(id, JobDetailFragment(JobNo, CompanyNo), "JobDetailFragment")
        ft.addToBackStack("Job Page")
        ft.commit()
    }

    fun getNearJob() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)
        fusedLocationClient.getCurrentLocation(102, null).addOnSuccessListener {
            var latitude = it.latitude.toString()
            var longitude = it.longitude.toString()
            if (it == null) {
                nearJobDone()
                binding.emptyNearJob.visibility = VISIBLE
                binding.btnSeeNearMe.visibility = GONE
            } else {
                binding.emptyNearJob.visibility = GONE

                JobAPI().getNearJob(latitude, longitude, context) {
                    nearJobDone()
                    if (it != null && it.data != null && it.data.size != 0) {
                        binding.btnSeeNearMe.visibility = if (it.data.size <= 5) GONE else VISIBLE
                        val recyclerView = binding.nearmeJob

                        recyclerView.apply {
                            adapter = JobAdapter(2, it.data.take(5), context, this@JobPage, null)
                            layoutManager = LinearLayoutManager(activity)
                        }
                        recyclerView.adapter?.notifyDataSetChanged()
                    } else {
                        binding.emptyNearJob.visibility = VISIBLE
                        binding.btnSeeNearMe.visibility = GONE
                    }
                }
            }
        }
    }

    override fun BookmarkJob(ListType: Int, JobNo: Long, Index: Int) {
        JobAPI().BookmarkJob(JobNo, !listRecommendation[Index].bookmarked, context) {
            if (it != null) {
                if (it.code == 210) {
                    when (ListType) {
                        1 -> {
                            listRecommendation[Index].bookmarked =
                                !listRecommendation[Index].bookmarked
                            binding.recommenJob.apply {
                                adapter =
                                    JobAdapter(1, listRecommendation, context, this@JobPage, null)
                                layoutManager = LinearLayoutManager(activity)
                            }
                            binding.recommenJob.adapter?.notifyDataSetChanged()
                            this.RefreshData()
                        }
                        2 -> {
                            listNear[Index].bookmarked = !listNear[Index].bookmarked
                            binding.nearmeJob.apply {
                                adapter = JobAdapter(1, listNear, context, this@JobPage, null)
                                layoutManager = LinearLayoutManager(activity)
                            }
                            binding.nearmeJob.adapter?.notifyDataSetChanged()
                            this.RefreshData()

                        }
                        3 -> {
                            listBookmark[Index].bookmarked = !listBookmark[Index].bookmarked
                            binding.bookmaredJob.apply {
                                adapter = JobAdapter(1, listBookmark, context, this@JobPage, null)
                                layoutManager = LinearLayoutManager(activity)
                            }
                            binding.bookmaredJob.adapter?.notifyDataSetChanged()
                            this.RefreshData()
                        }
                    }

                } else {
                    Toast.makeText(context, it.Message, Toast.LENGTH_SHORT).show()
                }
            }
        }


    }


    fun recommendationDone() {
        binding.recommendationJobContainer.visibility = VISIBLE
        binding.spinnerRecommendation.visibility = GONE
    }

    fun bookmarkedDone() {
        binding.bookmarkJobContainer.visibility = VISIBLE
        binding.spinnerBookmark.visibility = GONE
    }

    fun nearJobDone() {
        binding.nearJobContainer.visibility = VISIBLE
        binding.spinnerNear.visibility = GONE
    }

    fun changeFragment(Goto: Fragment) {
        val fragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.addToBackStack("Job Page")
        fragmentTransaction.replace(R.id.fragment_container, Goto)
        fragmentTransaction.commit()
    }
}

interface IJobPage {
    fun RefreshData()
    fun GoToJobDetail(JobNo: Long, CompanyNo: Long?)
    fun BookmarkJob(ListType: Int, JobNo: Long, Index: Int)
}
