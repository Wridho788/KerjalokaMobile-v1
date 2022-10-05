package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.ProfileAPI
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentProfileCvBinding
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter.EduAdapter
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter.ExpAdapter
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter.LanguageAdapter
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ManageCV.PapikostickResult
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ManageCV.edit_kemampuan
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ManageCV.fragment_manage_cv_edit_education_page
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ManageCV.manage_cv_edit_experience_page
import com.google.android.material.chip.Chip

class cvPage : Fragment() {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private lateinit var binding : FragmentProfileCvBinding

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
            ProfileAPI().GetJobseekerSkills(context) { skills ->
                ProfileAPI().GetJobseekerExperiences(context) { experiences ->
                    binding.recycleExp.apply {
                        layoutManager = LinearLayoutManager(activity)
                        adapter = experiences?.data?.let { ExpAdapter(it) }
                    }
                }

                ProfileAPI().GetJobseekerEducations(context) { educations ->
                    binding.recycleEdu.apply {
                        layoutManager = LinearLayoutManager(activity)
                        adapter = educations?.data?.let { EduAdapter(it) }
                    }

                }

                ProfileAPI().GetJobseekerLanguages(context) { languages ->
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
                        replaceFragment(edit_kemampuan(skills.data))
                    }

                    binding.seePapiResult.setOnClickListener {
                        val sheet = PapikostickResult()
                        activity?.let { it1 ->
                            sheet.show(
                                it1.supportFragmentManager,
                                "DemoBottomSheetFragment"
                            )
                        }
                    }
                    binding.addExp.setOnClickListener {
                        replaceFragment(manage_cv_edit_experience_page(null))
                    }
                    binding.addEdu.setOnClickListener {
                        replaceFragment(fragment_manage_cv_edit_education_page())
                    }
                    binding.addLang.setOnClickListener {
                        replaceFragment(
                            EditBahasa(
                                SessionManager(context).user!!.userNo,
                                languages?.data
                            )
                        )
                    }
                }
            }

    }
    private fun replaceFragment(fragment: Fragment) {

        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.fragment_container, fragment)
        fragmentTransaction?.commit()
    }
}