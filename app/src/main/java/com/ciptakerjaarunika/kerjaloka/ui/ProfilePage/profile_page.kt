package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.databinding.ActivityProfilePageBinding
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter.viewpagerAdapter
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.manage_profile.EditBasicInfo
import com.google.android.material.button.MaterialButton

class profile_page : AppCompatActivity() {
    private lateinit var binding: ActivityProfilePageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_page)

        val content = findViewById<ViewPager2>(R.id.profileContent)
        val btn_mngProfile = findViewById<MaterialButton>(R.id.manageProfile)
        val btn_mngCV = findViewById<MaterialButton>(R.id.CV)
        val btn_mngPref = findViewById<MaterialButton>(R.id.Preference)
        val btn_mngAttach = findViewById<MaterialButton>(R.id.attachment)
        val btn_mngMyReview= findViewById<MaterialButton>(R.id.myReview)
        val btn_mngMyRecord = findViewById<MaterialButton>(R.id.myRecord)
        val btn_mngSetting = findViewById<MaterialButton>(R.id.setting)

        val adapter = viewpagerAdapter(supportFragmentManager, lifecycle)
        content.adapter = adapter

        btn_mngProfile.setOnClickListener(){
            content.setCurrentItem(0)
        }
        btn_mngCV.setOnClickListener(){
            content.setCurrentItem(1)
        }
        btn_mngPref.setOnClickListener(){
            content.setCurrentItem(2)
        }
        btn_mngAttach.setOnClickListener(){
            content.setCurrentItem(3)
        }
        btn_mngMyReview.setOnClickListener(){
            content.setCurrentItem(4)
        }
        btn_mngMyRecord.setOnClickListener(){
            content.setCurrentItem(5)
        }
        btn_mngSetting.setOnClickListener(){
            content.setCurrentItem(6)
        }
    }
}