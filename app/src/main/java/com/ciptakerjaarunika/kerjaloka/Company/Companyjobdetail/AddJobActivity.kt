package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.model.*
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.companyAddJob.AddJobAPI
import com.ciptakerjaarunika.kerjaloka.databinding.ActivityAddJobBinding
import com.ciptakerjaarunika.kerjaloka.model.Data.LocationFilter
import com.ciptakerjaarunika.kerjaloka.model.Data.SkillFilter
import com.ciptakerjaarunika.kerjaloka.model.Data.Title
import com.ciptakerjaarunika.kerjaloka.model.Job.JobLocation

class AddJobActivity : AppCompatActivity(), iAddJob {

    private lateinit var binding: ActivityAddJobBinding
    var getposition: String? = ""
    var getlocation: List<LocationFilter>? = listOf()
    var getjobType: Int? = 0
    var getminSalary: String? = ""
    var getmaxSalary: String? = ""
    var getjobSkills: List<SkillFilter>? = listOf()
    var getjobTitle: List<Title>? = listOf()
    var getjobField: Int? = 0
    var getjobRole: Int? = 0
    var getminExperience: String? = ""
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
            this.onBackPressed()
        }
        Log.d("addJob",getposition.toString())
        Log.d("addJob",getlocation.toString())
        Log.d("addJob",getjobDescription.toString())
        Log.d("addJob",getmaxSalary.toString())
        Log.d("addJob",getminSalary.toString())
        Log.d("addJob",getjobTests.toString())
        Log.d("addJob",getjobShortQuestion.toString())
        Log.d("addJob",getautoReject.toString())

        binding.btnPostingPekerjaan.setOnClickListener {
        AddJobAPI().AddJob(baseContext, addJobRequest(
                position = getposition!!,
                location = getlocation!!.map {item-> JobLocation(item.locationsNo, item.city) } ,
                jobType = getjobType!!,
                minSalary = getminSalary!!.toBigDecimal(),
                maxSalary = getmaxSalary!!.toBigDecimal(),
                jobSkills = getjobSkills!!.map {item -> JobSkillRequest(0, 0, item.skillNo.toLong()) },
                jobTitle = getjobTitle!!.map{item -> JobTitleRequest(0, 0,  item.titleNo.toLong()) },
                jobField = getjobField!!,
                jobRole = getjobRole!!,
                minExperience = getminExperience!!.toInt(),
                jobExperienceLevelNo = getjobExperienceLevelNo!!,
                jobDescription = getjobDescription!!,
                jobTests = getjobTests!!,
                jobShortQuestion = getjobShortQuestion!!,
                autoReject = false
            )
        ){
                Log.d("add job response", it.toString())
                if(it != null && it.code == "210"){
                    Log.d("add job sukses", it.toString())
                }
            }
        }
        replaceFragment(fragment_company_add_jobs_1(this@AddJobActivity))
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = this.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentHolder, fragment)
        fragmentTransaction.commit()
    }

    override fun addJobPage1(
        position: String,
        location: List<LocationFilter>,
        jobType: Int,
        minSalary: String
    ) {
        Log.d("addJob",position)
        Log.d("addJob",location.toString())
        Log.d("addJob",jobType.toString())
        Log.d("addJob",minSalary)
        if (position != null) {
            getposition = position
        }

        if (location != null) {
            getlocation = location
        }

        if (jobType != null) {
            getjobType = jobType
        }

        if (minSalary != null) {
            getminSalary = minSalary
            getmaxSalary = minSalary
        }
    }

    override fun addJobPage2(
        jobSkills: List<SkillFilter>,
        jobTitles: List<Title>,
        jobField: Int,
        jobExperienceLevelNo: Int,
        jobRole: Int,
        minExperience: String
    ) {
        Log.d("addJob",jobSkills.toString())
        Log.d("addJob",jobTitles.toString())
        Log.d("addJob",jobField.toString())
        Log.d("addJob",jobExperienceLevelNo.toString())
        Log.d("addJob",jobRole.toString())
        Log.d("addJob",minExperience)

        if (jobSkills != null) {
            getjobSkills = jobSkills
        }
        if (jobTitles != null) {
            getjobTitle = jobTitles
        } else { getjobTitle = null}
        if (jobField != null) {
            getjobField = jobField
        } else { getjobField = 0}
        if (jobExperienceLevelNo != 0) {
            getjobExperienceLevelNo = jobExperienceLevelNo
        } else { getjobExperienceLevelNo = 0}
        if (jobRole != null) {
            getjobRole = jobRole
        } else { getjobRole = 0}
        if (minExperience != null) {
            getminExperience = minExperience
        } else { getminExperience = ""}
    }

    override fun addJobPage3(jobDescription: String) {
        Log.d("addJob",jobDescription)
        if (jobDescription != null) {
        getjobDescription = jobDescription
        } else { getjobDescription = null}
    }
}

interface iAddJob {
    fun addJobPage1(
        position: String,
        location: List<LocationFilter>,
        jobType: Int,
        minSalary: String
    )

    fun addJobPage2(
        jobSkills: List<SkillFilter>,
        jobTitles: List<Title>,
        jobField: Int,
        jobExperienceLevelNo: Int,
        jobRole: Int,
        minExperience: String
    )

    fun addJobPage3(
        jobDescription: String
    )
}
