package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.ProfileAPI
import com.ciptakerjaarunika.kerjaloka.model.Profile.JobseekerProfile
import com.ciptakerjaarunika.kerjaloka.model.User.Jobseeker
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Preference.activity_editjob_layout
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Preference.fragment_edit_interest_layout
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.manage_profile.EditAboutMe
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.math.BigDecimal

// TODO: Rename parameter arguments, choose names that match

class manage_preference(val data : JobseekerProfile?) : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    private var loading = 3

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_item_profile_minat_page, container, false)

        val chipField = view.findViewById<ChipGroup>(R.id.chipGroup_minat)
        val fieldGroup = view.findViewById<LinearLayout>(R.id.fieldGroup)
        val nullField = view.findViewById<LinearLayout>(R.id.nullField)
        val chipJobType = view.findViewById<ChipGroup>(R.id.chipGroup_tipe_pekerjaan)
        val jobTypeGroup = view.findViewById<LinearLayout>(R.id.jobTypeGroup)
        val nullJobType = view.findViewById<LinearLayout>(R.id.nullJobType)

        val emptyView: LinearLayout.LayoutParams =
            nullField.getLayoutParams() as LinearLayout.LayoutParams
        val s1View: LinearLayout.LayoutParams =
            fieldGroup.getLayoutParams() as LinearLayout.LayoutParams
        val emptyView1: LinearLayout.LayoutParams =
            nullJobType.getLayoutParams() as LinearLayout.LayoutParams
        val jobTypeView: LinearLayout.LayoutParams =
            jobTypeGroup.getLayoutParams() as LinearLayout.LayoutParams

        ProfileAPI().GetJobseekerField(context) { fields ->
            loading -=1
            if(loading == 0){
                view.findViewById<LinearLayout>(R.id.spinnerPref).visibility = GONE
                view.findViewById<LinearLayout>(R.id.content_pref).visibility = VISIBLE
            }
            if (fields?.data?.size != 0) {
                fields?.data?.forEach {
                    s1View.height = ViewGroup.LayoutParams.WRAP_CONTENT
                    fieldGroup.isVisible = true
                    val fieldChip = Chip(context)
                    fieldChip.setChipBackgroundColorResource(R.color.danger_100)
                    fieldChip.apply {
                        textSize = 12f
                        text = it.fieldName
                        isChipIconVisible = false
                        isCloseIconVisible = false
                        isClickable = true
                        isCheckable = false
                        view.apply {
                            chipField.addView(fieldChip as View)
                        }
                    }
                }
            } else {
                emptyView.height = ViewGroup.LayoutParams.WRAP_CONTENT
            }
        }
            ProfileAPI().GetJobseekerJobType(context){ jobTypes ->
                loading -=1
                if(loading == 0){
                    view.findViewById<LinearLayout>(R.id.spinnerPref).visibility = GONE
                    view.findViewById<LinearLayout>(R.id.content_pref).visibility = VISIBLE
                }
                if (jobTypes?.data?.size != 0) {
                    jobTypes?.data?.forEach {
                        jobTypeView.height = ViewGroup.LayoutParams.WRAP_CONTENT
                        jobTypeGroup.isVisible = true
                        val jTypeChip = Chip(context)
                        jTypeChip.setChipBackgroundColorResource(R.color.danger_100)
                        jTypeChip.apply {
                            textSize = 12f
                            text = it.jobTypeName
                            isChipIconVisible = false
                            isCloseIconVisible = false
                            isClickable = false
                            isCheckable = false
                            view.apply {
                                chipJobType.addView(jTypeChip as View)
                            }
                        }
                    }
                }
                else {
                    emptyView1.height = ViewGroup.LayoutParams.WRAP_CONTENT
                }}

                ProfileAPI().GetJobseekerSalaryExpected(context) {salary->
                    loading -=1
                    if(loading == 0){
                        view.findViewById<LinearLayout>(R.id.spinnerPref).visibility = GONE
                        view.findViewById<LinearLayout>(R.id.content_pref).visibility = VISIBLE
                    }
                    val expectedSalary = view.findViewById<TextView>(R.id.expectedSalary)
                    expectedSalary.text = if(salary != null && salary?.data != BigDecimal(0)) salary?.data.toString() else "-"

                    val btn_EdMinat = view.findViewById<TextView>(R.id.edit_minat)
                    val btn_EdJobType = view.findViewById<TextView>(R.id.edit_tipe_pekerjaan)
                    val btn_gaji = view.findViewById<TextView>(R.id.edit_ekspektasi_gaji)

                    btn_EdMinat.setOnClickListener{
                        replaceFragment(fragment_edit_interest_layout())
                    }
                    btn_EdJobType.setOnClickListener{
                        replaceFragment(activity_editjob_layout())
                    }
                    btn_gaji.setOnClickListener{
                        replaceFragment(EditAboutMe(data))
                    }
        }

        return view
    }

    private fun replaceFragment(fragment: Fragment){

        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.fragment_container, fragment)
        fragmentTransaction?.commit()
    }
}