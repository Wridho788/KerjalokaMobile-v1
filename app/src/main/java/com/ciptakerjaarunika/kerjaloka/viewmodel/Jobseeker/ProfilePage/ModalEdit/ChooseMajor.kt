package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.ModalEdit

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.`interface`.iUpdateMajor
import com.ciptakerjaarunika.kerjaloka.model.Data.Major
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Companyjobdetail.Bottomsheet.iChooseMajor
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.Adapter.ChooseMajorAdapter
import java.util.*


class ChooseMajor(val value: Int?, val majors: List<Major>, val iUpdateMajor: iUpdateMajor) :
    SuperBottomSheetFragment(),
    iChooseMajor {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = View.inflate(context, R.layout.global_modal_edit, null)

        val title = view.findViewById<TextView>(R.id.judul_bottom_sheet)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycleEdit)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = majors.let { it1 ->
                ChooseMajorAdapter(
                    value,
                    it1, this@ChooseMajor, iUpdateMajor
                )
            }
        }
        var searchInput = view.findViewById<SearchView>(R.id.search_filter)
        searchInput.visibility = View.VISIBLE
        searchInput.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }
            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextChange(newText: String?): Boolean {
                val keyword = newText.toString().lowercase(Locale.getDefault())
                if (keyword.isNullOrEmpty()) {
                    recyclerView.apply {
                        layoutManager = LinearLayoutManager(activity)
                        adapter = majors.let { it1 ->
                            ChooseMajorAdapter(
                                value,
                                it1, this@ChooseMajor, iUpdateMajor
                            )
                        }
                    }
                    recyclerView.adapter?.notifyDataSetChanged()
                } else {
                    var temp =
                        majors.filter { data -> data.majorName.lowercase(Locale.getDefault()).contains(keyword) }
                    recyclerView.apply {
                        layoutManager = LinearLayoutManager(activity)
                        adapter = temp?.let { it1 ->
                            ChooseMajorAdapter(
                                value,
                                it1, this@ChooseMajor, iUpdateMajor
                            )
                        }
                    }
                    recyclerView.adapter?.notifyDataSetChanged()
                }
                return true
            }
        })
        title.text = "Pilih Bidang Studi"
        return view
    }

    override fun getCornerRadius() = 30f

    override fun isSheetAlwaysExpanded(): Boolean {
        return true
    }

    @SuppressLint("Range")
    override fun getExpandedHeight(): Int {
        val displayMetrics = DisplayMetrics()
        (context as Activity?)!!.windowManager
            .defaultDisplay
            .getMetrics(displayMetrics)
        return (displayMetrics.heightPixels * 0.8).toInt()
    }

    override fun close() {
        this.dismiss()
    }
}

