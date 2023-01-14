package com.ciptakerjaarunika.kerjaloka.viewmodel.Company.CompanyApplicant.ApplicantDetail.SectionRecords

import android.os.Bundle
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
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.CompanyApplicant.ApplicantDetail.SectionRecords.Adapter.RecordsAdapter
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.CompanyApplicant.ApplicantDetail.SectionRecords.Model.RecordsModel


class RecordsFragment : Fragment() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_records, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val list = ArrayList<RecordsModel>()
        val list1 = RecordsModel(
            1,
            "PT. Mantap Indonesia",
            "12 Agustus 2022 pada 16:24",
            "Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint. Velit officia consequat duis enim velit mollit. Exercitation veniam consequat sunt nostrud amet."
        )
        val list2 = RecordsModel(
            2,
            "PT. Mantap Indonesia",
            "12 Agustus 2022 pada 16:24",
            ""
        )
        val list3 = RecordsModel(
            3,
            "PT. Mantap Indonesia",
            "12 Agustus 2022 pada 16:24",
            "Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint. Velit officia consequat duis enim velit mollit. Exercitation veniam consequat sunt nostrud amet."
        )
        val list4 = RecordsModel(
            4,
            "PT. Mantap Indonesia",
            "12 Agustus 2022 pada 16:24",
            "Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint. Velit officia consequat duis enim velit mollit. Exercitation veniam consequat sunt nostrud amet."
        )
        val list5 = RecordsModel(
            5,
            "PT. Mantap Indonesia",
            "12 Agustus 2022 pada 16:24",
            "Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint. Velit officia consequat duis enim velit mollit. Exercitation veniam consequat sunt nostrud amet."
        )

        list.add(list1)
        list.add(list2)
        list.add(list3)
        list.add(list4)
        list.add(list5)

        val toolbar = view.findViewById<ImageView>(R.id.btn_back_records)
        toolbar.setOnClickListener {
            activity?.onBackPressed()
        }

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)

        val rv_record = view.findViewById<RecyclerView>(R.id.rv_records_applicant)
        rv_record.setHasFixedSize(true)
        rv_record.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = RecordsAdapter(list)
        }
    }
}