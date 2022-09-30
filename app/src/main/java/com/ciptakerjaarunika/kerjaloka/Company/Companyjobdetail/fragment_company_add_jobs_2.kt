package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.Bottomsheet.*
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentCompanyAddJobs2Binding

class fragment_company_add_jobs_2 : Fragment(), iUpdatePage2 {
    private lateinit var binding: FragmentCompanyAddJobs2Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCompanyAddJobs2Binding.inflate(layoutInflater)
        val view = binding.root

        binding.backButton.setOnClickListener {
            replaceFragment(fragment_company_add_jobs_1())
        }
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.btnPostingPekerjaan.setOnClickListener {
            Toast.makeText(context, "Posting Pekerjaan", Toast.LENGTH_SHORT).show()
        }
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
        binding.compnyExperience.text.toString()
        binding.compnyJobField.text.toString()
        binding.compnyJobPosition.text.toString()
        binding.compnyMinExperience.text.toString()

        binding.btnKembaliCmpny.setOnClickListener {
            replaceFragment(fragment_company_add_jobs_1())
        }
        binding.btnSelanjutnyaCmpny.setOnClickListener {
            replaceFragment(fragment_company_add_jobs_3())
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
        val sheet = BottomSheetMajorJob()
        activity?.let { it ->
            sheet.show(it.supportFragmentManager, "majorModal")
        }
    }

    fun experienceLevelModal() {
        val sheet = BottomSheetExperienceLevelJob()
        activity?.let { it ->
            sheet.show(it.supportFragmentManager, "experienceLevelModal")
        }
    }

    fun fieldModal() {
        val sheet = BottomSheetFieldsJob()
        activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "fieldModal") }
    }

    fun roleModal() {
        val sheet = BottomSheetJobRole()
        activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "rolemodal") }
    }

    override fun updateSkill(skill: String) {
        val skill_job = view?.findViewById<TextView>(R.id.compny_skill)
        skill_job?.text = skill
    }
}

interface iUpdatePage2 {
    fun updateSkill(skill: String)
}