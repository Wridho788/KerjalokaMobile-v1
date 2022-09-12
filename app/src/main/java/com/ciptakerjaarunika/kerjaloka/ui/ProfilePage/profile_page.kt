package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.databinding.ActivityMainBinding
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter.viewpagerAdapter
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.add_Info
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.js_profile
import com.google.android.material.button.MaterialButton


class profilepage : Fragment() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setContentView(root: ConstraintLayout) {
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var data = js_profile()
        var addInfo = add_Info()
        val view = inflater.inflate(R.layout.activity_profile_page, container, false)
        val profileLl = view.findViewById<LinearLayout>(R.id.profileLl)
        val content = view.findViewById<ViewPager2>(R.id.profileContent)
        val btn_mngProfile = view.findViewById<MaterialButton>(R.id.manageProfile)
        val btn_mngCV = view.findViewById<MaterialButton>(R.id.CV)
        val btn_mngPref = view.findViewById<MaterialButton>(R.id.Preference)
        val btn_mngAttach = view.findViewById<MaterialButton>(R.id.attachment)
        val btn_mngMyReview= view.findViewById<MaterialButton>(R.id.myReview)
        val btn_mngMyRecord = view.findViewById<MaterialButton>(R.id.myRecord)
        val btn_mngSetting = view.findViewById<MaterialButton>(R.id.setting)

        val jsName = view.findViewById<TextView>(R.id.jsName1)
        val jsusrname = view.findViewById<TextView>(R.id.username)
        val js_AboutMe = view.findViewById<TextView>(R.id.txt_aboutme)

        val adapter = viewpagerAdapter(parentFragmentManager, lifecycle)
        jsName.text = data.jobseekerName
        js_AboutMe.text = addInfo.jobseekerAbout

        content.adapter = adapter
//        content.layoutParams = ViewGroup.LayoutParams.WRAP_CONTENT
        content.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//            override fun onPageScrolled(
//                position: Int,
//                positionOffset: Float,
//                positionOffsetPixels: Int
//            ) {
//                super.onPageScrolled(position,positionOffset,positionOffsetPixels)
//                if (position>0 && positionOffset==0.0f && positionOffsetPixels==0){
//                    content.layoutParams.height =
//                        content.getChildAt(0).height
//                }
//            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                profileLl.post {
                    val wMeasureSpec = View.MeasureSpec.makeMeasureSpec(view.width, View.MeasureSpec.EXACTLY)
                    val hMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
                    view.measure(wMeasureSpec, hMeasureSpec)

                    if (content.layoutParams.height != view.measuredHeight) {
                        // ParentViewGroup is, for example, LinearLayout
                        // ... or whatever the parent of the ViewPager2 is
//                        content.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT

                        content.layoutParams = (content.layoutParams as ViewGroup.LayoutParams)
                            .also { lp -> lp.height = view.measuredHeight }
                    }
                }
            }
        })

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


        return view
    }

}