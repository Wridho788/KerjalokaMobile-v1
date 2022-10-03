package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.model.*
import com.ciptakerjaarunika.kerjaloka.MainActivity
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.companyAddJob.AddJobAPI
import com.ciptakerjaarunika.kerjaloka.databinding.ActivityAddJobBinding
import com.ciptakerjaarunika.kerjaloka.model.Data.LocationFilter
import com.ciptakerjaarunika.kerjaloka.model.Data.SkillFilter
import com.ciptakerjaarunika.kerjaloka.model.Data.Title
import com.ciptakerjaarunika.kerjaloka.model.Job.JobLocation
import java.math.BigDecimal

class AddJobActivity : AppCompatActivity(), iAddJob {

    private lateinit var binding: ActivityAddJobBinding
    var getPosition: String? = ""
    var getLocation: List<LocationFilter>? = listOf()
    var getjobType: Int? = 0
    var getminSalary: BigDecimal? = null
    var getmaxSalary: BigDecimal? = null
    var getjobSkills: List<SkillFilter>? = listOf()
    var getjobTitle: List<Title>? = listOf()
    var getjobField: Int? = 0
    var getjobRole: Int? = 0
    var getminExperience: Int? = 0
    var getjobExperienceLevelNo: Int? = 0
    var getjobDescription: String? = ""
    var getjobTests: List<JobTest>? = listOf()
    var getjobShortQuestion: List<JobShortQuestionDto>? = listOf()
    var getautoReject: Boolean? = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddJobBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.backButton.setOnClickListener {
            val intentToMain = Intent(baseContext, MainActivity::class.java)
            startActivity(intentToMain)
        }

        binding.btnPostingPekerjaan.setOnClickListener {
            SendAddJob()
        }
        replaceFragment(fragment_company_add_jobs_1(this@AddJobActivity))
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = this.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentHolder, fragment)
        fragmentTransaction.commit()
    }


    fun SendAddJob() {
        try {
            if (getPosition != null) {
                Log.d("addJob position", getPosition!!)
            } else {
                Toast.makeText(baseContext, "Posisi lowongan harus diisi", Toast.LENGTH_SHORT)
                    .show()
            }

            if (getLocation!!.isEmpty()) {
                listOf(getLocation)
            } else {
                Log.d("addJob location", getLocation.toString())
            }
            if (getjobType != null) {
                Log.d("addJob type", getjobType.toString())
            } else {
                getjobType = 0
            }
            if (getjobSkills != null) {
                Log.d("addJob skills", getjobSkills.toString())
            }

            AddJobAPI().AddJob(
                baseContext,
                addJobRequest(
                    Position = getPosition!!,
                    Location = getLocation!!.map { item ->
                        JobLocation(
                            item.locationsNo,
                            item.city
                        )
                    },
                    JobType = getjobType!!,
                    MinSalary = getminSalary,
                    MaxSalary = getmaxSalary,
                    JobSkills = getjobSkills!!.map { item ->
                        JobSkillRequest(
                            0,
                            0,
                            item.skillNo.toLong()
                        )
                    },
                    JobTitles = getjobTitle!!.map { item ->
                        JobTitleRequest(
                            0,
                            0,
                            item.titleNo.toLong()
                        )
                    },
                    JobField = getjobField!!,
                    JobRole = getjobRole!!,
                    MinExperience = getminExperience!!.toInt(),
                    JobExperienceLevelNo = getjobExperienceLevelNo!!,
                    JobDescription = getjobDescription!!,
                    JobTest = getjobTests!!,
                    JobShortQuestion = getjobShortQuestion!!,
                    AutoReject = getautoReject,
                )
            ) {
                Log.d("addJob response", it.toString())
            }

        } catch (e: IllegalStateException) {
            Log.d("addJobErr", e.toString())
        }

    }

    override fun addPosition(position: String?) {
        getPosition = position!!
    }

    override fun addSalary(minSalary: String?) {
        var salary = BigDecimal.valueOf(minSalary!!.toDouble())
        getminSalary = salary
        getmaxSalary = salary
    }

    override fun addJobPage1(
        location: List<LocationFilter>?,
        jobType: Int?,
    ) {
        getLocation = location!!
        getjobType = jobType!!
    }


    override fun addJobPage2(
        jobSkills: List<SkillFilter>?,
        jobTitles: List<Title>?,
        jobField: Int?,
        jobExperienceLevelNo: Int?,
        jobRole: Int?,
    ) {
        getjobSkills = jobSkills!!
        getjobTitle = jobTitles!!
        getjobField = jobField!!
        getjobExperienceLevelNo = jobExperienceLevelNo!!
        getjobRole = jobRole!!
    }

    override fun addMinExperience(minExperience: String?) {
        getminExperience = minExperience!!.toInt()
    }

    override fun addJobPage3(jobDescription: String?) {
        getjobDescription = jobDescription!!
    }
}

interface iAddJob {
    fun addPosition(position: String?)
    fun addSalary(minSalary: String?)
    fun addJobPage1(
        location: List<LocationFilter>?,
        jobType: Int?,
    )

    fun addJobPage2(
        jobSkills: List<SkillFilter>?,
        jobTitles: List<Title>?,
        jobField: Int?,
        jobExperienceLevelNo: Int?,
        jobRole: Int?,
    )

    fun addMinExperience(
        minExperience: String?
    )

    fun addJobPage3(
        jobDescription: String?
    )
}
