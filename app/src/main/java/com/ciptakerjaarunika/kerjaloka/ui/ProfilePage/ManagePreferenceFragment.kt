package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.ProfileAPI
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentManagePreferenceBinding
import com.ciptakerjaarunika.kerjaloka.model.Profile.JobseekerProfile
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Preference.FragmentEditJobType
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Preference.FragmentSalaryExpectation
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Preference.fragment_edit_interest_layout
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.math.BigDecimal


class ManagePreferenceFragment(val data: JobseekerProfile?) : Fragment() {
    private lateinit var binding : FragmentManagePreferenceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    private var loading = 3

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentManagePreferenceBinding.inflate(layoutInflater)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ProfileAPI().GetJobseekerField(context) { fields ->
            loading -=1
            if(loading == 0){
                view.findViewById<LinearLayout>(R.id.spinnerPref).visibility = View.GONE
                view.findViewById<LinearLayout>(R.id.content_pref).visibility = View.VISIBLE
            }
            binding.editMinat.setOnClickListener{
                replaceFragment(fragment_edit_interest_layout(fields?.data))
            }

            if (fields?.data?.size != 0) {

                fields?.data?.forEach {
                    binding.fieldGroup.isVisible = true
                    if (context != null) {
                        val fieldChip : Chip = Chip(context)

                        fieldChip.setChipBackgroundColorResource(R.color.danger_100)
                        fieldChip.apply {
                            textSize = 12f
                            text = it.fieldName
                            isChipIconVisible = false
                            isCloseIconVisible = false
                            isClickable = true
                            isCheckable = false
                            view.apply {
                                binding.chipGroupMinat.addView(fieldChip as View)
                            }
                        }
                    }

                }
            } else {
                binding.nullField.visibility = VISIBLE
            }
        }
        ProfileAPI().GetJobseekerJobType(context){ jobTypes ->
            loading -=1
            if(loading == 0){
                view.findViewById<LinearLayout>(R.id.spinnerPref).visibility = View.GONE
                view.findViewById<LinearLayout>(R.id.content_pref).visibility = View.VISIBLE
            }
            binding.editTipePekerjaan.setOnClickListener{
                replaceFragment(FragmentEditJobType(jobTypes?.data))
            }
            if (jobTypes?.data?.size != 0) {
                jobTypes?.data?.forEach {
                    binding.jobTypeGroup.isVisible = true
                    if (context != null) {
                        val jTypeChip: Chip = Chip(context)

                        jTypeChip.setChipBackgroundColorResource(R.color.danger_100)
                        jTypeChip.apply {
                            textSize = 12f
                            text = it.jobTypeName
                            isChipIconVisible = false
                            isCloseIconVisible = false
                            isClickable = false
                            isCheckable = false
                            view.apply {
                                binding.chipGroupTipePekerjaan.addView(jTypeChip as View)
                            }
                        }
                    }
                }
            }
            else {
                binding.nullJobType.visibility = VISIBLE
            }}

        ProfileAPI().GetJobseekerSalaryExpected(context) { salary->
            loading -=1
            if(loading == 0){
                view.findViewById<LinearLayout>(R.id.spinnerPref).visibility = View.GONE
                view.findViewById<LinearLayout>(R.id.content_pref).visibility = View.VISIBLE
            }
            val expectedSalary = view.findViewById<TextView>(R.id.expectedSalary)
            expectedSalary.text = if(salary != null && salary?.data != BigDecimal(0)) salary?.data.toString() else "-"

            val btn_EdMinat = view.findViewById<TextView>(R.id.edit_minat)
            val btn_EdJobType = view.findViewById<TextView>(R.id.edit_tipe_pekerjaan)
            val btn_gaji = view.findViewById<TextView>(R.id.edit_ekspektasi_gaji)

            btn_gaji.setOnClickListener{
                replaceFragment(FragmentSalaryExpectation(data?.additionals?.expectedSalary))
            }
        }
    }

    private fun replaceFragment(fragment: Fragment){

        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.addToBackStack("")
        fragmentTransaction?.replace(R.id.fragment_container, fragment)
        fragmentTransaction?.commit()
    }
}