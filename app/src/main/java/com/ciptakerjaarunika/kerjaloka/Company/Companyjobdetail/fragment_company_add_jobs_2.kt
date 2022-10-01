package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.Bottomsheet.*
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentCompanyAddJobs2Binding
import com.ciptakerjaarunika.kerjaloka.model.Data.SkillFilter
import com.ciptakerjaarunika.kerjaloka.model.Data.Title

class fragment_company_add_jobs_2(val iAddJob: iAddJob) : Fragment(), iUpdatePage2 {
    private lateinit var binding: FragmentCompanyAddJobs2Binding
    var getSkills: List<SkillFilter>? = listOf()
    var getMajors: List<Title>? = listOf()
    var getRoles: Int? = 0
    var getField: Int? = 0
    var getExperienceLevels: Int? = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCompanyAddJobs2Binding.inflate(layoutInflater)
        val view = binding.root

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.btnChooseSkill.setOnClickListener {
            skillModal()
        }

        binding.btnChooseMajor.setOnClickListener {
            majorModal()
        }

        binding.btnChooseExperience.setOnClickListener {
            experienceLevelModal()
        }

        binding.btnChooseJobField.setOnClickListener {
            fieldModal()
        }

        binding.btnChooseJobPosition.setOnClickListener {
            roleModal()
        }

        binding.compnySkill.text.toString()
        binding.compnyMajor.text.toString()
        binding.compnyJobField.text.toString()
        binding.compnyJobPosition.text.toString()

        binding.btnKembaliCmpny.setOnClickListener {
            replaceFragment(fragment_company_add_jobs_1(iAddJob))
        }
        iAddJob.addJobPage2(
            getSkills!!, getMajors!!, getField!!, getExperienceLevels!!, getRoles!!, binding.compnyMinExperience.text.toString(),
        )
        binding.btnSelanjutnyaCmpny.setOnClickListener {

            replaceFragment(fragment_company_add_jobs_3(this.iAddJob))
        }
        return view
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(id, fragment)
        fragmentTransaction?.commit()
    }

    fun skillModal() {
        val sheet = BottomSheetSkillJob(this)
        activity?.let { it ->
            sheet.show(it.supportFragmentManager, "skill")
        }
    }

    fun majorModal() {
        val sheet = BottomSheetMajorJob(this)
        activity?.let { it ->
            sheet.show(it.supportFragmentManager, "majorModal")
        }
    }

    fun experienceLevelModal() {
        val sheet = BottomSheetExperienceLevelJob(this)
        activity?.let { it ->
            sheet.show(it.supportFragmentManager, "experienceLevelModal")
        }
    }

    fun fieldModal() {
        val sheet = BottomSheetFieldsJob(this)
        activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "fieldModal") }
    }

    fun roleModal() {
        val sheet = BottomSheetJobRole(this)
        activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "rolemodal") }
    }

    override fun updateSkill(skill: List<SkillFilter>) {
        skill.map { skill -> binding.compnySkill.text = skill.skillName }
        getSkills = skill
    }

    override fun updateMajor(major: List<Title>) {
        major.map { map -> binding.compnyMajor.text = map.titleName }
        getMajors = major
    }

    override fun updateExperience(experience: String, experienceLevelNo: Int) {
        binding.compnyExperience.text = experience
        getExperienceLevels = experienceLevelNo
    }

    override fun updateField(field: String, fieldNo: Int) {
        binding.compnyJobField.text = field
        getField = fieldNo
    }

    override fun updateRole(role: String, roleNo: Int) {
        binding.compnyJobPosition.text = role
        getRoles = roleNo
    }
}

interface iUpdatePage2 {
    fun updateSkill(skill: List<SkillFilter>)
    fun updateMajor(major: List<Title>)
    fun updateExperience(experience: String, experienceLevelNo: Int)
    fun updateField(field: String, fieldNo: Int)
    fun updateRole(role: String, roleNo: Int)
}