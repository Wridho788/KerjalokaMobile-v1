package com.ciptakerjaarunika.kerjaloka.Company.Profile.ReviewSaya

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RatingBar
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.fragment_company_job_active_page
import com.ciptakerjaarunika.kerjaloka.Company.Package.Adapter.myPackageAdapter
import com.ciptakerjaarunika.kerjaloka.Company.Package.Listener.ShowModalHistory
import com.ciptakerjaarunika.kerjaloka.Company.Package.Model.Data
import com.ciptakerjaarunika.kerjaloka.Company.Package.history_modal
import com.ciptakerjaarunika.kerjaloka.Company.Package.pack
import com.ciptakerjaarunika.kerjaloka.Company.Package.pckHistory
import com.ciptakerjaarunika.kerjaloka.Company.Profile.ReviewSaya.Model.CategoryList
import com.ciptakerjaarunika.kerjaloka.Company.Profile.ReviewSaya.Model.DataX
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.UsersAPI
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyReview.Model.conRatingList
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyReview.Model.proRatingList
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.gson.Gson

class EditMyReview : SuperBottomSheetFragment() {

    var review: DataX? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val ratingBar = view.findViewById<RatingBar>(R.id.ratingBar)
        val txtComment = view.findViewById<EditText>(R.id.txt_review)
        val btn_Send = view.findViewById<MaterialButton>(R.id.sendReview)
        val pro1 = view.findViewById<Chip>(R.id.pro1)
        val pro2 = view.findViewById<Chip>(R.id.pro2)
        val pro3 = view.findViewById<Chip>(R.id.pro3)
        val pro4 = view.findViewById<Chip>(R.id.pro4)
        val pro5 = view.findViewById<Chip>(R.id.pro5)
        val pro6 = view.findViewById<Chip>(R.id.pro6)
        val pro7 = view.findViewById<Chip>(R.id.pro7)
        val con1 = view.findViewById<Chip>(R.id.con1)
        val con2 = view.findViewById<Chip>(R.id.con2)
        val con3 = view.findViewById<Chip>(R.id.con3)
        val con4 = view.findViewById<Chip>(R.id.con4)
        val con5 = view.findViewById<Chip>(R.id.con5)
        val con6 = view.findViewById<Chip>(R.id.con6)
        val con7 = view.findViewById<Chip>(R.id.con7)
        var ProRating = ArrayList<CategoryList>()
        var ConRating = ArrayList<CategoryList>()

        if (arguments != null) {
            val descFromBundle = arguments?.getString(EXTRA_EDIT_REVIEW)
            review = Gson().fromJson(descFromBundle, DataX::class.java)
            txtComment.setText(review?.comment)
            ratingBar.rating = review?.rating?.toFloat()!!
            var proRatingList = review?.proRating
            var conRatingList = review?.conRating
            review?.proRating?.forEach {
                if (it == "Disiplin") {
                    pro1.isChecked = true
                }
                if (it == "Kemauan Bekerja") {
                    pro2.isChecked = true
                }
                if (it == "Bekerja Keras") {
                    pro3.isChecked = true
                }
                if (it == "Emosional") {
                    pro4.isChecked = true
                }
                if (it == "Etika") {
                    pro5.isChecked = true
                }
                if (it == "Bekerja Sama") {
                    pro6.isChecked = true
                }
                if (it == "Kerapian") {
                    pro7.isChecked = true
                }
            }
            review?.conRating?.forEach {
                if (it == "Disiplin") {
                    con1.isChecked = true
                }
                if (it == "Kemauan Bekerja") {
                    con2.isChecked = true
                }
                if (it == "Bekerja Keras") {
                    con3.isChecked = true
                }
                if (it == "Emosional") {
                    con4.isChecked = true
                }
                if (it == "Etika") {
                    con5.isChecked = true
                }
                if (it == "Bekerja Sama") {
                    con6.isChecked = true
                }
                if (it == "Kerapian") {
                    con7.isChecked = true
                }
            }
//            ratingBar.onRatingBarChangeListener = RatingBar.OnRatingBarChangeListener{ ratingBar, nilai, b -> ratingBar.rating}
        }
        btn_Send.setOnClickListener {
            val Message = txtComment.text.toString()
            val UserNo = review?.userNo
            val Rating = ratingBar.rating.toInt()
            var newPro = ArrayList<CategoryList>()
            if (pro1.isChecked == true) {
                newPro.add(CategoryList(1))
            }
            if (pro2.isChecked == true) {
                newPro.add(CategoryList(2))
            }
            if (pro3.isChecked == true) {
                newPro.add(CategoryList(3))
            }
            if (pro4.isChecked == true) {
                newPro.add(CategoryList(4))
            }
            if (pro5.isChecked == true) {
                newPro.add(CategoryList(5))
            }
            if (pro6.isChecked == true) {
                newPro.add(CategoryList(6))
            }
            if (pro7.isChecked == true) {
                newPro.add(CategoryList(7))
            }
            var newCon = ArrayList<CategoryList>()
            if (con1.isChecked == true) {
                newCon.add(CategoryList(1))
            }
            if (con2.isChecked == true) {
                newCon.add(CategoryList(2))
            }
            if (con3.isChecked == true) {
                newCon.add(CategoryList(3))
            }
            if (con4.isChecked == true) {
                newCon.add(CategoryList(4))
            }
            if (con5.isChecked == true) {
                newCon.add(CategoryList(5))
            }
            if (con6.isChecked == true) {
                newCon.add(CategoryList(6))
            }
            if (con7.isChecked == true) {
                newCon.add(CategoryList(7))
            }
            ProRating = newPro
            ConRating = newCon
            if (UserNo != null) {
                UsersAPI().SendReview(UserNo, Message, Rating, ProRating, ConRating, context){}
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_edit_my_review, container, false)

        return view
    }

    companion object {
        var EXTRA_EDIT_REVIEW = "extra_editReview"
    }

    internal fun assignAdapter(list: List<Data>): myPackageAdapter {
        return myPackageAdapter(requireContext(), list, object : ShowModalHistory {
            override fun showDetail(pack: Data) {
                val sheet = history_modal()
                Log.d("data", pack.orderNo.toString())
                activity?.let { it1 ->
                    sheet.show(
                        it1.supportFragmentManager,
                        "DemoBottomSheetFragment"
                    )
                }
            }
        })
    }

    @SuppressLint("Range")
    override fun getExpandedHeight() = ViewGroup.LayoutParams.WRAP_CONTENT
}