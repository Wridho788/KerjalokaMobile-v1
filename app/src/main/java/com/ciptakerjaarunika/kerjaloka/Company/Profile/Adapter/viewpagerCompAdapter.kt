package com.ciptakerjaarunika.kerjaloka.Company.Profile.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ciptakerjaarunika.kerjaloka.Company.Profile.Profile.Profile
import com.ciptakerjaarunika.kerjaloka.Company.Profile.ReviewSaya.CompMyReview
import com.ciptakerjaarunika.kerjaloka.Company.Profile.Setting.AccountSetting
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.*

class viewpagerCompAdapter (fragmentManager: FragmentManager, lifecycle: Lifecycle,): FragmentStateAdapter(fragmentManager,lifecycle){
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->{
                Profile()
            }
            1->{
                CompMyReview()
            }
            2->{
                AccountSetting()
            }
            else -> {
                ManageProfile()
            }
        }
    }

}