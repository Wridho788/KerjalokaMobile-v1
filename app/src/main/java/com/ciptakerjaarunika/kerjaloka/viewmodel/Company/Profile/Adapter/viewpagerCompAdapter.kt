package com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Profile.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Profile.Profile.Profile
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Profile.ReviewSaya.CompMyReview
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Profile.Setting.AccountSetting
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Profile.data

class viewpagerCompAdapter(
    val data: data?,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        var fragment = Fragment()
        when (position) {
            0 -> fragment = Profile(data)
            1 -> fragment = CompMyReview(data)
            2 -> fragment = AccountSetting(data)
        }
        return fragment
    }

}