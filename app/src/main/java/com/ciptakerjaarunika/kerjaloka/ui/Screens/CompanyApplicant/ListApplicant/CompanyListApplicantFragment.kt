package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ListApplicant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ciptakerjaarunika.kerjaloka.R

class CompanyListApplicantFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_company_list_applicant, container, false)
    }

}