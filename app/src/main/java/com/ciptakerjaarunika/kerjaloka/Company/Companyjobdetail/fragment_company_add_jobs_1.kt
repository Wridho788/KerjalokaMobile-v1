package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.Bottomsheet.BottomSheetEditJob
import com.ciptakerjaarunika.kerjaloka.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

class fragment_company_add_jobs_1 : Fragment(), iLocationPage {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_company_add_jobs_1, container, false)
        val btnnext = view.findViewById<MaterialButton>(R.id.btn_selanjutnya_cmpny)
        val position_job_text = view.findViewById<EditText>(R.id.edit_position_job)
        val btn_choose_location = view.findViewById<MaterialCardView>(R.id.btn_choose_location)
        val type_job_text = view.findViewById<EditText>(R.id.edit_job_type)
        val salary_job_text = view.findViewById<EditText>(R.id.edit_salary_job)

        btn_choose_location.setOnClickListener {
            locationModal()
        }


        btnnext.setOnClickListener {
            replaceFragment(
                fragment_company_add_jobs_2(
//                    position_job_text.text.toString(),
//                    type_job_text.text.toString(),
//                    salary_job_text.text.toString())
                ))
        }
        return view
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.fragmentHolder, fragment)
        fragmentTransaction?.commit()
    }

    fun locationModal(){
        val sheet = BottomSheetEditJob(this@fragment_company_add_jobs_1)
        activity?.let { it -> sheet.show(it.supportFragmentManager, "location") }
    }

    override fun editJobModal(location: String) {
        val location_job = view?.findViewById<TextView>(R.id.compny_lokasi)
        location_job?.text = location
    }


}

interface iLocationPage{
    fun editJobModal(location: String)
}
