package com.ciptakerjaarunika.kerjaloka.Company.Profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.viewpager2.widget.ViewPager2
import com.ciptakerjaarunika.kerjaloka.Company.Profile.Adapter.viewpagerCompAdapter
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.databinding.ActivityMainBinding
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter.viewpagerAdapter
import com.google.android.material.button.MaterialButton

class ProfilePage : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile_page, container, false)
        val content = view.findViewById<ViewPager2>(R.id.Comp_profileContent)
        val mProfile = view.findViewById<MaterialButton>(R.id.manageProfile)
        val myRev = view.findViewById<MaterialButton>(R.id.myReview)
        val accSet = view.findViewById<MaterialButton>(R.id.accSetting)

        mProfile.setOnClickListener(){
            content.setCurrentItem(0)
        }
        myRev.setOnClickListener(){
            content.setCurrentItem(1)
        }
        accSet.setOnClickListener(){
            content.setCurrentItem(3)
        }

        val adapter = viewpagerCompAdapter(parentFragmentManager, lifecycle)
        content.adapter = adapter
        return view
    }

    companion object {

    }
}