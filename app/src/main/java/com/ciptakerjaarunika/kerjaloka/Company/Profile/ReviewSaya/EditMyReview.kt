package com.ciptakerjaarunika.kerjaloka.Company.Profile.ReviewSaya

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RatingBar
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.ciptakerjaarunika.kerjaloka.Company.Package.Adapter.myPackageAdapter
import com.ciptakerjaarunika.kerjaloka.Company.Package.Listener.ShowModalHistory
import com.ciptakerjaarunika.kerjaloka.Company.Package.Model.Data
import com.ciptakerjaarunika.kerjaloka.Company.Package.history_modal
import com.ciptakerjaarunika.kerjaloka.Company.Package.pack
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyReview.Model.conRatingList
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyReview.Model.proRatingList
import com.google.android.material.button.MaterialButton

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
        val view =inflater.inflate(R.layout.fragment_edit_my_review, container, false)
        val ratingBar = view.findViewById<RatingBar>(R.id.ratingBar)
        val txtComment = view.findViewById<EditText>(R.id.txt_review)
        val btn_Send = view.findViewById<MaterialButton>(R.id.sendReview)

        ratingBar.onRatingBarChangeListener = RatingBar.OnRatingBarChangeListener{ ratingBar, nilai, b -> ratingBar.rating}
        val proRatingList = ArrayList<proRatingList>()
        val conRatingList = ArrayList<conRatingList>()



        return view
    }

    companion object {

    }

    internal fun assignAdapter(list: List<Data>): myPackageAdapter {
        return myPackageAdapter(requireContext(), list, object : ShowModalHistory {
            override fun showDetail(pack: Data) {
                val sheet = history_modal()
                Log.d("data", pack.orderNo.toString())
                activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "DemoBottomSheetFragment") }
            }
        })
    }
}