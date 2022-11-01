package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.ProfileAPI
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentJobseekerProfilePageBinding
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter.viewpagerAdapter
import com.google.android.material.button.MaterialButton


class profilepage(var Page: Int) : Fragment() {
    private lateinit var binding: FragmentJobseekerProfilePageBinding
    private lateinit var viewpagerAdapter: viewpagerAdapter
    private var loading: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentJobseekerProfilePageBinding.inflate(layoutInflater)
        ProfileAPI().JobseekerGetProfileData(context) { response ->
            if (response?.data != null) {
                if (response.data.users.roleNo == 4) {
                    response.data.users.photo = response.data.additionals.photo
                    response.data.users.jobseekerAdditional = response.data.additionals
                }
                SessionManager(context).user = response.data.users
            }

            lifecycleScope.launchWhenResumed {
                binding.spinnerProfile.visibility = GONE
                binding.profileContent.visibility = VISIBLE
                viewpagerAdapter =
                    viewpagerAdapter(response?.data, parentFragmentManager, lifecycle)
                with(binding) {
                    binding.profileContent.adapter = viewpagerAdapter
                    binding.manageProfile.setOnClickListener {
                        Page = 0
                        binding.profileContent.currentItem = Page
                        updatePage()
                    }
                    binding.CV.setOnClickListener {
                        Page = 1
                        binding.profileContent.currentItem = Page
                        updatePage()
                    }
                    binding.Preference.setOnClickListener {
                        Page = 2
                        binding.profileContent.currentItem = Page
                        updatePage()
                    }
                    binding.attachment.setOnClickListener {
                        Page = 3
                        binding.profileContent.currentItem = Page
                        updatePage()
                    }
                    binding.myReview.setOnClickListener {
                        Page = 4
                        binding.profileContent.currentItem = Page
                        updatePage()
                    }
                    binding.myRecord.setOnClickListener {
                        Page = 5
                        binding.profileContent.currentItem = Page
                        updatePage()
                    }
                    binding.setting.setOnClickListener {
                        Page = 6
                        binding.profileContent.currentItem = Page
                        updatePage()
                    }
                }

                val user = SessionManager(context).user
                binding.profile.jsName1.text = user?.userFullname
                binding.profile.txtAboutme.text = user?.jobseekerAdditional?.jobseekerAbout
                binding.profile.username.text = user?.username
                if (activity != null) {
                    Glide.with(context!!)
                        .load(config().portAddress + "/photo/Profile/" + user?.photo).circleCrop()
                        .into(binding.profile.userPhoto)
                }
            }
        }

        return binding.root
    }


    fun updatePage() {
        val content = view?.findViewById<ViewPager2>(R.id.profileContent)
        content?.currentItem = Page

        view?.findViewById<MaterialButton>(R.id.manageProfile)?.backgroundTintList =
            resources.getColorStateList(R.color.white)
        view?.findViewById<MaterialButton>(R.id.CV)?.backgroundTintList =
            resources.getColorStateList(R.color.white)
        view?.findViewById<MaterialButton>(R.id.Preference)?.backgroundTintList =
            resources.getColorStateList(R.color.white)
        view?.findViewById<MaterialButton>(R.id.attachment)?.backgroundTintList =
            resources.getColorStateList(R.color.white)
        view?.findViewById<MaterialButton>(R.id.myReview)?.backgroundTintList =
            resources.getColorStateList(R.color.white)
        view?.findViewById<MaterialButton>(R.id.myRecord)?.backgroundTintList =
            resources.getColorStateList(R.color.white)
        view?.findViewById<MaterialButton>(R.id.setting)?.backgroundTintList =
            resources.getColorStateList(R.color.white)
        when (Page) {
            0 -> view?.findViewById<MaterialButton>(R.id.manageProfile)?.backgroundTintList =
                resources.getColorStateList(R.color.danger_300)
            1 -> view?.findViewById<MaterialButton>(R.id.CV)?.backgroundTintList =
                resources.getColorStateList(R.color.danger_300)
            2 -> view?.findViewById<MaterialButton>(R.id.Preference)?.backgroundTintList =
                resources.getColorStateList(R.color.danger_300)
            3 -> view?.findViewById<MaterialButton>(R.id.attachment)?.backgroundTintList =
                resources.getColorStateList(R.color.danger_300)
            4 -> view?.findViewById<MaterialButton>(R.id.myReview)?.backgroundTintList =
                resources.getColorStateList(R.color.danger_300)
            5 -> view?.findViewById<MaterialButton>(R.id.myRecord)?.backgroundTintList =
                resources.getColorStateList(R.color.danger_300)
            6 -> view?.findViewById<MaterialButton>(R.id.setting)?.backgroundTintList =
                resources.getColorStateList(R.color.danger_300)
        }
    }
}