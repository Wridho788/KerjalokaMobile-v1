package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ManageCV

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ModalEdit.ChooseScale
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ModalEdit.ChooseSkill
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ModalEdit.EditMarital
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.skills
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [edit_kemampuan.newInstance] factory method to
 * create an instance of this fragment.
 */
class edit_kemampuan : Fragment() {
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
        val view = inflater.inflate(R.layout.fragment_edit_kemampuan, container, false)
        val skil = view.findViewById<TextView>(R.id.js_EditSkill)
        val scale = view.findViewById<TextView>(R.id.js_EditSkillLevel)

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
        val expPos = view.findViewById<TextView>(R.id.txt_Position)
        val expLoc = view.findViewById<TextView>(R.id.txt_loc)
        val expDrt = view.findViewById<TextView>(R.id.txt_duration)


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
                if (it.scale == 1) {
                    s1View.height = ViewGroup.LayoutParams.WRAP_CONTENT
                    skil1.isVisible = true
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
                } else if (it.scale == 2) {
                    s2View.height = ViewGroup.LayoutParams.WRAP_CONTENT
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
                } else if (it.scale == 3) {
                    s3View.height = ViewGroup.LayoutParams.WRAP_CONTENT
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
                } else if (it.scale == 4) {
                    s4View.height = ViewGroup.LayoutParams.WRAP_CONTENT
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
                } else if (it.scale == 5) {
                    s5View.height = ViewGroup.LayoutParams.WRAP_CONTENT
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
        } else {
            emptyView.height = ViewGroup.LayoutParams.WRAP_CONTENT
        }

        skil.setOnClickListener {
            val sheet = ChooseSkill()
            activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "DemoBottomSheetFragment") }
        }
        scale.setOnClickListener {
            val sheet = ChooseScale()
            activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "DemoBottomSheetFragment") }
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
         * @return A new instance of fragment edit_kemampuan.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            edit_kemampuan().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}