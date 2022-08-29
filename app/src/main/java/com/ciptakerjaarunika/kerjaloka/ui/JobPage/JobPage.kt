package com.ciptakerjaarunika.kerjaloka.ui.JobPage;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.databinding.ActivityMainBinding
import com.ciptakerjaarunika.kerjaloka.ui.HomePage.Adapter.RecommendationJobAdapter
import com.ciptakerjaarunika.kerjaloka.ui.HomePage.Model.rJobModel
import com.ciptakerjaarunika.kerjaloka.ui.HomePage.OnFragmentClickListener
import com.google.android.material.button.MaterialButton


class Job_Page(val onFragmentClickListener: OnFragmentClickListener) : Fragment() {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecommendationJobAdapter.ViewHolder>? = null

    private lateinit var binding: ActivityMainBinding

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
        val view = inflater.inflate(R.layout.activity_job_page, container, false)
        val btn_search = view.findViewById<LinearLayout>(R.id.btn_search) as LinearLayout
        val btn_seeBookmarkedJob = view.findViewById<MaterialButton>(R.id.btnSeeBookmarked)
        val btn_seeNearMeJob = view.findViewById<MaterialButton>(R.id.btnSeeNearMe)
        val btn_seeRecommendJob = view.findViewById<MaterialButton>(R.id.seeRecommend)
//        val btn_notif = view.findViewById<MaterialButton>(R.id.notif_btn) as MaterialButton
//        val btn_job = view.findViewById<MaterialCardView>(R.id.btn_job) as MaterialCardView
//        val btn_company = view.findViewById<MaterialCardView>(R.id.btn_company) as MaterialCardView
//        val btn_offer_job =
//            view.findViewById<MaterialCardView>(R.id.btn_job_offer) as MaterialCardView
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
//            val intent = Intent(this, SearchJob::class.java)
//            intent.putExtra("keyIdentifier", value)
//            startActivity(intent)
        }

        btn_seeBookmarkedJob.setOnClickListener {
            replaceFragment(BookmarkJob())
        }

        btn_seeNearMeJob.setOnClickListener {
            replaceFragment(nearMe_Job())
        }

        btn_seeRecommendJob.setOnClickListener {
            replaceFragment(RecommendedJob(onFragmentClickListener))
        }

//        btn_notif.setOnClickListener {
//            // code here to handle intent to notification  activity
//            Toast.makeText(activity, "Go to Notification Activity", Toast.LENGTH_SHORT).show()
//        }
//        btn_job.setOnClickListener {
//            // code here to handle intent to job activity
//            Toast.makeText(activity, "Go to job Activity", Toast.LENGTH_SHORT).show()
//        }
//        btn_company.setOnClickListener {
//            // code here to handle intent to company activity
//            Toast.makeText(activity, "Go to Company Activity", Toast.LENGTH_SHORT).show()
//        }
//        btn_offer_job.setOnClickListener {
//            // code here to handle intent to offer job activity
//            Toast.makeText(activity, "Go to Offer Job Activity", Toast.LENGTH_SHORT).show()
//        }
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
        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val list = ArrayList<rJobModel>()
        val rJob1 = rJobModel(
            1,
            1,
            "PT. KerjaLoka",
            "Medan",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png",
            "other",
        "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png",
        "kerjaloka",
        "one minutes ago"
        )
        val rJob2 = rJobModel(
            2,
            2,
            "PT. KerjaLoka",
            "Medan",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png",
            "other",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png",
            "kerjaloka",
            "one minutes ago"
        )
        val rJob3 = rJobModel(
            3,
            3,
            "PT. KerjaLoka",
            "Medan",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png",
            "other",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png",
            "kerjaloka",
            "one minutes ago"
        )
        val rJob4 = rJobModel(
            4,
            4,
            "PT. KerjaLoka",
            "Medan",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png",
            "other",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png",
            "kerjaloka",
            "one minutes ago"
        )
        val rJob5 = rJobModel(
            4,
            4,
            "PT. KerjaLoka",
            "Medan",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png",
            "other",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png",
            "kerjaloka",
            "one minutes ago"
        )

        list.add(rJob1)
        list.add(rJob2)
        list.add(rJob3)
        list.add(rJob4)
        list.add(rJob5)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recommenJob)
        layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        val Context = this
        adapter = RecommendationJobAdapter(list,  onFragmentClickListener )
        recyclerView.adapter = adapter

//        val recyclerView2 = view.findViewById<RecyclerView>(R.id.nearmeJob)
//        layoutManager = LinearLayoutManager(activity)
//        recyclerView2.layoutManager = layoutManager
//        adapter = RecommendationJobAdapter(list)
//        recyclerView2.adapter = adapter

//        val recyclerView3 = view.findViewById<RecyclerView>(R.id.bookmaredJob)
//        layoutManager = LinearLayoutManager(activity)
//        recyclerView3.layoutManager = layoutManager
//        adapter = RecommendationJobAdapter(list)
//        recyclerView3.adapter = adapter
    }

    private fun replaceFragment(fragment: Fragment){

        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.fragment_container, fragment)
        fragmentTransaction?.commit()
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomePage.
         */
        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            Job_Page().apply {
//
//            }
    }
}