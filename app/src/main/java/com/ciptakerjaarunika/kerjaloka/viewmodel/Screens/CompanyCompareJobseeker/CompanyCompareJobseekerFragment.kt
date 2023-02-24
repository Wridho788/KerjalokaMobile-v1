package com.ciptakerjaarunika.kerjaloka.viewmodel.Screens.CompanyCompareJobseeker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.viewmodel.Screens.CompanyCompareJobseeker.ResultCompare.ResultCompareFragment
import com.ciptakerjaarunika.kerjaloka.viewmodel.Screens.CompanyCompareJobseeker.bottomsheet.tambahJobseekerFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView


class CompanyCompareJobseekerFragment : Fragment(), OnFragmentClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_company_compare_jobseeker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btn_pelamar_1 = view.findViewById<MaterialCardView>(R.id.card_pelamar1)
        val btn_pelamar_2 = view.findViewById<MaterialCardView>(R.id.card_pelamar2)
        val btn_ganti_jobseeker = view.findViewById<MaterialCardView>(R.id.btn_ganti_pelamar)
        val toolbar = view.findViewById<ImageView>(R.id.btn_back_compare)
        val btn_banding_jobseeker = view.findViewById<MaterialButton>(R.id.btn_banding_jobseeker)

        btn_ganti_jobseeker.setOnClickListener {
            val sheet = tambahJobseekerFragment()
            activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "ResultPapikostick") }
        }

        toolbar.setOnClickListener {
            activity?.onBackPressed()
        }

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)

        btn_banding_jobseeker.setOnClickListener {
            goToResult()

        }
    }

    override fun goToResult() {
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.replace(id, ResultCompareFragment(), "ResultCompare")
        ft.addToBackStack("ResultCompare")
        ft.commit()
    }
}

interface OnFragmentClickListener {
    fun goToResult()
}