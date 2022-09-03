package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.manage_profile.ManageProfile
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.cvPage
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.manage_lampiran
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.manage_preference

class viewpagerAdapter (fragmentManager: FragmentManager, lifecycle: Lifecycle,): FragmentStateAdapter(fragmentManager,lifecycle){
    override fun getItemCount(): Int {
        return 7
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->{
                ManageProfile()
            }
            1->{
                cvPage()
            }
            2->{
                manage_preference()
            }
            3->{
                manage_lampiran()
            }
            4->{
                ManageProfile()
            }
            5->{
                cvPage()
            }
            6->{
                cvPage()
            }
            else -> {
                ManageProfile()
            }
        }
    }

}