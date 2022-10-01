package com.ciptakerjaarunika.kerjaloka.Company.Profile.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ciptakerjaarunika.kerjaloka.Company.Profile.Profile.Profile
import com.ciptakerjaarunika.kerjaloka.Company.Profile.ReviewSaya.CompMyReview
import com.ciptakerjaarunika.kerjaloka.Company.Profile.Setting.AccountSetting
import com.ciptakerjaarunika.kerjaloka.Company.Profile.data
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ManageProfile
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyReview.Model.company_reviews

class viewpagerCompAdapter(val data: data?, fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                Profile(data)
            }
            1 -> {
                CompMyReview(data)
            }
            2 -> {
                AccountSetting(data)
            }
            else -> {
                ManageProfile(data = null)
            }
        }
    }

}