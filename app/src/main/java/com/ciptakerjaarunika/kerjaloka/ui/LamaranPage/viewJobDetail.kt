package com.ciptakerjaarunika.kerjaloka.ui.LamaranPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ModalEdit.EditGender
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton

class viewJobDetail() : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_job_detail, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnWithdraw = view.findViewById<MaterialButton>(R.id.btnWithdraw) as MaterialButton

        view.findViewById<ImageView>(R.id.backButton)?.setOnClickListener{
            fragmentManager?.popBackStack()
        }

        btnWithdraw.setOnClickListener {
            val sheet = BottomSheetApplicant()
            activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "DemoBottomSheetFragment") }
        }

        val lapor = view.findViewById<LinearLayout>(R.id.report_job) as LinearLayout
        lapor.setOnClickListener {
            val sheet = EditGender()
            activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "DemoBottomSheetFragment") }
        }

    }

}
