package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentApplicantDetailBinding
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail.SectionHistory.HistoryFragment
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail.SectionKomentar.KomentarApplicantFragment
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail.SectionRecords.RecordsFragment
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail.SectionStatusPage.StatusPageFragment
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.JobApplicant.Model.applicantModel
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyCompareJobseeker.CompanyCompareJobseekerFragment
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobseekerReview.JobseekerReviewFragment
import java.time.format.DateTimeFormatter

class ApplicantDetailFragment(private val applicantDetail: applicantModel) : Fragment(),
    OnFragmentClickListener {

    private lateinit var binding: FragmentApplicantDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentApplicantDetailBinding.inflate(layoutInflater)
        val view = binding.root
        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        Log.d("tes", applicantDetail.toString())

        val experienceJob = applicantDetail.applicant.experiences
        Log.d("tes", experienceJob.toString())
        val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val toolbar = view.findViewById<ImageView>(R.id.btn_back_applicant)
        toolbar.setOnClickListener {
            activity?.onBackPressed()
        }

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.nameApplicant.text = applicantDetail.applicant.name
        binding.headerApplicantDetail.applicantName.text = applicantDetail.applicant.name
        binding.headerApplicantDetail.applicantLocation.text =
            applicantDetail.applicant.location.city + ", " + applicantDetail.applicant.location.province
        binding.txtAlasanMelamar.text = applicantDetail.application.message
//        binding.headerApplicantDetail.profileApplicant.setImageResource(contextapplicantDetail.applicant.photo)
//        btn_result_papikostick.setOnClickListener {
//            val sheet = PapikostikResultFragment()
//            activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "ResultPapikostick") }
//        }

//        btn_more.setOnClickListener {
//            val sheet = MoreActionFragment()
//            activity?.let { it -> sheet.show(it.supportFragmentManager, "MoreActionFragment")}
//        }

//        btn_lihat_komentar.setOnClickListener {
//            goToCommentApplicant()
//        }

//        btn_lihat_history.setOnClickListener {
//            goToHistoryApplicant()
//        }

//        btn_lihat_record.setOnClickListener {
//            goToRecordApplicant()
//        }

//        btn_ganti_status.setOnClickListener {
//            goToChangeStatus()
//        }

//        btn_lihat_review.setOnClickListener {
//            goToReview()
//        }

//        btn_portofolio.setOnClickListener {
//            goToCompareJobseeker()
//        }
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

    override fun goToReview() {
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.replace(id, JobseekerReviewFragment(), "JobseekerReview")
        ft.addToBackStack("JobseekerReview")
        ft.commit()
    }

    override fun goToCompareJobseeker() {
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.replace(id, CompanyCompareJobseekerFragment(), "CompanyCompareJobseeker")
        ft.addToBackStack("CompanyCompareJobseeker")
        ft.commit()
    }
}

interface OnFragmentClickListener {
    fun goToCommentApplicant()
    fun goToHistoryApplicant()
    fun goToRecordApplicant()
    fun goToChangeStatus()
    fun goToReview()
    fun goToCompareJobseeker()
}