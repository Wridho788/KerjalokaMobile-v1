package com.ciptakerjaarunika.kerjaloka.Company.Profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.ciptakerjaarunika.kerjaloka.Company.Profile.Adapter.viewpagerCompAdapter
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.company_profile_api
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentProfilePageBinding
import com.ciptakerjaarunika.kerjaloka.ui.Global.DeactivatedAccount
import com.google.android.material.button.MaterialButton


class ProfilePage(var Page: Int) : Fragment() {
    private lateinit var binding: FragmentProfilePageBinding
    private lateinit var viewpagerAdapter: viewpagerCompAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfilePageBinding.inflate(layoutInflater)
        company_profile_api().CompanyGetProfileData(context) { response ->
            if (response != null) {
                binding.spinnerProfile.visibility = View.GONE
                binding.profileComp.visibility = View.VISIBLE
                val compName = view?.findViewById<TextView>(R.id.jsName1)
                val username = view?.findViewById<TextView>(R.id.username)

                lifecycleScope.launchWhenResumed {
                    viewpagerAdapter =
                        viewpagerCompAdapter(response.data, parentFragmentManager, lifecycle)
                    with(binding) {
                        binding.CompProfileContent.adapter = viewpagerAdapter
                        binding.manageProfile.setOnClickListener {
                            Page = 0
                            updatePage()
                            binding.CompProfileContent.currentItem = Page
                        }
                        binding.myReview.setOnClickListener {
                            Page = 1
                            updatePage()
                            binding.CompProfileContent.currentItem = Page
                        }
                        binding.accSetting.setOnClickListener {
                            Page = 2
                            updatePage()
                            binding.CompProfileContent.currentItem = Page
                        }
                    }
                }
                compName?.text = response.data.companyName
                username?.text = response.data.username

                if (activity != null)
                    if (activity != null) {
                        Glide.with(view!!.context)
                            .load(config().portAddress + "/photo/Profile/" + response.data.logo)
                            .fitCenter()
                            .into(view!!.findViewById<ImageView>(R.id.compLogo))
                    }
            } else {
                val intent = Intent(context, DeactivatedAccount::class.java)
                startActivity(intent)
            }
        }
        return binding.root
    }

    fun updatePage() {
        val content = view?.findViewById<ViewPager2>(R.id.Comp_profileContent)
        content?.currentItem = Page
        view?.findViewById<MaterialButton>(R.id.manageProfile)?.backgroundTintList =
            resources.getColorStateList(R.color.white)
        view?.findViewById<MaterialButton>(R.id.myReview)?.backgroundTintList =
            resources.getColorStateList(R.color.white)
        view?.findViewById<MaterialButton>(R.id.accSetting)?.backgroundTintList =
            resources.getColorStateList(R.color.white)
        when (Page) {
            0 -> view?.findViewById<MaterialButton>(R.id.manageProfile)?.backgroundTintList =
                resources.getColorStateList(R.color.danger_300)
            1 -> view?.findViewById<MaterialButton>(R.id.myReview)?.backgroundTintList =
                resources.getColorStateList(R.color.danger_300)
            2 -> view?.findViewById<MaterialButton>(R.id.accSetting)?.backgroundTintList =
                resources.getColorStateList(R.color.danger_300)
        }
    }

}

