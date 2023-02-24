package com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Companyjobdetail.ManageJobPage

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ciptakerjaarunika.kerjaloka.api.DataAPI
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentAdditionalJobInfoBinding
import com.ciptakerjaarunika.kerjaloka.model.Job.CompanyJobDetail.*
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Companyjobdetail.Bottomsheet.*
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Companyjobdetail.Bottomsheet.Adapter.SelectedSkillAdapter
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Companyjobdetail.Bottomsheet.Adapter.SelectedTitleAdapter
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Companyjobdetail.iAddidiontalInfoPage

class AdditionalInfoPage(val data: CompanyJobDetail, val updateData: iAddidiontalInfoPage) :
    Fragment(),
    iUpdateJobAdditionalInfo {
    private lateinit var binding: FragmentAdditionalJobInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdditionalJobInfoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if (data.jobMinExperience != null) {
            binding.compnyMinExperience.setText(data.jobMinExperience.toString())
        }
        binding.recycleSkill.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = SelectedSkillAdapter(data.jobSkills, this@AdditionalInfoPage)
        }
        binding.recycleEducation.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = SelectedTitleAdapter(data.jobTitle, this@AdditionalInfoPage)
        }
        DataAPI().GetSkill(context) { res ->
            if (res != null) {
                binding.selectSkillBtn.setOnClickListener {
                    val sheet = SkillModal(data.jobSkills, res, this)
                    activity?.let { it1 ->
                        sheet.show(
                            it1.supportFragmentManager,
                            "DemoBottomSheetFragment"
                        )
                    }
                }
            }
        }

        DataAPI().GetTitles(context) { res ->
            if (res != null) {
                binding.selectTitleBtn.setOnClickListener {
                    val sheet = TitleModal(data.jobTitle, res, this)
                    activity?.let { it1 ->
                        sheet.show(
                            it1.supportFragmentManager,
                            "DemoBottomSheetFragment"
                        )
                    }
                }
            }
        }

        DataAPI().GetFields(context) { res ->
            if (res != null) {
                data.jobField?.let { updateField(it) }
                binding.selectFieldBtn.setOnClickListener {
                    val sheet = FieldModal(data.jobField?.fieldNo, res, this)
                    activity?.let { it1 ->
                        sheet.show(
                            it1.supportFragmentManager,
                            "DemoBottomSheetFragment"
                        )
                    }
                }
            }
        }

        DataAPI().GetRoles(context) { res ->
            if (res != null) {
                data.jobRole?.let { updateRole(it) }
                binding.selectRoleBtn.setOnClickListener {
                    val sheet = RoleModal(data.jobRole, res, this)
                    activity?.let { it1 ->
                        sheet.show(
                            it1.supportFragmentManager,
                            "DemoBottomSheetFragment"
                        )
                    }
                }
            }
        }

        DataAPI().GetExperienceLevel(context) { res ->
            if (res != null) {
                data.jobExperienceLevel?.let { updateExperienceLevel(it) }
                binding.selectExperienceLevelBtn.setOnClickListener {
                    val sheet =
                        ExperienceLevelModal(data.jobExperienceLevel?.experienceLevelNo, res, this)
                    activity?.let { it1 ->
                        sheet.show(
                            it1.supportFragmentManager,
                            "DemoBottomSheetFragment"
                        )
                    }
                }
            }
        }

        binding.compnyMinExperience.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                if (binding.compnyMinExperience.text.toString().isNullOrEmpty()
                    || binding.compnyMinExperience.text.toString().toInt() < 0
                ) {
                    updateData.updateMinExperience(null)
                } else
                    updateData.updateMinExperience(
                        binding.compnyMinExperience.text.toString().toInt()
                    )
            }
        })
    }

    override fun updateSkill(value: List<JobSkill>) {
        data.jobSkills = value
        binding.recycleSkill.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = SelectedSkillAdapter(data.jobSkills, this@AdditionalInfoPage)
        }
        binding.recycleSkill.adapter?.notifyDataSetChanged()
        updateData.updateSkill(value)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun updateTitle(value: List<JobTitle>) {
        data.jobTitle = value
        binding.recycleEducation.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = SelectedTitleAdapter(data.jobTitle, this@AdditionalInfoPage)
        }
        binding.recycleEducation.adapter?.notifyDataSetChanged()
        updateData.updateJobTitle(value)
    }

    override fun updateField(value: JobField) {
        data.jobField = value
        binding.fieldTxt.text = data.jobField!!.fieldName
        updateData.updateField(value)
    }

    override fun updateRole(value: JobRole) {
        data.jobRole = value
        binding.roleTxt.text = data.jobRole!!.jobRoleName
        updateData.updateRole(value)
    }

    override fun updateExperienceLevel(value: JobExperienceLevel) {
        data.jobExperienceLevel = value
        binding.experienceLevelTxt.text = data.jobExperienceLevel!!.experienceLevelName
        updateData.updateExperienceLevel(value)
    }

}

interface iUpdateJobAdditionalInfo {
    fun updateSkill(value: List<JobSkill>)
    fun updateTitle(value: List<JobTitle>)
    fun updateField(value: JobField)
    fun updateRole(value: JobRole)
    fun updateExperienceLevel(value: JobExperienceLevel)
}