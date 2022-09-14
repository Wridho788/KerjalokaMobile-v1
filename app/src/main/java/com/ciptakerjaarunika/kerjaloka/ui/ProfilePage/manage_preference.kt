package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Preference.activity_editjob_layout
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Preference.fragment_edit_interest_layout
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.manage_profile.EditAboutMe
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [manage_preference.newInstance] factory method to
 * create an instance of this fragment.
 */
class manage_preference : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.activity_item_profile_minat_page, container, false)
        val btn_EdMinat = view.findViewById<TextView>(R.id.edit_minat)
        val btn_EdJobType = view.findViewById<TextView>(R.id.edit_tipe_pekerjaan)
        val btn_gaji = view.findViewById<TextView>(R.id.edit_ekspektasi_gaji)
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

//
//        val list = ArrayList<field>()
//        val field1 = field(
//            "Mining",
//            2,
//            20211102115301,
//        )
//        val field2 = field(
//            "Python",
//            1,
//            20211102115301,
//        )
//        val field3 = field(
//            "Teamwork",
//            7,
//            20211102115301,
//        )
//        list.add(field1)
//        list.add(field2)
//        list.add(field3)
//
//        val jobTypeList = ArrayList<jobtype>()
//        val jt1 = jobtype(
//            "Contract",
//            3,
//            20211102115301,
//        )
//        val jt2 = jobtype(
//            "Full-Time",
//            1,
//            20211102115301,
//        )
//
//        jobTypeList.add(jt1)
//        jobTypeList.add(jt2)
//
//        if (list.isNotEmpty()) {
//            list.forEach {
//                s1View.height = ViewGroup.LayoutParams.WRAP_CONTENT
//                fieldGroup.isVisible = true
//                val fieldChip = Chip(context)
//                fieldChip.setChipBackgroundColorResource(R.color.danger_100)
//                fieldChip.apply {
//                    textSize = 12f
//                    text = it.fieldName
//                    isChipIconVisible = false
//                    isCloseIconVisible = false
//                    isClickable = true
//                    isCheckable = false
//                    view.apply {
//                        chipField.addView(fieldChip as View)
//                    }
//                }
//            }
//        }
//        else {
//            emptyView.height = ViewGroup.LayoutParams.WRAP_CONTENT
//        }
//
//        if (jobTypeList.isNotEmpty()) {
//            jobTypeList.forEach {
//                jobTypeView.height = ViewGroup.LayoutParams.WRAP_CONTENT
//                jobTypeGroup.isVisible = true
//                val jTypeChip = Chip(context)
//                jTypeChip.setChipBackgroundColorResource(R.color.danger_100)
//                jTypeChip.apply {
//                    textSize = 12f
//                    text = it.jobTypeName
//                    isChipIconVisible = false
//                    isCloseIconVisible = false
//                    isClickable = false
//                    isCheckable = false
//                    view.apply {
//                        chipJobType.addView(jTypeChip as View)
//                    }
//                }
//            }
//        }
//        else {
//            emptyView1.height = ViewGroup.LayoutParams.WRAP_CONTENT
//        }


        btn_EdMinat.setOnClickListener{
            replaceFragment(fragment_edit_interest_layout())
        }
        btn_EdJobType.setOnClickListener{
            replaceFragment(activity_editjob_layout())
        }
        btn_gaji.setOnClickListener{
            replaceFragment(EditAboutMe())
        }



        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment manage_preference.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            manage_preference().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun replaceFragment(fragment: Fragment){

        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.fragment_container, fragment)
        fragmentTransaction?.commit()
    }
}