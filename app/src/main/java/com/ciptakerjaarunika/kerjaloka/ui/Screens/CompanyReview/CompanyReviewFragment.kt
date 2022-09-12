package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyReview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ciptakerjaarunika.kerjaloka.R

class CompanyReviewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_company_review, container, false)
        return view
    }

    companion object
}