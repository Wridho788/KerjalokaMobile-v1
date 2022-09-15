package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.databinding.ActivityMainBinding
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail.Bottomsheet.PapikostikResultFragment
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail.SectionKomentar.KomentarApplicantFragment
import com.google.android.material.card.MaterialCardView

class ApplicantDetailFragment : Fragment(), OnFragmentClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_applicant_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btn_result_papikostick =
            view.findViewById<MaterialCardView>(R.id.btn_lihat_hasil_tes_applicant)
        val btn_lihat_komentar = view.findViewById<MaterialCardView>(R.id.btn_lihat_komentar)

        val toolbar = view.findViewById<ImageView>(R.id.btn_back_applicant)
        toolbar.setOnClickListener {
            activity?.onBackPressed()
        }

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)

        btn_result_papikostick.setOnClickListener {
            val sheet = PapikostikResultFragment()
            activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "ResultPapikostick") }
        }

        btn_lihat_komentar.setOnClickListener {
            goToCommentApplicant()
        }
    }

    override fun goToCommentApplicant() {
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.replace(id, KomentarApplicantFragment(), "CommentarApplicant")
        ft.addToBackStack("CommentarApplicant")
        ft.commit()
    }
}

interface OnFragmentClickListener {
    fun goToCommentApplicant()
}