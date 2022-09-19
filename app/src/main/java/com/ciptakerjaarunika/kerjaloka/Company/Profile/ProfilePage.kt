package com.ciptakerjaarunika.kerjaloka.Company.Profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.ScrollView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.anychart.scales.Linear
import com.ciptakerjaarunika.kerjaloka.Company.Profile.Adapter.viewpagerCompAdapter
import com.ciptakerjaarunika.kerjaloka.R
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
        val scroll = view.findViewById<ScrollView>(R.id.profile_content2)
        val cParent = view.findViewById<LinearLayout>(R.id.profileLl2)
        val content = view.findViewById<ViewPager2>(R.id.Comp_profileContent)
        val mProfile = view.findViewById<MaterialButton>(R.id.manageProfile)
        val myRev = view.findViewById<MaterialButton>(R.id.myReview)
        val accSet = view.findViewById<MaterialButton>(R.id.accSetting)

        mProfile.setOnClickListener() {
            content.setCurrentItem(0)
            content.layoutParams.height = 0
            val height = ViewGroup.LayoutParams.WRAP_CONTENT
            content.layoutParams.height = height
            cParent.layoutParams.height = height
        }
        myRev.setOnClickListener() {
            content.setCurrentItem(1)
            content.layoutParams.height = 0
            content.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        }
        accSet.setOnClickListener() {
            content.setCurrentItem(3)
            content.layoutParams.height = 0
            content.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        }

        val adapter = viewpagerCompAdapter(parentFragmentManager, lifecycle)
        content.adapter = adapter
        return view
    }

    companion object {

    }
}