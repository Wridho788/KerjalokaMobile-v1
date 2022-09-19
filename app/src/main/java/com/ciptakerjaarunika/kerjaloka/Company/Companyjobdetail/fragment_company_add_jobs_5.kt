package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ciptakerjaarunika.kerjaloka.R
import com.google.android.material.button.MaterialButton

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class fragment_company_add_jobs_5 : Fragment() {
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
        val view = inflater.inflate(R.layout.fragment_company_add_jobs_5, container, false)
        val btn_kembali_cmpny = view.findViewById<MaterialButton>(R.id.btn_kembali_cmpny)
        val btn_next = view.findViewById<MaterialButton>(R.id.btn_selanjutnya_cmpny)
        btn_kembali_cmpny.setOnClickListener {
            replaceFragment(fragment_company_add_jobs_4())
        }
        return view
    }
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.fragmentHolder, fragment)
        fragmentTransaction?.commit()
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            fragment_company_add_jobs_5().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}