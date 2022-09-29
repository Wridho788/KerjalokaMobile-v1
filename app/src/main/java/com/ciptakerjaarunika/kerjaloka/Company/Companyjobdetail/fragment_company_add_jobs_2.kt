package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.ciptakerjaarunika.kerjaloka.R
import com.google.android.material.button.MaterialButton

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class fragment_company_add_jobs_2() : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_company_add_jobs_2, container, false)
        val btn_kembali_cmpny = view.findViewById<MaterialButton>(R.id.btn_kembali_cmpny)
        val btn_next = view.findViewById<MaterialButton>(R.id.btn_selanjutnya_cmpny)
        val selectskill = view.findViewById<TextView>(R.id.cmpny_insert_skill)
        val selectpend = view.findViewById<TextView>(R.id.cmpny_insert_pend)
        val selectpeng = view.findViewById<TextView>(R.id.cmpny_insert_peng)
        val selectbindpek = view.findViewById<TextView>(R.id.cmpny_insert_bidang_pekerjaan)

        btn_kembali_cmpny.setOnClickListener {
            replaceFragment(fragment_company_add_jobs_1())
        }
        btn_next.setOnClickListener {
            replaceFragment(fragment_company_add_jobs_3())
        }
        return view
    }
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.fragmentHolder, fragment)
        fragmentTransaction?.commit()
    }


}