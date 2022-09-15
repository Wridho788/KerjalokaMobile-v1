package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.manage_profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.model.Profile.JobseekerProfile
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ModalEdit.EditMarital
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ModalEdit.EditReligion
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ModalEdit.EditResident

class EditAddInfo(val data : JobseekerProfile?) : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_edit_add_info, container, false)
        val marital = view.findViewById<TextView>(R.id.js_EditMarital)
        val religi = view.findViewById<TextView>(R.id.js_EditReligi)
        val resident = view.findViewById<TextView>(R.id.jsEditResident)

        marital.setOnClickListener {
            val sheet = EditMarital()
            activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "DemoBottomSheetFragment") }
        }
        religi.setOnClickListener {
            val sheet = EditReligion()
            activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "DemoBottomSheetFragment") }
        }
        resident.setOnClickListener {
            val sheet = EditResident()
            activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "DemoBottomSheetFragment") }
        }

        return view
    }
}