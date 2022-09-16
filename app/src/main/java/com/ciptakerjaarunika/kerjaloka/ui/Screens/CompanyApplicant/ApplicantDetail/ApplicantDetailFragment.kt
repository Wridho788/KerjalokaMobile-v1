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
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail.Bottomsheet.MoreAction.MoreActionFragment
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail.Bottomsheet.PapikostikResultFragment
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail.SectionHistory.HistoryFragment
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail.SectionKomentar.KomentarApplicantFragment
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail.SectionRecords.RecordsFragment
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail.SectionStatusPage.StatusPageFragment
import com.google.android.material.button.MaterialButton
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
        val btn_lihat_history = view.findViewById<MaterialCardView>(R.id.btn_lihat_sejarah)
        val btn_lihat_record = view.findViewById<MaterialCardView>(R.id.btn_lihat_record)
        val btn_ganti_status = view.findViewById<MaterialButton>(R.id.btn_change_status)
        val btn_more = view.findViewById<MaterialCardView>(R.id.btn_more)
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

        btn_more.setOnClickListener {
            val sheet = MoreActionFragment()
            activity?.let { it -> sheet.show(it.supportFragmentManager, "MoreActionFragment")}
        }

        btn_lihat_komentar.setOnClickListener {
            goToCommentApplicant()
        }

        btn_lihat_history.setOnClickListener {
            goToHistoryApplicant()
        }

        btn_lihat_record.setOnClickListener {
            goToRecordApplicant()
        }

        btn_ganti_status.setOnClickListener {
            goToChangeStatus()
        }
    }

    override fun goToCommentApplicant() {
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.replace(id, KomentarApplicantFragment(), "CommentApplicant")
        ft.addToBackStack("CommentApplicant")
        ft.commit()
    }

    override fun goToHistoryApplicant() {
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.replace(id, HistoryFragment(), "HistoryApplicant")
        ft.addToBackStack("HistoryApplicant")
        ft.commit()
    }

    override fun goToRecordApplicant() {
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.replace(id, RecordsFragment(), "RecordApplicant")
        ft.addToBackStack("RecordApplicant")
        ft.commit()
    }

    override fun goToChangeStatus() {
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.replace(id, StatusPageFragment(), "ChangeStatus")
        ft.addToBackStack("ChangeStatus")
        ft.commit()
    }
}

interface OnFragmentClickListener {
    fun goToCommentApplicant()
    fun goToHistoryApplicant()
    fun goToRecordApplicant()
    fun goToChangeStatus()
}