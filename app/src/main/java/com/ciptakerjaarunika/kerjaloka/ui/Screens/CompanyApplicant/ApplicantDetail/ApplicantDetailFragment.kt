package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentApplicantDetailBinding
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail.SectionEducations.EducationsAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail.SectionExperiences.ExperiencesAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail.SectionHistory.HistoryFragment
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail.SectionKomentar.KomentarApplicantFragment
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail.SectionRecords.RecordsFragment
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail.SectionStatusPage.StatusPageFragment
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.JobApplicant.Model.applicantModel
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyCompareJobseeker.CompanyCompareJobseekerFragment
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobseekerReview.JobseekerReviewFragment
import com.google.android.material.chip.Chip
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ApplicantDetailFragment(private val applicantDetail: applicantModel) : Fragment(),
    OnFragmentClickListener {

    private lateinit var binding: FragmentApplicantDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentApplicantDetailBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val experienceJob = applicantDetail.applicant.experiences
        val beginat = experienceJob.sortedByDescending { item -> item.experienceBeginAt }
        val endingAt = experienceJob.sortedByDescending { item -> item.experienceEndedAt }

        val education = applicantDetail.applicant.education
        val educationBegin = education.sortedByDescending { item -> item.educationBeginAt }
        val educationEnded = education.sortedByDescending { item -> item.educationEndedAt }
//        val listEducation = List<education>

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

        val time = beginat[0].experienceBeginAt
        val beginYear = LocalDateTime.parse(time)
            .format(DateTimeFormatter.ofPattern("MMMM yyyy"))

        val timeNow = endingAt[0].experienceEndedAt
        val beginEndYear =
            LocalDateTime.parse(timeNow).format(DateTimeFormatter.ofPattern("MMMM yyyy"))

        val convertTimeBegin = educationBegin[0].educationBeginAt
        val beginYearEducation =
            LocalDateTime.parse(convertTimeBegin).format(DateTimeFormatter.ofPattern("MMMM yyyy"))

        val convertTimeEnded = educationEnded[0].educationEndedAt
        val endedYearEducation =
            LocalDateTime.parse(convertTimeEnded).format(DateTimeFormatter.ofPattern("MMMM yyyy"))

        binding.headerApplicantDetail.experienceYearText.text =
            "$beginYear - $beginEndYear"

        binding.headerApplicantDetail.experienceJobText.text =
            beginat[0].experiencePosition + " - " + endingAt[0].experienceCompanyName

        binding.headerApplicantDetail.educationYearText.text =
            "$beginYearEducation - $endedYearEducation"

        binding.headerApplicantDetail.educationNameText.text =
            educationBegin[0].educationMajorName + " - " + educationBegin[0].educationSchool

        binding.rvExperiences.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = ExperiencesAdapter(applicantDetail.applicant.experiences)
        }

        binding.rvEducations.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = EducationsAdapter(applicantDetail.applicant.education)
        }

        binding.expectedSalary.text = "IDR ${applicantDetail.applicant.expectedSalary}"


        binding.headerApplicantDetail.txtRatingApplicant.text =
            if (applicantDetail.ownRating.ownRating == null) ({
                binding.headerApplicantDetail.txtRatingApplicant.text = " - "
            }).toString() else {
                applicantDetail.ownRating.ownRating.toString()
            }

        if(applicantDetail.ownRating.conRating == null && applicantDetail.ownRating.proRating == null) {
            binding.sectionKemampuan.visibility = View.GONE
        }

        if (applicantDetail.ownRating.conRating != null) {
            applicantDetail.ownRating.conRating.forEach {
                val chip = Chip(context)
                chip.setChipBackgroundColorResource(R.color.danger_100)
                chip.apply {
                    textSize = 12f
                    text = it
                    isChipIconVisible = false
                    isCloseIconVisible = false
                    isClickable = false
                    isCheckable = false
                    rootView.apply {
                        binding.chipGroupMenengah.addView(chip as View)
                    }
                }
            }
        } else {
            binding.chipGroupMenengah.visibility = View.GONE
            binding.layoutChipMenengah.visibility = View.GONE
        }


        if (applicantDetail.ownRating.proRating != null) {
            applicantDetail.ownRating.proRating.forEach {
                val chip = Chip(context)
                chip.setChipBackgroundColorResource(R.color.danger_100)
                chip.apply {
                    textSize = 12f
                    text = it
                    isChipIconVisible = false
                    isCloseIconVisible = false
                    isClickable = false
                    isCheckable = false
                    rootView.apply {
                        binding.chipGroupProfessional.addView(chip as View)
                    }
                }
            }
        } else {
            binding.chipGroupProfessional.visibility = View.GONE
            binding.layoutChipProfessional.visibility = View.GONE
        }

//        val record = applicantDetail.applicant.record.isEmpty()
//        if (record) {
//            binding.sectionrecordLayout.descRecords.text = "Pelamar ini memiliki records, cek terlebih dahulu."
//            binding.sectionrecordLayout.btnLihatRecord.setOnClickListener {
//                goToRecordApplicant()
//            }
//        } else {
//            binding.layoutRecord.visibility = View.GONE
//        }
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