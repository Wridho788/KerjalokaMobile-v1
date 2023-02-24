package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.JobBookmark

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.anychart.ui.contextmenu.Item
import com.ciptakerjaarunika.kerjaloka.api.JobAPI
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentJobBookmarkBinding
import com.ciptakerjaarunika.kerjaloka.model.Job.SearchJobModel
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.JobDetailScreen.JobDetailFragment
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.JobPage.Adapter.JobAdapter
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.JobPage.IJobPage
import com.google.firebase.ktx.Firebase
import com.google.firebase.perf.ktx.performance
import com.google.firebase.perf.metrics.AddTrace

class JobBookmarkFragment : Fragment(), IJobPage {
    private lateinit var binding: FragmentJobBookmarkBinding
    private var listData: List<SearchJobModel> = listOf()

    @AddTrace(name = "onJobBookmarkTrace", enabled = true)
    class ItemCache {
        fun fetch(name: String): Item? {
            return null
        }
    }

    fun jobBookmarkTrace() {
        val cache = ItemCache()
        val myTrace = Firebase.performance.newTrace("job_bookmark_trace")
        myTrace.start()
        val item = cache.fetch("item")
        if (item != null) {
            myTrace.incrementMetric("item_cache_hit", 1)
        } else {
            myTrace.incrementMetric("item_cache_miss", 1)
        }
        myTrace.stop()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        jobBookmarkTrace()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentJobBookmarkBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backButton.setOnClickListener {
            fragmentManager?.popBackStack()
        }
        RefreshData()

    }

    override fun RefreshData() {
        if (SessionManager(context).user != null) {
            JobAPI().getBookmarkedJob(context) {
                if (it != null) {
                    listData = it.data
                    binding.spinner.visibility = View.GONE
                    binding.recycleview.visibility = View.VISIBLE

                    binding.recycleview.apply {
                        adapter = JobAdapter(1, listData, context, this@JobBookmarkFragment, null)
                        layoutManager = LinearLayoutManager(activity)
                    }
                }
            }
        }
    }

    override fun GoToJobDetail(JobNo: Long, CompanyNo: Long?) {
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.replace(id, JobDetailFragment(JobNo, CompanyNo), "JobDetailFragment")
        ft.addToBackStack("Job Page")
        ft.commit()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun BookmarkJob(ListNo: Int, JobNo: Long, Index: Int) {
        JobAPI().BookmarkJob(JobNo, !listData[Index].bookmarked, context) {
            if (it != null) {
                if (it.code == 210) {
                    listData[Index].bookmarked = !listData[Index].bookmarked
                    this.RefreshData()
                    binding.recycleview.adapter?.notifyDataSetChanged()
                } else {
                    Toast.makeText(context, it.Message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}