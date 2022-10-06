package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.ProfileAPI
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentJobseekerProfilePageBinding
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentProfilePageBinding
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter.viewpagerAdapter
import com.google.android.material.button.MaterialButton


class profilepage(var Page : Int) : Fragment() {
    private lateinit var binding : FragmentJobseekerProfilePageBinding
    private var loading :Int = 0;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentJobseekerProfilePageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ProfileAPI().JobseekerGetProfileData(context) { response ->
            if(response?.data != null) {
                if(response.data.users.roleNo == 4) {
                    response.data.users.jobseekerAdditional = response.data.additionals
                }
                SessionManager(context).user = response.data.users
            }
            view.findViewById<LinearLayout>(R.id.spinnerProfile)?.visibility = GONE
            view.findViewById<ScrollView>(R.id.profile_content)?.visibility = VISIBLE

            val profileLl = view.findViewById<LinearLayout>(R.id.profileLl)
            val content = view.findViewById<ViewPager2>(R.id.profileContent)
            val btn_mngProfile = view.findViewById<MaterialButton>(R.id.manageProfile)
            val btn_mngCV = view.findViewById<MaterialButton>(R.id.CV)
            val btn_mngPref = view.findViewById<MaterialButton>(R.id.Preference)
            val btn_mngAttach = view.findViewById<MaterialButton>(R.id.attachment)
            val btn_mngMyReview = view.findViewById<MaterialButton>(R.id.myReview)
            val btn_mngMyRecord = view.findViewById<MaterialButton>(R.id.myRecord)
            val btn_mngSetting = view.findViewById<MaterialButton>(R.id.setting)

            val jsName = view.findViewById<TextView>(R.id.jsName1)
            val jsusrname = view.findViewById<TextView>(R.id.username)
            val js_AboutMe = view.findViewById<TextView>(R.id.txt_aboutme)

            val adapter = viewpagerAdapter(response?.data, parentFragmentManager, lifecycle)
            val user = SessionManager(context).user
            jsName.text = user?.userFullname
            js_AboutMe.text = user?.jobseekerAdditional?.jobseekerAbout
            jsusrname.text = user?.username

            Glide.with(view.context)
                .load(config().portAddress + "/photo/Profile/" + user?.photo).fitCenter()
                .into(view.findViewById<ImageView>(R.id.userPhoto))
            content.isUserInputEnabled=false
            content.adapter = adapter
            updatePage()

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
                        val wMeasureSpec =
                            View.MeasureSpec.makeMeasureSpec(view.width, View.MeasureSpec.EXACTLY)
                        val hMeasureSpec =
                            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
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

            btn_mngProfile.setOnClickListener() {
                Page = 0
                content.currentItem = Page
                updatePage()
            }
            btn_mngCV.setOnClickListener() {
                Page = 1
                content.currentItem = Page
                updatePage()
            }
            btn_mngPref.setOnClickListener() {
                Page = 2
                content.currentItem = Page
                updatePage()
            }
            btn_mngAttach.setOnClickListener() {
                Page = 3
                content.currentItem = Page
                updatePage()
            }
            btn_mngMyReview.setOnClickListener() {
                Page = 4
                content.currentItem = Page
                updatePage()
            }
            btn_mngMyRecord.setOnClickListener() {
                Page = 5
                content.currentItem = Page
                updatePage()
            }
            btn_mngSetting.setOnClickListener() {
                Page = 6
                content.currentItem = Page
                updatePage()
            }
        }

    }
    fun updatePage(){
        val content = view?.findViewById<ViewPager2>(R.id.profileContent)
        content?.currentItem = Page

        view?.findViewById<MaterialButton>(R.id.manageProfile)?.backgroundTintList = resources.getColorStateList(R.color.white);
        view?.findViewById<MaterialButton>(R.id.CV)?.backgroundTintList = resources.getColorStateList(R.color.white);
        view?.findViewById<MaterialButton>(R.id.Preference)?.backgroundTintList = resources.getColorStateList(R.color.white);
        view?.findViewById<MaterialButton>(R.id.attachment)?.backgroundTintList = resources.getColorStateList(R.color.white);
        view?.findViewById<MaterialButton>(R.id.myReview)?.backgroundTintList = resources.getColorStateList(R.color.white);
        view?.findViewById<MaterialButton>(R.id.myRecord)?.backgroundTintList = resources.getColorStateList(R.color.white);
        view?.findViewById<MaterialButton>(R.id.setting)?.backgroundTintList = resources.getColorStateList(R.color.white);
        when (Page){
            0 ->   view?.findViewById<MaterialButton>(R.id.manageProfile)?.backgroundTintList = resources.getColorStateList(R.color.danger_300);
            1 ->   view?.findViewById<MaterialButton>(R.id.CV)?.backgroundTintList = resources.getColorStateList(R.color.danger_300);
            2 ->   view?.findViewById<MaterialButton>(R.id.Preference)?.backgroundTintList = resources.getColorStateList(R.color.danger_300);
            3 ->   view?.findViewById<MaterialButton>(R.id.attachment)?.backgroundTintList = resources.getColorStateList(R.color.danger_300);
            4 ->   view?.findViewById<MaterialButton>(R.id.myReview)?.backgroundTintList = resources.getColorStateList(R.color.danger_300);
            5 ->   view?.findViewById<MaterialButton>(R.id.myRecord)?.backgroundTintList = resources.getColorStateList(R.color.danger_300);
            6 ->   view?.findViewById<MaterialButton>(R.id.setting)?.backgroundTintList = resources.getColorStateList(R.color.danger_300);
        }

    }
}