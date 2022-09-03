package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ManageCV.PapikostickResult
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ManageCV.edit_kemampuan
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ManageCV.fragment_manage_cv_edit_education_page
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ManageCV.manage_cv_edit_experience_page
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.manage_profile.EditAddInfo
import com.google.android.material.button.MaterialButton

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [cvPage.newInstance] factory method to
 * create an instance of this fragment.
 */
class cvPage : Fragment() {
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
        val view = inflater.inflate(R.layout.jsprofile_cv, container, false)
        val btn_edSkil = view?.findViewById<TextView>(R.id.editSkill)
        val btn_seePapiRes = view?.findViewById<MaterialButton>(R.id.seePapiResult)
        val btn_edExp = view?.findViewById<TextView>(R.id.addExp)
        val btn_edEdu = view?.findViewById<TextView>(R.id.addEdu)
        val btn_edlang = view?.findViewById<TextView>(R.id.addLang)

        btn_edSkil?.setOnClickListener{
            replaceFragment(edit_kemampuan())
        }
        btn_seePapiRes?.setOnClickListener{
            replaceFragment(PapikostickResult())
        }
        btn_edExp?.setOnClickListener{
            replaceFragment(manage_cv_edit_experience_page())
        }
        btn_edEdu?.setOnClickListener{
            replaceFragment(fragment_manage_cv_edit_education_page())
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
         * @return A new instance of fragment cvPage.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            cvPage().apply {
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