package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.CompanyReview.Bottomsheet

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Profile.ReviewSaya.Model.CategoryList
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Profile.ReviewSaya.Model.DataX
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.UsersAPI
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip

class SendReview(val CompanyNo: Long, val fragmentId: Int, val GotoFragment: Fragment) :
    SuperBottomSheetFragment() {
    var review: DataX? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.layout_send_review, container, false)
        val ratingBar = view.findViewById<RatingBar>(R.id.ratingBar)
        val txtComment = view.findViewById<EditText>(R.id.txt_review)
        val btn_send_review = view.findViewById<MaterialButton>(R.id.sendReview)
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
        pro1.setOnClickListener {
            if (con1.isChecked) {
                con1.isChecked = false
            }
        }
        pro2.setOnClickListener {
            if (con2.isChecked) {
                con2.isChecked = false
            }
        }
        pro3.setOnClickListener {
            if (con3.isChecked) {
                con3.isChecked = false
            }
        }
        pro4.setOnClickListener {
            if (con4.isChecked) {
                con4.isChecked = false
            }
        }
        pro5.setOnClickListener {
            if (con5.isChecked) {
                con5.isChecked = false
            }
        }
        pro6.setOnClickListener {
            if (con6.isChecked) {
                con6.isChecked = false
            }
        }
        pro7.setOnClickListener {
            if (con7.isChecked) {
                con7.isChecked = false
            }
        }

        con1.setOnClickListener {
            if (pro1.isChecked) {
                pro1.isChecked = false
            }
        }
        con2.setOnClickListener {
            if (pro2.isChecked) {
                pro2.isChecked = false
            }
        }
        con3.setOnClickListener {
            if (pro3.isChecked) {
                pro3.isChecked = false
            }
        }
        con4.setOnClickListener {
            if (pro4.isChecked) {
                pro4.isChecked = false
            }
        }
        con5.setOnClickListener {
            if (pro5.isChecked) {
                pro5.isChecked = false
            }
        }
        con6.setOnClickListener {
            if (pro6.isChecked) {
                pro6.isChecked = false
            }
        }
        con7.setOnClickListener {
            if (pro7.isChecked) {
                pro7.isChecked = false
            }
        }
        review?.proRating?.forEach {
            if (it == "Disiplin" || it == "Gaji dan Tunjangan") {
                pro1.isChecked = true
            }
            if (it == "Kemauan Bekerja" || it == "Tingkat Stress") {
                pro2.isChecked = true
            }
            if (it == "Bekerja Keras" || it == "Jumlah Pekerjaan") {
                pro3.isChecked = true
            }
            if (it == "Emosional" || it == "Manajemen") {
                pro4.isChecked = true
            }
            if (it == "Etika" || it == "Lingkungan Pekerjaan") {
                pro5.isChecked = true
            }
            if (it == "Bekerja Sama" || it == "Fleksibilitas Waktu") {
                pro6.isChecked = true
            }
            if (it == "Kerapian" || it == "Pengembangan Karir") {
                pro7.isChecked = true
            }
        }
        review?.conRating?.forEach {
            if (it == "Disiplin" || it == "Gaji dan Tunjangan") {
                con1.isChecked = true
            }
            if (it == "Kemauan Bekerja" || it == "Tingkat Stress") {
                con2.isChecked = true
            }
            if (it == "Bekerja Keras" || it == "Jumlah Pekerjaan") {
                con3.isChecked = true
            }
            if (it == "Emosional" || it == "Manajemen") {
                con4.isChecked = true
            }
            if (it == "Etika" || it == "Lingkungan Pekerjaan") {
                con5.isChecked = true
            }
            if (it == "Bekerja Sama" || it == "Fleksibilitas Waktu") {
                con6.isChecked = true
            }
            if (it == "Kerapian" || it == "Pengembangan Karir") {
                con7.isChecked = true
            }
        }

        btn_send_review.setOnClickListener {

            val Message = txtComment?.text.toString()
            val Rating = ratingBar?.rating?.toInt()
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
            if (ProRating.size == 0) {
                showMessage("Pilih minimal 1 kelebihan")
            } else if (ConRating.size == 0) {
                showMessage("Pilih minimal 1 kekurangan")
            } else if (ProRating.size > 3) {
                showMessage("Pilih maksimal hanya 3 kelebihan")
            } else if (ConRating.size > 3) {
                showMessage("Pilih maksimal hanya 3 'kekurangan'")
            } else if (Message.isNullOrEmpty() && Message.isEmpty()) {
                showMessage("Pesan review tidak boleh kosong")
            } else {
                UsersAPI().SendReview(CompanyNo, Message, Rating!!, ProRating, ConRating, context) {
                    if (!it?.message.isNullOrEmpty()) {
                        Toast.makeText(activity, it?.message, Toast.LENGTH_LONG).show()
                    }
                    if (it != null && it.code == 210) {
                        this.dismiss()
                        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
                        ft.replace(fragmentId, GotoFragment, "companyReviewFragment")
                        ft.commit()
                    }
                }
            }
        }
        return view
    }

    @SuppressLint("Range")
    override fun getExpandedHeight(): Int {
        val displayMetrics = DisplayMetrics()
        (context as Activity?)!!.windowManager
            .defaultDisplay
            .getMetrics(displayMetrics)
        return (displayMetrics.heightPixels * 0.8).toInt()
    }


    override fun isSheetAlwaysExpanded(): Boolean {
        return true
    }

    override fun isSheetCancelableOnTouchOutside(): Boolean {
        return true
    }


    fun showMessage(message: String?) {
        if (!message.isNullOrEmpty()) {
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
        }
    }
}
