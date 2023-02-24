package com.ciptakerjaarunika.kerjaloka.viewmodel.Company.CompanyApplicant.ApplicantDetail

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentApplicantDetailBinding
import com.ciptakerjaarunika.kerjaloka.enum.ApplicanStatusType
import com.ciptakerjaarunika.kerjaloka.enum.DocumentType
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.CompanyApplicant.ApplicantDetail.Bottomsheet.MoreAction.MoreActionFragment
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.CompanyApplicant.ApplicantDetail.Bottomsheet.PapikostikResultFragment
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.CompanyApplicant.ApplicantDetail.SectionEducations.EducationsAdapter
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.CompanyApplicant.ApplicantDetail.SectionExperiences.ExperiencesAdapter
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.CompanyApplicant.ApplicantDetail.SectionHistory.HistoryFragment
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.CompanyApplicant.ApplicantDetail.SectionKomentar.KomentarApplicantFragment
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.CompanyApplicant.ApplicantDetail.SectionKomentar.Model.CommentModel
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.CompanyApplicant.ApplicantDetail.SectionRecords.RecordsFragment
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.CompanyApplicant.ApplicantDetail.SectionStatusPage.StatusPageFragment
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.CompanyApplicant.CVbanks.iCvBankInterface
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.CompanyApplicant.JobApplicant.Model.applicantModel
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.CompanyApplicant.JobApplicant.Model.jobApplicantHistory
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.CompanyApplicant.JobApplicant.iJobApplicant
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.JobseekerReview.JobseekerReviewFragment
import com.ciptakerjaarunika.kerjaloka.viewmodel.Screens.CompanyCompareJobseeker.CompanyCompareJobseekerFragment
import com.google.android.material.chip.Chip
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class ApplicantDetailFragment(
    private val applicantDetail: applicantModel,
    val iJobApplicant: iJobApplicant?,
    iCvBankInterface: iCvBankInterface?
) : Fragment(), OnFragmentClickListener {

    private lateinit var binding: FragmentApplicantDetailBinding
    private lateinit var application: applicantModel
    private var downloadManager: DownloadManager? = null
    private var fileName: String? = null
    private var downLoadId: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentApplicantDetailBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    @SuppressLint("ResourceType", "SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = view.findViewById<ImageView>(R.id.btn_back_applicant)
        toolbar.setOnClickListener {
            fragmentManager?.popBackStack()
        }
        if (applicantDetail.application.applicationStatusNo == ApplicanStatusType.ShortList.value) {
            binding.headerApplicantDetail.txtStatus.text = "Terpilih"
        } else if (applicantDetail.application.applicationStatusNo == ApplicanStatusType.Test.value) {
            binding.headerApplicantDetail.txtStatus.text = "Dalam Test"
        } else if (applicantDetail.application.applicationStatusNo == ApplicanStatusType.Interview.value) {
            binding.headerApplicantDetail.txtStatus.text = "Interview"
        } else if (applicantDetail.application.applicationStatusNo == ApplicanStatusType.Accepted.value) {
            binding.headerApplicantDetail.txtStatus.text = "Diterima"
        } else if (applicantDetail.application.applicationStatusNo == ApplicanStatusType.Rejected.value) {
            binding.headerApplicantDetail.txtStatus.text = "Ditolak"
        } else {
            binding.headerApplicantDetail.txtStatus.text = "CV Bank"
        }

        val experienceJob = applicantDetail.applicant.experiences
        if (experienceJob != null) {
            if (experienceJob.isEmpty() == true) {
                binding.headerApplicantDetail.sectionExperience.visibility = View.GONE
            } else if (experienceJob.isEmpty() != true) {
                val beginat = experienceJob.sortedByDescending { item -> item.experienceBeginAt }
                val endingAt = experienceJob.sortedByDescending { item -> item.experienceEndedAt }

                val beginYear = LocalDateTime.parse(beginat[0].experienceBeginAt)
                    .format(DateTimeFormatter.ofPattern("MMMM yyyy"))
                if (endingAt != null) {
                    if (endingAt[0].experienceEndedAt != null) {
                        val beginEndYear = LocalDateTime.parse(endingAt[0].experienceEndedAt)
                            .format(DateTimeFormatter.ofPattern("MMMM yyyy "))
                        binding.headerApplicantDetail.experienceJobText.text =
                            beginat[0].experiencePosition + " - " + endingAt[0].experienceCompanyName
                        binding.headerApplicantDetail.experienceYearText.text =
                            "$beginYear - $beginEndYear"
                    } else {
                        binding.headerApplicantDetail.experienceYearText.text =
                            "$beginYear - Sekarang"
                    }

                } else {
                    binding.headerApplicantDetail.experienceJobText.text =
                        beginat[0].experiencePosition + " - " + endingAt[0].experienceCompanyName
                    binding.headerApplicantDetail.experienceYearText.text = "$beginYear - Sekarang"
                }
            }
        } else {
            binding.headerApplicantDetail.sectionExperience.visibility = View.GONE
        }
        val education = applicantDetail.applicant.education
        if (education != null) {
            if (education.isEmpty() == true) {
                binding.headerApplicantDetail.sectionEducation.visibility = View.GONE
            } else if (education.isNotEmpty() == true) {
                val educationBegin = education.sortedByDescending { item -> item.educationBeginAt }
                val educationEnded = education.sortedByDescending { item -> item.educationEndedAt }

                val beginYearEducation = LocalDateTime.parse(educationBegin[0].educationBeginAt)
                    .format(DateTimeFormatter.ofPattern("MMMM yyyy"))
                if (educationEnded != null) {
                    val endedYearEducation = LocalDateTime.parse(educationEnded[0].educationEndedAt)
                        .format(DateTimeFormatter.ofPattern("MMMM yyyy"))
                    binding.headerApplicantDetail.educationYearText.text =
                        "$beginYearEducation - $endedYearEducation"

                    binding.headerApplicantDetail.educationNameText.text =
                        educationBegin[0].educationMajorName + " - " + educationBegin[0].educationSchool
                } else {
                    binding.headerApplicantDetail.educationNameText.text =
                        educationBegin[0].educationMajorName + " - " + educationBegin[0].educationSchool
                    binding.headerApplicantDetail.educationYearText.text =
                        "$beginYearEducation - Sekarang"
                }
            }
        } else {
            binding.headerApplicantDetail.sectionEducation.visibility = View.GONE
        }
        val preferencesJob = applicantDetail.applicant.preferenceJobType.map {
            it.jobTypeName
        }

        val vaccinated = applicantDetail.applicant.documents.filter { item ->
            item.documentTypeNo == DocumentType.Vaccine3.value || item.documentTypeNo == DocumentType.Vaccine2.value || item.documentTypeNo == DocumentType.Vaccine1.value
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
            binding.iconVaccine.setImageResource(R.drawable.ic_vaccine_reject)
            binding.vaccineStatus.text =
                "Jobseeker ini belum melakukan vaksinasi atau belum melengkapi status vaksinasi"
        }

        if (applicantDetail.applicant.record != null) {
            val recordlist = applicantDetail.applicant.record.map { it.name }
        } else {
            binding.layoutRecord.visibility = View.GONE
        }

        if (applicantDetail.papiKostickResult != null) {
            binding.btnLihatHasilTesApplicant.setOnClickListener {
                goToPapikostikModal()
            }
        } else {
            binding.txtDescPapiKostick.text = "Jobseeker ini belum menyelesaikan tes PAPI Kostick"
            binding.dateResultPapokostick.visibility = View.GONE
            binding.btnLihatHasilTesApplicant.visibility = View.GONE
        }

        Glide.with(this)
            .load(config().portAddress + "photo/Profile/" + applicantDetail.applicant.photo)
            .fitCenter().into(binding.headerApplicantDetail.profileApplicant)


        binding.nameApplicant.text = applicantDetail.applicant.name
        binding.headerApplicantDetail.applicantName.text = applicantDetail.applicant.name
        binding.headerApplicantDetail.applicantLocation.text =
            applicantDetail.applicant.location.city + ", " + applicantDetail.applicant.location.province
        binding.txtAlasanMelamar.text = applicantDetail.application.message

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

        if (applicantDetail.applicant.documents == null) {
            binding.documentlayout.visibility = View.GONE
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
        binding.btnLihatKomentar.setOnClickListener {
            goToCommentApplicant(
                applicantDetail.comment,
                applicantDetail.applicant.jobseekerNo,
                applicantDetail.application.jobNo
            )
        }
        binding.btnLihatSejarah.setOnClickListener {
            goToHistoryApplicant(applicantDetail.jobApplicationHistory)
        }

        initializeDownloadManager()
        if (applicantDetail.applicant.documents != null) {
            applicantDetail.applicant.documents.forEach { data ->
                val chip = Chip(context)
                chip.setChipBackgroundColorResource(R.color.danger_500)
                chip.setTextAppearanceResource(R.color.white)
                chip.apply {
                    textSize = 12f
                    text = data.documentName
                    isChipIconVisible = false
                    isCloseIconVisible = false
                    isClickable = true
                    isFocusable = true
                    rootView.apply {
                        binding.chipDocument.addView(chip as View)
                    }
                }
                chip.setOnClickListener { downloadCV(data.documentFileName, data.documentName) }
            }
        }
        val jobseekerNo = applicantDetail.application.jobseekerNo
//        Log.d("userno", applicantDeta.toString())
        binding.headerApplicantDetail.btnReview.setOnClickListener {
            goToReview(jobseekerNo, applicantDetail)
        }


        binding.headerApplicantDetail.btnMore.setOnClickListener {
            val sheet = MoreActionFragment(
                applicantDetail.applicant.jobseekerNo,
                applicantDetail.application.jobNo,
                applicantDetail,
                iJobApplicant!!
            )
            activity?.let { it -> sheet.show(it.supportFragmentManager, "MoreActionFragment") }
        }

        binding.headerApplicantDetail.btnChangeStatus.setOnClickListener {
            goToChangeStatus()
        }
    }

    private fun initializeDownloadManager() {
        downloadManager = activity?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager?
        fileName = "Document"
    }

    fun downloadCV(fileName: String, documentName: String) {
        val request = DownloadManager.Request(
            Uri.parse(
                "${config().portAddress}/document/download?fileName=${fileName}&documentName=${documentName}"
            )
        )
        request.setTitle(documentName).setDescription("File is downloading...")
            .setDestinationInExternalFilesDir(
                context, Environment.DIRECTORY_DOWNLOADS, fileName
            ).setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)

        downLoadId = downloadManager!!.enqueue(request)
    }

    override fun goToCommentApplicant(
        commentList: List<CommentModel>, jobseekerNo: Long, jobNo: Long
    ) {
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.replace(
            id, KomentarApplicantFragment(
                commentList, applicantDetail.application.applicationNo, jobseekerNo, jobNo
            ), "CommentApplicant"
        )
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
        ft.replace(
            id, StatusPageFragment(
                applicantDetail.application.applicationNo,
                applicantDetail.applicant.name,
                applicantDetail.applicant.email,
                applicantDetail.application.applicationStatusNo
            ), "ChangeStatus"
        )
        ft.addToBackStack("ChangeStatus")
        ft.commit()
    }

    override fun goToReview(jobseekerNo: Long, applicantDetail: applicantModel) {
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.replace(id, JobseekerReviewFragment(jobseekerNo, applicantDetail), "JobseekerReview")
        ft.addToBackStack("JobseekerReview")
        ft.commit()
    }

    override fun goToCompareJobseeker() {
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.replace(id, CompanyCompareJobseekerFragment(), "CompanyCompareJobseeker")
        ft.addToBackStack("CompanyCompareJobseeker")
        ft.commit()
    }

    override fun goToPapikostikModal() {
        val sheet = PapikostikResultFragment()
        activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "ResultPapikostick") }
    }
}

interface OnFragmentClickListener {
    fun goToCommentApplicant(commentList: List<CommentModel>, jobseekerNo: Long, jobNo: Long)
    fun goToHistoryApplicant(jobApplicantHistory: List<List<jobApplicantHistory>>)
    fun goToRecordApplicant()
    fun goToChangeStatus()
    fun goToReview(jobseekerNo: Long, applicantDetail: applicantModel)
    fun goToCompareJobseeker()
    fun goToPapikostikModal()
}