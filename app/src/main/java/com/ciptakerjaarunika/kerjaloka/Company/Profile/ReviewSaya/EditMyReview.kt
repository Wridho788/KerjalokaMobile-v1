package com.ciptakerjaarunika.kerjaloka.Company.Profile.ReviewSaya

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.ciptakerjaarunika.kerjaloka.Company.Package.Adapter.myPackageAdapter
import com.ciptakerjaarunika.kerjaloka.Company.Package.Listener.ShowModalHistory
import com.ciptakerjaarunika.kerjaloka.Company.Package.history_modal
import com.ciptakerjaarunika.kerjaloka.Company.Package.pack
import com.ciptakerjaarunika.kerjaloka.R

class EditMyReview : SuperBottomSheetFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_my_review, container, false)
    }

    companion object {

    }

    internal fun assignAdapter(list: List<pack>): myPackageAdapter {
        return myPackageAdapter(requireContext(), list, object : ShowModalHistory {
            override fun showDetail(pack: pack) {
                val sheet = history_modal()
                Log.d("data", pack.orderNo.toString())
                activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "DemoBottomSheetFragment") }
            }
        })
    }
}