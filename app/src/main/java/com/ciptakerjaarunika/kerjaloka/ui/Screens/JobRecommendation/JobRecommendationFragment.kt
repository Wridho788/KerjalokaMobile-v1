package com.ciptakerjaarunika.kerjaloka.ui.Screens.JobRecommendation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.ciptakerjaarunika.kerjaloka.api.JobAPI
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentJobRecommendationBinding
import com.ciptakerjaarunika.kerjaloka.model.Job.SearchJobModel
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobDetailScreen.JobDetailFragment
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobPage.Adapter.JobAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobPage.IJobPage


class JobRecommendationFragment : Fragment(), IJobPage {
    private lateinit var binding: FragmentJobRecommendationBinding
    private var listData : List<SearchJobModel> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentJobRecommendationBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backButton.setOnClickListener{
            fragmentManager?.popBackStack()
        }
        RefreshData()

    }
    override fun RefreshData(){
        if(SessionManager(context).user != null) {
            JobAPI().getJobRecommendation(true, context){
                if (it != null) {
                    listData = it.data
                    binding.spinner.visibility = GONE
                    binding.recycleview.visibility = VISIBLE

                    binding.recycleview?.apply {
                        adapter = JobAdapter(1, listData, context, this@JobRecommendationFragment)
                        layoutManager = LinearLayoutManager(activity)
                    }
                }
            }
        }
        else{
            //Get All Jobs
        }
    }

    override fun GoToJobDetail(JobNo: Long, CompanyNo: Long?) {
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.replace(id, JobDetailFragment(JobNo, CompanyNo), "JobDetailFragment")
        ft.addToBackStack("Job Page")
        ft.commit()
    }

    override fun BookmarkJob(ListNo : Int, JobNo: Long, Index: Int) {
        JobAPI().BookmarkJob(JobNo, !listData[Index].bookmarked, context) {
            if(it != null) {
                if (it.code == 210) {
                   listData[Index].bookmarked = !listData[Index].bookmarked
                    binding.recycleview.adapter?.notifyDataSetChanged()
                } else {
                    Toast.makeText(context, it.Message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}