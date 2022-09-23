package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail.SectionKomentar

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.databinding.ActivityMainBinding
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail.SectionKomentar.Adapter.KomentarAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail.SectionKomentar.Model.CommentModel

class KomentarApplicantFragment(private val comment: List<CommentModel>) : Fragment() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_komentar_applicant, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("comment", comment.toString())
        val list = ArrayList<CommentModel>()
        val list1 = CommentModel(
            1,
            "Pt. Mantab Mantab",
            "Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint. Velit officia consequat duis enim velit mollit. Exercitation veniam consequat sunt nostrud amet.",
            "24 Mei 2022 pada 16:04"
        )
        val list2 = CommentModel(
            2,
            "Pt. Mantab Mantab",
            "Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint. Velit officia consequat duis enim velit mollit. Exercitation veniam consequat sunt nostrud amet.",
            "24 Mei 2022 pada 16:04"
        )
        val list3 = CommentModel(
            3,
            "Pt. Mantab Mantab",
            "Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint. Velit officia consequat duis enim velit mollit. Exercitation veniam consequat sunt nostrud amet.",
            "24 Mei 2022 pada 16:04"
        )
        val list4 = CommentModel(
            4,
            "Pt. Mantab Mantab",
            "Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint. Velit officia consequat duis enim velit mollit. Exercitation veniam consequat sunt nostrud amet.",
            "24 Mei 2022 pada 16:04"
        )
        val list5 = CommentModel(
            5,
            "Pt. Mantab Mantab",
            "Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint. Velit officia consequat duis enim velit mollit. Exercitation veniam consequat sunt nostrud amet.",
            "24 Mei 2022 pada 16:04"
        )
        list.add(list1)
        list.add(list2)
        list.add(list3)
        list.add(list4)
        list.add(list5)

        val toolbar = view.findViewById<ImageView>(R.id.btn_back)
        toolbar.setOnClickListener {
            activity?.onBackPressed()
        }

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)

        val rv_comment = view.findViewById<RecyclerView>(R.id.rv_comment_applicant)
        rv_comment.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = KomentarAdapter(list)
        }

    }
}