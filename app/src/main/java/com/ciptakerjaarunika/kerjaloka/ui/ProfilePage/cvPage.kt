package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.*
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.databinding.ActivitySearchJobBinding
import com.ciptakerjaarunika.kerjaloka.ui.HomePage.Model.rJobModel
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ManageCV.PapikostickResult
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ManageCV.edit_kemampuan
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ManageCV.fragment_manage_cv_edit_education_page
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ManageCV.manage_cv_edit_experience_page
import com.ciptakerjaarunika.kerjaloka.ui.search_job.Adapter.SearchJobAdapter
import com.ciptakerjaarunika.kerjaloka.ui.search_job.SearchModel
import com.ciptakerjaarunika.kerjaloka.view_job_detail
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class cvPage : Fragment() {
    //private var layoutManager: RecyclerView.LayoutManager? = null
    //private var adapter: RecyclerView.Adapter<SearchJobAdapter.ViewHolder>? = null

    var list = ArrayList<skills>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.jsprofile_cv, container, false)
        val btn_edSkil = view?.findViewById<TextView>(R.id.editSkill)
        val btn_seePapiRes = view?.findViewById<MaterialButton>(R.id.seePapiResult)
        val btn_edExp = view?.findViewById<TextView>(R.id.addExp)
        val btn_edEdu = view?.findViewById<TextView>(R.id.addEdu)
        val btn_edlang = view?.findViewById<TextView>(R.id.addLang)

        val chipLv1 = view.findViewById<ChipGroup>(R.id.chipGroup1)
        val chipLv2 = view.findViewById<ChipGroup>(R.id.chipGroup2)
        val chipLv3 = view.findViewById<ChipGroup>(R.id.chipGroup3)
        val chipLv4 = view.findViewById<ChipGroup>(R.id.chipGroup4)
        val chipLv5 = view.findViewById<ChipGroup>(R.id.chipGroup5)
        val skil1 = view.findViewById<LinearLayout>(R.id.skillLv1)
        val skil2 = view.findViewById<LinearLayout>(R.id.skillLv2)
        val skil3 = view.findViewById<LinearLayout>(R.id.skillLv3)
        val skil4 = view.findViewById<LinearLayout>(R.id.skillLv4)
        val skil5 = view.findViewById<LinearLayout>(R.id.skillLv5)
        val nullskill = view.findViewById<LinearLayout>(R.id.nullSkill)
        val emptyView: LinearLayout.LayoutParams =
            nullskill.getLayoutParams() as LinearLayout.LayoutParams
        val s1View: LinearLayout.LayoutParams =
            skil1.getLayoutParams() as LinearLayout.LayoutParams
        val s2View: LinearLayout.LayoutParams =
            skil2.getLayoutParams() as LinearLayout.LayoutParams
        val s3View: LinearLayout.LayoutParams =
            skil3.getLayoutParams() as LinearLayout.LayoutParams
        val s4View: LinearLayout.LayoutParams =
            skil4.getLayoutParams() as LinearLayout.LayoutParams
        val s5View: LinearLayout.LayoutParams =
            skil5.getLayoutParams() as LinearLayout.LayoutParams

        val list = ArrayList<skills>()
        val skill1 = skills(
            20211102115301,
            2,
            "Mining",
            2,
        )
        val skill2 = skills(
            20211102115301,
            1,
            "Python",
            1,
        )
        val skill3 = skills(
            20211102115301,
            4,
            "Teamwork",
            7,
        )
        list.add(skill1)
        list.add(skill2)
        list.add(skill3)

        if (list.isNotEmpty()) {
            list.forEach {
                if(it.scale==1)
                {
                    s1View.height=ViewGroup.LayoutParams.WRAP_CONTENT
                    skil1.isVisible=true
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
                            chipLv1.addView(skil1Chip as View)
                        }
                    }
                }
                else if (it.scale==2){
                    s2View.height=ViewGroup.LayoutParams.WRAP_CONTENT
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
                            chipLv2.addView(skil2Chip as View)
                        }
                    }
                }
                else if(it.scale==3){
                    s3View.height=ViewGroup.LayoutParams.WRAP_CONTENT
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
                            chipLv3.addView(skil3Chip as View)
                        }
                    }
                }
                else if(it.scale==4){
                    s4View.height=ViewGroup.LayoutParams.WRAP_CONTENT
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
                            chipLv4.addView(skil4Chip as View)
                        }
                    }
                }
                else if(it.scale==5){
                    s5View.height=ViewGroup.LayoutParams.WRAP_CONTENT
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
                            chipLv5.addView(skil5Chip as View)
                        }
                    }
                }
            }
        }
        else{
            emptyView.height=ViewGroup.LayoutParams.WRAP_CONTENT
        }

        btn_edSkil?.setOnClickListener {
            replaceFragment(edit_kemampuan())
        }
        btn_seePapiRes?.setOnClickListener {
            replaceFragment(PapikostickResult())
        }
        btn_edExp?.setOnClickListener {
            replaceFragment(manage_cv_edit_experience_page())
        }
        btn_edEdu?.setOnClickListener {
            replaceFragment(fragment_manage_cv_edit_education_page())
        }
        return view
    }

    companion object {

    }

    private fun replaceFragment(fragment: Fragment) {

        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.fragment_container, fragment)
        fragmentTransaction?.commit()
    }
}