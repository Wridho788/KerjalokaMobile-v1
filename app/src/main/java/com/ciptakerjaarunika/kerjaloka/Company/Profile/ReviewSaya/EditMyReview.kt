package com.ciptakerjaarunika.kerjaloka.Company.Profile.ReviewSaya

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.os.Message
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.ciptakerjaarunika.kerjaloka.Company.Package.Adapter.myPackageAdapter
import com.ciptakerjaarunika.kerjaloka.Company.Package.Listener.ShowModalHistory
import com.ciptakerjaarunika.kerjaloka.Company.Package.Model.Data
import com.ciptakerjaarunika.kerjaloka.Company.Package.history_modal
import com.ciptakerjaarunika.kerjaloka.Company.Profile.ReviewSaya.Model.CategoryList
import com.ciptakerjaarunika.kerjaloka.Company.Profile.ReviewSaya.Model.DataX
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.`interface`.iRefreshData
import com.ciptakerjaarunika.kerjaloka.api.UsersAPI
import com.ciptakerjaarunika.kerjaloka.enum.Role
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.gson.Gson

class EditMyReview(val iRefreshData : iRefreshData) : SuperBottomSheetFragment() {

    var review: DataX? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return if (SessionManager(context).user?.roleNo == Role.Jobseekers.value)
            inflater.inflate(R.layout.fragment_jobseeker_edit_review, container, false)
        else
            inflater.inflate(R.layout.fragment_edit_my_review, container, false)
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

            pro1.setOnClickListener {
                if(con1.isChecked){con1.isChecked = false}
            }
            pro2.setOnClickListener {
                if(con2.isChecked){con2.isChecked = false}
            }
            pro3.setOnClickListener {
                if(con3.isChecked){con3.isChecked = false}
            }
            pro4.setOnClickListener {
                if(con4.isChecked){con4.isChecked = false}
            }
            pro5.setOnClickListener {
                if(con5.isChecked){con5.isChecked = false}
            }
            pro6.setOnClickListener {
                if(con6.isChecked){con6.isChecked = false}
            }
            pro7.setOnClickListener {
                if(con7.isChecked){con7.isChecked = false}
            }

            con1.setOnClickListener {
                if(pro1.isChecked){pro1.isChecked = false}
            }
            con2.setOnClickListener {
                if(pro2.isChecked){pro2.isChecked = false}
            }
            con3.setOnClickListener {
                if(pro3.isChecked){pro3.isChecked = false}
            }
            con4.setOnClickListener {
                if(pro4.isChecked){pro4.isChecked = false}
            }
            con5.setOnClickListener {
                if(pro5.isChecked){pro5.isChecked = false}
            }
            con6.setOnClickListener {
                if(pro6.isChecked){pro6.isChecked = false}
            }
            con7.setOnClickListener {
                if(pro7.isChecked){pro7.isChecked = false}
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
            if(ProRating.size == 0){
                showMessage("Pilih minimal 1 kelebihan")
            }
            else if(ConRating.size == 0){
                showMessage("Pilih minimal 1 kekurangan")
            }
            else if(ProRating.size > 3){
                showMessage("Pilih maksimal hanya 3 kelebihan")
            }
            else if(ConRating.size > 3){
                showMessage("Pilih maksimal hanya 3 'kekurangan'")
            }
            else if (UserNo != null) {
                UsersAPI().SendReview(UserNo, Message, Rating, ProRating, ConRating, context){
                    if(!it?.message.isNullOrEmpty()) {
                        Toast.makeText(activity, it?.message, Toast.LENGTH_LONG).show()
                    }
                    if(it!= null && it.code == 210){
                        iRefreshData.refresh()
                        this.dismiss()
                    }
                }
            }
        }
    }

    fun showMessage(message : String?){
        if(!message.isNullOrEmpty()) {
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
        }
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


    override fun isSheetAlwaysExpanded(): Boolean {
        return true
    }

    @SuppressLint("Range")
    override fun getExpandedHeight(): Int {
        val displayMetrics = DisplayMetrics()
        (context as Activity?)!!.windowManager
            .defaultDisplay
            .getMetrics(displayMetrics)
        return (displayMetrics.heightPixels * 0.8).toInt();
    }
}