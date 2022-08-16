package com.ciptakerjaarunika.kerjaloka.ui.HomePage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.Screens.SearchScreen.SearchActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomePage.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomePage : Fragment() {

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
        var btn_see_all = view.findViewById<TextView>(R.id.btn_see_all) as TextView
        var card_test_section =
            view.findViewById<MaterialCardView>(R.id.card_test) as MaterialCardView
        var card_interview_section =
            view.findViewById<MaterialCardView>(R.id.card_interview) as MaterialCardView
        var btn_see_all_interview =
            view.findViewById<TextView>(R.id.btn_see_all_interview) as TextView
        var card_recommendation_job =
            view.findViewById<MaterialCardView>(R.id.card_recommendation_job) as MaterialCardView
        var btn_see_all_recommendation_job =
            view.findViewById<TextView>(R.id.btn_see_all_recommendation_jobs) as TextView
        var btn_bookmark = view.findViewById<MaterialButton>(R.id.btn_bookmark) as MaterialButton
        var btn_share = view.findViewById<MaterialButton>(R.id.btn_share) as MaterialButton

        btn_search.setOnClickListener {
            // code here to handle intent to search activity
            // create intent to search activity
            val intent = Intent(activity, SearchActivity::class.java)
            // start activity
            startActivity(intent)

//            Toast.makeText(activity, "Go to Search Activity", Toast.LENGTH_SHORT).show()
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
        btn_see_all.setOnClickListener {
            // code here to handle intent to see all activity
            Toast.makeText(activity, "see all!", Toast.LENGTH_SHORT).show()
        }
        card_test_section.setOnClickListener {  // code here to handle intent to Selection List activity
            Toast.makeText(activity, "Seleksi Saya!", Toast.LENGTH_SHORT).show()
        }
        card_interview_section.setOnClickListener {
            // code here to handle intent to Selection Interview activity
            Toast.makeText(activity, "Interview Saya!", Toast.LENGTH_SHORT).show()
        }
        btn_see_all_interview.setOnClickListener {
            // code here to handle intent to see all activity
            Toast.makeText(activity, "see all!", Toast.LENGTH_SHORT).show()
        }
        card_recommendation_job.setOnClickListener {
            // code here to handle intent to recommend job activity
            Toast.makeText(activity, "Pekerjaan Rekomendasi ", Toast.LENGTH_SHORT).show()
        }
        btn_see_all_recommendation_job.setOnClickListener {
            // code here to handle intent to see all activity
            Toast.makeText(activity, "see all!", Toast.LENGTH_SHORT).show()
        }
        btn_bookmark.setOnClickListener {
            // code here to handle intent to bookmark activity
            Toast.makeText(activity, "bookmark", Toast.LENGTH_SHORT).show()
        }
        btn_share.setOnClickListener {
            // code here to handle intent to share activity
            Toast.makeText(activity, "share", Toast.LENGTH_SHORT).show()
        }
        // Inflate the layout for this fragment
        return view
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
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomePage().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}