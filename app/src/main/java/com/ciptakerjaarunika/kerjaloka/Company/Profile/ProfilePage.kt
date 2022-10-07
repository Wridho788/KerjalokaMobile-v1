package com.ciptakerjaarunika.kerjaloka.Company.Profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.anychart.scales.Linear
import com.bumptech.glide.Glide
import com.ciptakerjaarunika.kerjaloka.Company.Profile.Adapter.viewpagerCompAdapter
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.CompanyReviewAPI
import com.ciptakerjaarunika.kerjaloka.api.company_profile_api
import com.ciptakerjaarunika.kerjaloka.api.users
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyReview.Model.company_reviews
import com.google.android.material.button.MaterialButton


class ProfilePage : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile_page, container, false)

        company_profile_api().CompanyGetProfileData(context) { response ->

            val scroll = view.findViewById<ScrollView>(R.id.profile_content2)
            val cParent = view.findViewById<LinearLayout>(R.id.profileLl2)
            val content = view.findViewById<ViewPager2>(R.id.Comp_profileContent)
            val mProfile = view.findViewById<MaterialButton>(R.id.manageProfile)
            val myRev = view.findViewById<MaterialButton>(R.id.myReview)
            val accSet = view.findViewById<MaterialButton>(R.id.accSetting)
            val compName = view.findViewById<TextView>(R.id.jsName1)
            val username = view.findViewById<TextView>(R.id.username)

            mProfile.setOnClickListener() {
                content.setCurrentItem(0)
            }
            myRev.setOnClickListener() {
                content.setCurrentItem(1)
            }
            accSet.setOnClickListener() {
                content.setCurrentItem(3)
            }

            val adapter = viewpagerCompAdapter(response?.data, parentFragmentManager, lifecycle)
            content.isUserInputEnabled=false
            content.adapter = adapter
            compName.text = response?.data?.companyName
            username.text = response?.data?.username

            if(activity != null)
                if(activity != null) {
                    Glide.with(view.context)
                        .load(config().portAddress + "/photo/Profile/" + response?.data?.logo)
                        .fitCenter()
                        .into(view.findViewById<ImageView>(R.id.compLogo))
                }

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

                    cParent.post {
                        val wMeasureSpec =
                            View.MeasureSpec.makeMeasureSpec(view.width, View.MeasureSpec.EXACTLY)
                        val hMeasureSpec =
                            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
                        view.measure(wMeasureSpec, hMeasureSpec)

                        if (content.layoutParams.height != view.measuredHeight) {
                            content.layoutParams = (content.layoutParams as ViewGroup.LayoutParams)
                                .also { lp -> lp.height = view.measuredHeight }
                        }
                    }
                }
            })
        }
        return view
    }

    companion object {

    }
}