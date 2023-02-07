package com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Companyjobdetail

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.JobAPI
import com.ciptakerjaarunika.kerjaloka.api.companyAddJob.AddJobAPI
import com.ciptakerjaarunika.kerjaloka.databinding.ActivityAddJobBinding
import com.ciptakerjaarunika.kerjaloka.model.Data.JobType
import com.ciptakerjaarunika.kerjaloka.model.Job.CompanyJobDetail.*
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Companyjobdetail.Bottomsheet.BottomSheetConfirm
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Companyjobdetail.ManageJobPage.*
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Companyjobdetail.model.addJobRequest
import com.google.gson.Gson

class ManageJobActivity : AppCompatActivity(), iBasicInfoPage, iAddidiontalInfoPage, iConfirmPage {
    private var JobDetailData: CompanyJobDetail = CompanyJobDetail(
        null, null, null, listOf(), null, null,
        null, listOf(), null, null, null, null, null, null, listOf(), listOf(),
        listOf(), listOf(), null, null, null, null
    )

    private lateinit var binding: ActivityAddJobBinding

    private var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddJobBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        val jobNo = Gson().fromJson(bundle?.getString("jobNo"), Long::class.java)

        if (jobNo != null) {
            JobAPI().GetCompanyJobDetail(jobNo, baseContext) {
                if (!it.data.isEmpty()) {
                    JobDetailData = it.data[0]
                    updatePage()
                }
            }
        } else {
            updatePage()
        }
        binding.prevBtn.setOnClickListener {
            prevPage()
        }
        binding.nextBtn.setOnClickListener {
            nextPage()
        }

        binding.backButton.setOnClickListener {
            val sheet = BottomSheetConfirm(this@ManageJobActivity)
            this.let { it1 -> sheet.show(it1.supportFragmentManager, "confirm") }
        }

