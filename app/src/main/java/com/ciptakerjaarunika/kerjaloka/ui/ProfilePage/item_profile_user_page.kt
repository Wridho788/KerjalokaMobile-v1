package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.add_Info
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.js_profile
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.minat_model

class item_profile_user_page : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var data = js_profile()
        data.jobseekerName="Egi Bangun"
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.activity_profile_page, container, false)
        val jsName= view.findViewById<TextView>(R.id.jsName1)


        jsName.text="Bambang"


        return view
    }
}
