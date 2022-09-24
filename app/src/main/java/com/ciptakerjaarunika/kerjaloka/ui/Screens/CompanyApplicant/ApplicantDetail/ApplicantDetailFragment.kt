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
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentApplicantDetailBinding
import com.ciptakerjaarunika.kerjaloka.enum.DocumentType
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail.SectionEducations.EducationsAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail.SectionExperiences.ExperiencesAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail.SectionHistory.HistoryFragment
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail.SectionKomentar.KomentarApplicantFragment
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail.SectionKomentar.Model.CommentModel
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail.SectionRecords.RecordsFragment
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail.SectionStatusPage.StatusPageFragment
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.JobApplicant.Model.applicantModel
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.JobApplicant.Model.jobApplicantHistory
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyCompareJobseeker.CompanyCompareJobseekerFragment
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobseekerReview.JobseekerReviewFragment
import com.google.android.material.chip.Chip
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ApplicantDetailFragment(private val applicantDetail: applicantModel) : Fragment(),
    OnFragmentClickListener {

    private lateinit var binding: FragmentApplicantDetailBinding
    private lateinit var application: applicantModel
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

        val toolbar = view.findViewById<ImageView>(R.id.btn_back_applicant)
        toolbar.setOnClickListener {
            activity?.onBackPressed()
        }
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)

        val experienceJob = applicantDetail.applicant.experiences
        val beginat = experienceJob.sortedByDescending { item -> item.experienceBeginAt }
        val endingAt = experienceJob.sortedByDescending { item -> item.experienceEndedAt }

        val education = applicantDetail.applicant.education
        val educationBegin = education.sortedByDescending { item -> item.educationBeginAt }
        val educationEnded = education.sortedByDescending { item -> item.educationEndedAt }

        val preferencesJob = applicantDetail.applicant.preferenceJobType.map {
            it.jobTypeName
        }

        val vaccinated = applicantDetail.applicant.documents.filter {
            item ->
            item.documentTypeNo == DocumentType.Vaccine3.value ||
            item.documentTypeNo == DocumentType.Vaccine2.value ||
            item.documentTypeNo == DocumentType.Vaccine1.value
        }

        if (vaccinated != null) {
            Log.d("vaccinated", vaccinated.toString())
            if (vaccinated.size == DocumentType.Vaccine3.value) {
                binding.iconVaccine.setImageResource(R.drawable.ic_vaccine_green)
                binding.vaccineStatus.text = "Vaksin 3"
            } else if (vaccinated.size == DocumentType.Vaccine2.value) {
                binding.iconVaccine.setImageResource(R.drawable.ic_vaccine_pending)
                binding.vaccineStatus.text = "Vaksin 2"
            } else if (vaccinated.size == DocumentType.Vaccine1.value) {
                binding.iconVaccine.setImageResource(R.drawable.ic_vaccine_pending)
                binding.vaccineStatus.text = "Vaksin 1"
            } else {
                binding.iconVaccine.setImageResource(R.drawable.ic_vaccine_reject)
                binding.vaccineStatus.text =
                    "Jobseeker ini belum melakukan vaksinasi atau belum melengkapi status vaksinasi"
            }
        } else {
            Log.d("vaccinated", "Not Vaccine")
            binding.iconVaccine.setImageResource(R.drawable.ic_vaccine_reject)
            binding.vaccineStatus.text =
                "Jobseeker ini belum melakukan vaksinasi atau belum melengkapi status vaksinasi"
        }

        if (applicantDetail.applicant.record != null) {
            val recordlist = applicantDetail.applicant.record.map { it.name }
            Log.d("record", recordlist.toString())
        } else {
            binding.layoutRecord.visibility = View.GONE
        }

        if (applicantDetail.papiKostickResult != null) {
            Log.d("papikostik", "papikostick_result")
        } else {
            binding.txtDescPapiKostick.text = "Jobseeker ini belum menyelesaikan tes PAPI Kostick"
            binding.dateResultPapokostick.visibility = View.GONE
            binding.btnLihatHasilTesApplicant.visibility = View.GONE
        }
        Glide.with(this)
            .load(applicantDetail.applicant.photo)
            .fitCenter().into(binding.headerApplicantDetail.profileApplicant)

        binding.nameApplicant.text = applicantDetail.applicant.name
        binding.headerApplicantDetail.applicantName.text = applicantDetail.applicant.name
        binding.headerApplicantDetail.applicantLocation.text =
            applicantDetail.applicant.location.city + ", " + applicantDetail.applicant.location.province
        binding.txtAlasanMelamar.text = applicantDetail.application.message

        val beginYear = LocalDateTime.parse(beginat[0].experienceBeginAt)
            .format(DateTimeFormatter.ofPattern("MMMM yyyy"))

        val beginEndYear =
            LocalDateTime.parse(endingAt[0].experienceEndedAt).format(DateTimeFormatter.ofPattern("MMMM yyyy"))

        val beginYearEducation =
            LocalDateTime.parse(educationBegin[0].educationBeginAt).format(DateTimeFormatter.ofPattern("MMMM yyyy"))

        val endedYearEducation =
            LocalDateTime.parse(educationEnded[0].educationEndedAt).format(DateTimeFormatter.ofPattern("MMMM yyyy"))

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

        if (applicantDetail.ownRating.conRating == null && applicantDetail.ownRating.proRating == null) {
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

        binding.typeJob.text = preferencesJob.toString()

        Log.d("comment", applicantDetail.comment.toString())

        binding.btnLihatKomentar.setOnClickListener {
            goToCommentApplicant(applicantDetail.comment, applicantDetail.applicant.jobseekerNo)
        }


        Log.d("job application", applicantDetail.jobApplicationHistory.toString())

        binding.btnLihatSejarah.setOnClickListener {
            goToHistoryApplicant(applicantDetail.jobApplicationHistory)
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
//        btn_result_papikostick.setOnClickListener {
//            val sheet = PapikostikResultFragment()
//            activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "ResultPapikostick") }
//        }

//        btn_more.setOnClickListener {
//            val sheet = MoreActionFragment()
//            activity?.let { it -> sheet.show(it.supportFragmentManager, "MoreActionFragment")}
//        }

//        btn_lihat_history.setOnClickListener {
//            goToHistoryApplicant()
//        }

//        btn_lihat_record.setOnClickListener {
//            goToRecordApplicant()
//        }

        binding.headerApplicantDetail.btnChangeStatus.setOnClickListener {
            goToChangeStatus()
        }

//        btn_lihat_review.setOnClickListener {
//            goToReview()
//        }

//        btn_portofolio.setOnClickListener {
//            goToCompareJobseeker()
//        }
    }

    override fun goToCommentApplicant(commentList: List<CommentModel>, jobseekerNo: Long) {
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.replace(id, KomentarApplicantFragment(commentList, jobseekerNo), "CommentApplicant")
        ft.addToBackStack("CommentApplicant")
        ft.commit()
    }

    override fun goToHistoryApplicant(jobApplicantHistory: List<List<jobApplicantHistory>>) {
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.replace(id, HistoryFragment(jobApplicantHistory), "HistoryApplicant")
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
    fun goToCommentApplicant(commentList: List<CommentModel>, jobseekerNo: Long)
    fun goToHistoryApplicant(jobApplicantHistory: List<List<jobApplicantHistory>>)
    fun goToRecordApplicant()
    fun goToChangeStatus()
    fun goToReview()
    fun goToCompareJobseeker()
}