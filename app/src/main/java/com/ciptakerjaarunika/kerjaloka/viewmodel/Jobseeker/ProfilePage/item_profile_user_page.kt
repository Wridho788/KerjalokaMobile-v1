package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.ciptakerjaarunika.kerjaloka.R

class item_profile_user_page : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_jobseeker_profile_page, container, false)
        val jsName= view.findViewById<TextView>(R.id.jsName1)
//        jsName.text="Bambang"
        return view
    }
}