        binding.btnPostingPekerjaan.setOnClickListener {
            if (JobDetailData.jobPosition.isNullOrEmpty()) {
                page = 1
                updatePage()
                showMessage("Posisi lowongan tidak boleh kosong")
            } else if (JobDetailData.jobLocation?.isEmpty() == true) {
                page = 1
                updatePage()
                showMessage("Lokasi lowongan tidak boleh kosong")
            } else if (JobDetailData.jobType == null) {
                page = 1
                updatePage()
                showMessage("Tipe pekerjaan tidak boleh kosong")
            } else if (JobDetailData.jobSkills?.isEmpty() == true) {
                page = 2
                updatePage()
                showMessage("Skill tidak boleh kosong")
            } else {
                SendJob()
            }
        }
    }

    fun showMessage(message: String) {
        Toast.makeText(baseContext, message, Toast.LENGTH_SHORT).show()
    }


    @SuppressLint("LogNotTimber")
    fun SendJob() {
        try {
            AddJobAPI().SendJob(
                baseContext,
                addJobRequest(
                    JobDetailData.jobNo?.toLong(),
                    JobDetailData.jobPosition,
                    JobDetailData.jobLocation,
                    JobDetailData.jobType!!.jobTypeNo,
                    JobDetailData.jobSalaryMin,
                    JobDetailData.jobSalaryMax,
                    JobDetailData.jobSkills,
                    JobDetailData.jobTitle,
                    JobDetailData.jobField?.fieldNo,
                    JobDetailData.jobRole?.fieldNo,
                    JobDetailData.jobMinExperience,
                    JobDetailData.jobExperienceLevel?.experienceLevelNo,
                    JobDetailData.jobDescription,
                    JobDetailData.jobTest,
                    JobDetailData.jobShortQuestion,
                    false
                )
            ) {
                if (it != null) {
                    if (it.code == "210") {
                        finish()
                    } else {
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this, "err", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: IllegalStateException) {
            Log.d("addJobErr", e.toString())
        }
    }


    fun nextPage() {
        if (page < 5) {
            this.page += 1
            updatePage()
        }
    }

    fun prevPage() {
        if (page > 1) {
            this.page -= 1
            updatePage()

        }
    }

    fun updatePage() {
        binding.prevBtn.elevation = 10F
        binding.nextBtn.elevation = 10F
        binding.prevBtn.setStrokeColorResource(R.color.danger_500)
        binding.nextBtn.setStrokeColorResource(R.color.danger_500)
        when (page) {
            1 -> {
                binding.prevBtn.elevation = 0F
                binding.prevBtn.setStrokeColorResource(R.color.danger_300)
                replaceFragment(BasicInfoPage(JobDetailData, this))
            }
            2 -> {
                replaceFragment(AdditionalInfoPage(JobDetailData, this))
            }
            3 -> {
                replaceFragment(DescriptionPage(JobDetailData.jobDescription, this))
            }
            4 -> {
                replaceFragment(TestPage(JobDetailData, this))
            }
            5 -> {
                binding.nextBtn.elevation = 0F
                binding.nextBtn.setStrokeColorResource(R.color.danger_300)
                replaceFragment(ShortQuestionPage(JobDetailData, this))
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = this.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentHolder, fragment)
        fragmentTransaction.commit()
    }

    override fun updateJobPosition(value: String) {
        JobDetailData.jobPosition = value
    }

    override fun updateJobDescription(value: String) {
        JobDetailData.jobDescription = value
    }

    override fun updateJobSalary(value: Int?, valueMax: Int?) {
        JobDetailData.jobSalaryMin = value
        JobDetailData.jobSalaryMax = valueMax
    }

    override fun updateJobType(value: JobType) {
        JobDetailData.jobType = value
    }

    override fun updateJobLocation(value: List<JobLocation>) {
        JobDetailData.jobLocation = value
    }

    override fun updateJobTitle(value: List<JobTitle>) {
        JobDetailData.jobTitle = value
    }

    override fun updateSkill(value: List<JobSkill>) {
        JobDetailData.jobSkills = value
    }

    override fun updateField(value: JobField) {
        JobDetailData.jobField = value
    }

    override fun updateRole(value: JobRole) {
        JobDetailData.jobRole = value
    }

    override fun updateMinExperience(value: Int?) {
        JobDetailData.jobMinExperience = value
    }

    override fun updateExperienceLevel(value: JobExperienceLevel) {
        JobDetailData.jobExperienceLevel = value
    }

    override fun updateJobTest(value: List<JobTest>) {
        JobDetailData.jobTest = value
    }

    override fun updateJobShortQuestion(value: List<JobShortQuestion>) {
        JobDetailData.jobShortQuestion = value
    }

    override fun deletePage() {
        finish()
    }

    override fun draftJob() {
        try {
            AddJobAPI().draftJob(
                baseContext,
                addJobRequest(
                    JobDetailData.jobNo?.toLong(),
                    JobDetailData.jobPosition,
                    JobDetailData.jobLocation,
                    JobDetailData.jobType!!.jobTypeNo,
                    JobDetailData.jobSalaryMin,
                    JobDetailData.jobSalaryMax,
                    JobDetailData.jobSkills,
                    JobDetailData.jobTitle,
                    JobDetailData.jobField?.fieldNo,
                    JobDetailData.jobRole?.fieldNo,
                    JobDetailData.jobMinExperience,
                    JobDetailData.jobExperienceLevel?.experienceLevelNo,
                    JobDetailData.jobDescription,
                    JobDetailData.jobTest,
                    JobDetailData.jobShortQuestion,
                    false
                )
            ) {
                if (it != null) {
                    if (it.code == "210") {
                        finish()
                    }
                } else {
                    Toast.makeText(this, "err", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: IllegalStateException) {
            Log.d("addJobErr", e.toString())
        }
    }
}

interface iAddidiontalInfoPage {
    fun updateSkill(value: List<JobSkill>)
    fun updateJobTitle(value: List<JobTitle>)
    fun updateField(value: JobField)
    fun updateRole(value: JobRole)
    fun updateMinExperience(value: Int?)
    fun updateExperienceLevel(value: JobExperienceLevel)
    fun updateJobTest(value: List<JobTest>)
    fun updateJobShortQuestion(value: List<JobShortQuestion>)
}

interface iBasicInfoPage {
    fun updateJobDescription(value: String)
    fun updateJobPosition(value: String)
    fun updateJobSalary(value: Int?, valueMax: Int?)
    fun updateJobType(jobTypeNo: JobType)
    fun updateJobLocation(value: List<JobLocation>)
}

interface iConfirmPage {
    fun deletePage()
    fun draftJob()
}
