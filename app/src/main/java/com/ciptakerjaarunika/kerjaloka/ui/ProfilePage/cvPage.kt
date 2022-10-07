package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.`interface`.iRefreshData
import com.ciptakerjaarunika.kerjaloka.api.ManageProfileAPI
import com.ciptakerjaarunika.kerjaloka.api.ProfileAPI
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentProfileCvBinding
import com.ciptakerjaarunika.kerjaloka.model.Profile.JobseekerEducations
import com.ciptakerjaarunika.kerjaloka.model.Profile.JobseekerEducationsRequest
import com.ciptakerjaarunika.kerjaloka.model.Profile.JobseekerExperienceRequest
import com.ciptakerjaarunika.kerjaloka.model.Profile.JobseekerExperiences
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter.EduAdapter
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter.ExpAdapter
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter.LanguageAdapter
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ManageCV.PapikostickResult
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ManageCV.edit_kemampuan
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ManageCV.fragment_manage_cv_edit_education_page
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ManageCV.manage_cv_edit_experience_page
import com.google.android.material.chip.Chip


class cvPage : Fragment(), iRefreshData, iCvPage {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private lateinit var binding : FragmentProfileCvBinding
    private var loading = 4;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileCvBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GetData()
    }

    private fun GetData(){
        binding.chipGroup1.removeAllViews()
        binding.chipGroup2.removeAllViews()
        binding.chipGroup3.removeAllViews()
        binding.chipGroup4.removeAllViews()
        binding.chipGroup5.removeAllViews()

            ProfileAPI().GetJobseekerSkills(context) { skills ->
                loading -= 1;
                LoadingDone()

                ProfileAPI().GetJobseekerExperiences(context) { experiences ->
                    loading -= 1;
                    LoadingDone()

                    binding.recycleExp.apply {
                        layoutManager = LinearLayoutManager(activity)
                        adapter = experiences?.data?.let { ExpAdapter(it, this@cvPage) }
                    }
                }

                ProfileAPI().GetJobseekerEducations(context) { educations ->
                    loading -= 1;
                    LoadingDone()

                    binding.recycleEdu.apply {
                        layoutManager = LinearLayoutManager(activity)
                        adapter = educations?.data?.let { EduAdapter(it, this@cvPage) }
                    }

                }

                ProfileAPI().GetJobseekerLanguages(context) { languages ->
                    loading -= 1;
                    LoadingDone()

                    binding.recycleLang.apply {
                        layoutManager = LinearLayoutManager(activity)
                        adapter = languages?.data?.let { LanguageAdapter(false, it, null) }

                    }

                    if (skills?.data!!.size != 0) {
                        skills?.data.forEach {
                            if (it.scale == 1) {
                                binding.skillLv1.visibility = VISIBLE
                                if(context != null) {
                                    val skil1Chip = Chip(context)
                                    skil1Chip.setChipBackgroundColorResource(R.color.danger_100)
                                    skil1Chip.apply {
                                        textSize = 12f
                                        text = it.skillName
                                        isChipIconVisible = false
                                        isCloseIconVisible = false
                                        isClickable = true
                                        isCheckable = false
                                        view.apply {
                                            binding.chipGroup1.addView(skil1Chip as View)
                                        }
                                    }
                                }
                            } else if (it.scale == 2) {
                                binding.skillLv2.visibility = VISIBLE
                                if(context != null) {
                                    val skil2Chip = Chip(context)
                                    skil2Chip.setChipBackgroundColorResource(R.color.danger_100)
                                    skil2Chip.apply {
                                        textSize = 12f
                                        text = it.skillName
                                        isChipIconVisible = false
                                        isCloseIconVisible = false
                                        isClickable = true
                                        isCheckable = false
                                        view.apply {
                                            binding.chipGroup2.addView(skil2Chip as View)
                                        }
                                    }
                                }
                            } else if (it.scale == 3) {
                                binding.skillLv3.visibility = VISIBLE
                                if(context != null) {
                                    val skil3Chip = Chip(context)
                                    skil3Chip.setChipBackgroundColorResource(R.color.danger_100)
                                    skil3Chip.apply {
                                        textSize = 12f
                                        text = it.skillName
                                        isChipIconVisible = false
                                        isCloseIconVisible = false
                                        isClickable = true
                                        isCheckable = false
                                        view.apply {
                                            binding.chipGroup3.addView(skil3Chip as View)
                                        }
                                    }
                                }
                            } else if (it.scale == 4) {
                                binding.skillLv4.visibility = VISIBLE
                                if(context != null) {
                                    val skil4Chip = Chip(context)
                                    skil4Chip.setChipBackgroundColorResource(R.color.danger_100)
                                    skil4Chip.apply {
                                        textSize = 12f
                                        text = it.skillName
                                        isChipIconVisible = false
                                        isCloseIconVisible = false
                                        isClickable = true
                                        isCheckable = false
                                        view.apply {
                                            binding.chipGroup4.addView(skil4Chip as View)
                                        }
                                    }
                                }
                            } else if (it.scale == 5) {
                                binding.skillLv5.visibility = VISIBLE
                                if(context != null) {
                                    val skil5Chip = Chip(context)
                                    skil5Chip.setChipBackgroundColorResource(R.color.danger_100)
                                    skil5Chip.apply {
                                        textSize = 12f
                                        text = it.skillName
                                        isChipIconVisible = false
                                        isCloseIconVisible = false
                                        isClickable = true
                                        isCheckable = false
                                        view.apply {
                                            binding.chipGroup5.addView(skil5Chip as View)
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        binding.nullSkill.visibility = VISIBLE
                    }

                    binding.editSkill.setOnClickListener {
                        replaceFragment(edit_kemampuan(skills.data, this))
                    }

                    binding.seePapiResult.setOnClickListener {
                        ProfileAPI().GetPapiKostick(context) { res ->
                            if(res!=null) {
                                val sheet = PapikostickResult(res.data)
                                activity?.let { it1 ->
                                    sheet.show(
                                        it1.supportFragmentManager,
                                        "DemoBottomSheetFragment"
                                    )
                                }
                            }
                        }
                    }
                    binding.addExp.setOnClickListener {
                        replaceFragment(manage_cv_edit_experience_page(null, this))
                    }
                    binding.addEdu.setOnClickListener {
                        replaceFragment(fragment_manage_cv_edit_education_page(null, this))
                    }
                    binding.addLang.setOnClickListener {
                        replaceFragment(
                            EditBahasa(
                                SessionManager(context).user!!.userNo,
                                languages?.data,
                                this
                            )
                        )
                    }
                }
            }

    }
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.addToBackStack("")
        fragmentTransaction?.replace(R.id.fragment_container, fragment)
        fragmentTransaction?.commit()
    }

    override fun editExp(data: JobseekerExperiences) {
        replaceFragment(manage_cv_edit_experience_page(
            JobseekerExperienceRequest(
                data.experienceNo,
                SessionManager(context).user!!.userNo,
                data.experienceCityNo,
                data.experienceCompanyName,
                data.experienceCompanyNo,
                data.experienceDescription,
                data.experienceEndedAt,
                data.experienceBeginAt
                ,data.experienceJobTypeNo
                ,data.experiencePosition,
                data.experienceSalary)
        , this))
    }

    override fun editEdu(data: JobseekerEducations) {
        replaceFragment(fragment_manage_cv_edit_education_page(
            JobseekerEducationsRequest(
                data.jobseekerEducationNo,
                SessionManager(context).user!!.userNo,
                data.educationSchool,
                data.educationBeginAt,
                data.educationEndedAt,
                data.educationMajorNo,
                data.educationTitleNo,
                data.educationCityNo,
                data.gpa,
                data.educationDescription), this))
    }

    override fun deleteExp(data: JobseekerExperiences) {
        AlertDialog.Builder(context)
            .setMessage("Yakin ingin menghapus '${data.experiencePosition}'?")
            .setTitle("Konfirmasi menghapus")
                            .setPositiveButton(android.R.string.ok, object : DialogInterface.OnClickListener {
                                override fun onClick(dialog: DialogInterface, which: Int) {
                                    ManageProfileAPI().JobseekerDeleteExperience(data.experienceNo, context){
                                        if(it != null){
                                            ProfileAPI().GetJobseekerExperiences(context) { experiences ->
                                                binding.recycleExp.apply {
                                                    layoutManager = LinearLayoutManager(activity)
                                                    adapter = experiences?.data?.let { ExpAdapter(it, this@cvPage) }
                                                }
                                                Toast.makeText(activity, "Berhasil menghapus", Toast.LENGTH_SHORT).show()
                                                dialog.dismiss()
                                            }

                                        }
                                    }
                                }
                            })
                            .setNegativeButton(android.R.string.cancel, object : DialogInterface.OnClickListener{
                                override fun onClick(dialog: DialogInterface, which: Int) {
                                    dialog.dismiss()
                                }
                            }).create().show()
    }
    override fun deleteEducation(data: JobseekerEducations) {
        AlertDialog.Builder(context)
            .setMessage("Yakin ingin menghapus '${data.educationSchool}'?")
            .setTitle("Konfirmasi menghapus")
            .setPositiveButton(android.R.string.ok, object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, which: Int) {
                    ManageProfileAPI().JobseekerDeleteEducation(data.educationNo, context){
                        if(it != null){
                            ProfileAPI().GetJobseekerEducations(context) { edu ->
                                binding.recycleExp.apply {
                                    layoutManager = LinearLayoutManager(activity)
                                    adapter = edu?.data?.let { EduAdapter(it, this@cvPage) }
                                }
                                Toast.makeText(activity, "Berhasil menghapus", Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                            }

                        }
                    }
                }
            })
            .setNegativeButton(android.R.string.cancel, object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface, which: Int) {
                    dialog.dismiss()
                }
            }).create().show()
    }
    fun LoadingDone(){
        if(loading == 0){
            binding.spinner.visibility = GONE
            binding.contentContainer.visibility = VISIBLE
        }
    }

    override fun refresh() {
        GetData()
    }
}
interface iCvPage{
    fun editExp(data : JobseekerExperiences)
    fun editEdu(data : JobseekerEducations)
    fun deleteExp(data : JobseekerExperiences)
    fun deleteEducation(data : JobseekerEducations)
}