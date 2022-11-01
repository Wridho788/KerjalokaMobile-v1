package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ciptakerjaarunika.kerjaloka.model.Profile.JobseekerProfile
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.*

class viewpagerAdapter(
    var data: JobseekerProfile?,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 7

    override fun createFragment(position: Int): Fragment {
        var fragment = Fragment()
        when (position) {
            0 -> fragment = ManageProfile(data)
            1 -> fragment = cvPage()
            2 -> fragment = ManagePreferenceFragment(data)
            3 -> fragment = manage_lampiran()
            4 -> fragment = fragment_my_review_page()
            5 -> fragment = fragment_my_record_page()
            6 -> fragment = ManageUserSetting()
        }
        return fragment
    }


}