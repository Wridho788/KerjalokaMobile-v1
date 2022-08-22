package com.ciptakerjaarunika.kerjaloka.ui.HomePage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.databinding.ActivityMainBinding
import com.ciptakerjaarunika.kerjaloka.ui.HomePage.Adapter.RecommendationJobAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobDetailScreen.JobDetailFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

class HomePage : Fragment(), OnFragmentClickListener {
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
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val btn_search = view.findViewById<LinearLayout>(R.id.btn_search) as LinearLayout
        val btn_notif = view.findViewById<MaterialButton>(R.id.notif_btn) as MaterialButton
        val btn_job = view.findViewById<MaterialCardView>(R.id.btn_job) as MaterialCardView
        val btn_company = view.findViewById<MaterialCardView>(R.id.btn_company) as MaterialCardView
        val btn_offer_job =
            view.findViewById<MaterialCardView>(R.id.btn_job_offer) as MaterialCardView

        btn_search.setOnClickListener {
            Toast.makeText(activity, "Go to Search Activity", Toast.LENGTH_SHORT).show()
        }
        btn_notif.setOnClickListener {
            Toast.makeText(activity, "Go to Notification Activity", Toast.LENGTH_SHORT).show()
        }
        btn_job.setOnClickListener {
            Toast.makeText(activity, "Go to job Activity", Toast.LENGTH_SHORT).show()
        }
        btn_company.setOnClickListener {
            Toast.makeText(activity, "Go to Company Activity", Toast.LENGTH_SHORT).show()
        }
        btn_offer_job.setOnClickListener {
            Toast.makeText(activity, "Go to Offer Job Activity", Toast.LENGTH_SHORT).show()
        }


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val Context = this;
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_recommendation_jobs)
        layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        adapter = RecommendationJobAdapter(Context)
        recyclerView.adapter = adapter
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomePage().apply {
            }
    }

    override fun onFragmentClick() {
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.replace(id, JobDetailFragment(), "jobDetailFragment")
        ft.addToBackStack(null)
        ft.commit()
    }
}

interface OnFragmentClickListener {
    fun onFragmentClick()
}