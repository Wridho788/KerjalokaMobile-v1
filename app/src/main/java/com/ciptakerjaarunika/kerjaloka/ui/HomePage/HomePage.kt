package com.ciptakerjaarunika.kerjaloka.ui.HomePage

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
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
import com.ciptakerjaarunika.kerjaloka.ui.HomePage.Adapter.RecommendationJobAdapter
import com.ciptakerjaarunika.kerjaloka.ui.HomePage.Model.rJobModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import kotlin.math.log

class HomePage : Fragment() {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecommendationJobAdapter.ViewHolder>? = null
    private lateinit var binding: ActivityMainBinding
    private var listJob : List<rJobModel>?=null;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setContentView(root: ConstraintLayout) {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val btn_search = view.findViewById<LinearLayout>(R.id.btn_search) as LinearLayout
        val btn_notif = view.findViewById<MaterialButton>(R.id.notif_btn) as MaterialButton
        val btn_job = view.findViewById<MaterialCardView>(R.id.btn_job) as MaterialCardView
        val btn_company = view.findViewById<MaterialCardView>(R.id.btn_company) as MaterialCardView
        val btn_offer_job =
            view.findViewById<MaterialCardView>(R.id.btn_job_offer) as MaterialCardView
//        var btn_see_all = view.findViewById<TextView>(R.id.btn_see_all) as TextView
//        var card_test_section =
//            view.findViewById<MaterialCardView>(R.id.card_test) as MaterialCardView
//        var card_interview_section = view.findViewById<MaterialCardView>(R.id.card_interview) as MaterialCardView;
//        var btn_see_all_interview = view.findViewById<TextView>(R.id.btn_see_all_interview) as TextView;
//        var card_recommendation_job = view.findViewById<MaterialCardView>(R.id.card_recommendation_job) as MaterialCardView;
//        var btn_see_all_recommendation_job = view.findViewById<TextView>(R.id.btn_see_all_recommendation_jobs) as TextView;

//        var btn_bookmark = view.findViewById<MaterialButton>(R.id.btn_bookmark) as MaterialButton
//        var btn_share = view.findViewById<MaterialButton>(R.id.btn_share) as MaterialButton


        btn_search.setOnClickListener {
            // code here to handle intent to search activity
            Toast.makeText(activity, "Go to Search Activity", Toast.LENGTH_SHORT).show()
        }
        btn_notif.setOnClickListener {
            // code here to handle intent to notification  activity
            Toast.makeText(activity, "Go to Notification Activity", Toast.LENGTH_SHORT).show()
        }
        btn_job.setOnClickListener {
            // code here to handle intent to job activity
            Toast.makeText(activity, "Go to job Activity", Toast.LENGTH_SHORT).show()
        }
        btn_company.setOnClickListener {
            // code here to handle intent to company activity
            Toast.makeText(activity, "Go to Company Activity", Toast.LENGTH_SHORT).show()
        }
        btn_offer_job.setOnClickListener {
            // code here to handle intent to offer job activity
            Toast.makeText(activity, "Go to Offer Job Activity", Toast.LENGTH_SHORT).show()
        }
//        btn_see_all.setOnClickListener {
//            // code here to handle intent to see all activity
//            Toast.makeText(activity, "see all!", Toast.LENGTH_SHORT).show()
//        }
//        card_test_section.setOnClickListener {  // code here to handle intent to Selection List activity
//            Toast.makeText(activity, "Seleksi Saya!", Toast.LENGTH_SHORT).show()
//        }
//        card_interview_section.setOnClickListener {
//            // code here to handle intent to Selection Interview activity
//            Toast.makeText(activity, "Interview Saya!", Toast.LENGTH_SHORT).show()
//        }
//        btn_see_all_interview.setOnClickListener {
//            // code here to handle intent to see all activity
//            Toast.makeText(activity, "see all!", Toast.LENGTH_SHORT).show()
//        }


//        card_recommendation_job.setOnClickListener {
//            // code here to handle intent to recommend job activity
//            Toast.makeText(activity, "Pekerjaan Rekomendasi ", Toast.LENGTH_SHORT).show()
//        }
//        btn_see_all_recommendation_job.setOnClickListener {
//            // code here to handle intent to see all activity
//            Toast.makeText(activity, "see all!", Toast.LENGTH_SHORT).show()
//        }
//        btn_bookmark.setOnClickListener {
//            // code here to handle intent to bookmark activity
//            Toast.makeText(activity, "bookmark", Toast.LENGTH_SHORT).show()
//        }
//        btn_share.setOnClickListener {
//            // code here to handle intent to share activity
//            Toast.makeText(activity, "share", Toast.LENGTH_SHORT).show()
//        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Response API", "Testing")
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_recommendation_jobs)

        val Context = this;
        JobAPI().getJobHomeAsync {
            Log.d("Response API", it.toString())
            if (it != null) {
                listJob = it.data
                recyclerView.apply {
                    layoutManager = LinearLayoutManager(activity)
                    recyclerView.layoutManager = layoutManager
                    adapter = RecommendationJobAdapter(listJob)
                }

            }
        }

//        recyclerView.adapter = adapter
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomePage().apply {

            }
    }
}