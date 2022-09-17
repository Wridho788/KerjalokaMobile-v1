package com.ciptakerjaarunika.kerjaloka.ui.Screens.JobPage;

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.JobAPI
import com.ciptakerjaarunika.kerjaloka.databinding.ActivityMainBinding
import com.ciptakerjaarunika.kerjaloka.model.Job.RecommendationJob
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobPage.Adapter.RecommendationJobAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobSearch.SearchJob
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton


class JobPage: Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_job_page, container, false)
        val btn_search = view.findViewById<LinearLayout>(R.id.search_job_btn)
        val btn_seeBookmarkedJob = view.findViewById<MaterialButton>(R.id.btnSeeBookmarked)
        val btn_seeNearMeJob = view.findViewById<MaterialButton>(R.id.btnSeeNearMe)
        val btn_seeRecommendJob = view.findViewById<MaterialButton>(R.id.seeRecommend)

        var rcylRecommendation = view.findViewById<RecyclerView>(R.id.recommenJob)
        if(SessionManager(context).user != null) {
            JobAPI().getJobRecommendation(false, context) {
                recommendationDone()
                if (it != null) {
                    rcylRecommendation.apply {
                        adapter = RecommendationJobAdapter(it.data.take(5), null,context)
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
        val recyclerView = view.findViewById<RecyclerView>(R.id.recommenJob)
    }

    fun recommendationDone() {
        view?.findViewById<LinearLayout>(R.id.recommendation_job_container)?.visibility = VISIBLE
        view?.findViewById<LinearLayout>(R.id.spinnerRecommendation)?.visibility = GONE
    }
}
