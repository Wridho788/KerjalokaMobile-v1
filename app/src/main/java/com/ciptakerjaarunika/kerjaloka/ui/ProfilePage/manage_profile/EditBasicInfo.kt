package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.manage_profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.model.Profile.JobseekerProfile
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ModalEdit.EditCity
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ModalEdit.EditGender

class EditBasicInfo(val data : JobseekerProfile?) : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_edit_basic_info, container, false)
        val gender = view.findViewById<TextView>(R.id.jsGender)
        val city = view.findViewById<TextView>(R.id.jsCity)

        gender.setOnClickListener {
            val sheet = EditGender()
            activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "DemoBottomSheetFragment") }
        }

        city.setOnClickListener {
            val sheet = EditCity()
            activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "DemoBottomSheetFragment") }
        }

        return view
    }
}