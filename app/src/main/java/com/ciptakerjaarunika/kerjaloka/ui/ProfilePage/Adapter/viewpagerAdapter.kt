package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.manage_profile.ManageProfile
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.cvPage

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
                ManageProfile()
            }
            3->{
                cvPage()
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