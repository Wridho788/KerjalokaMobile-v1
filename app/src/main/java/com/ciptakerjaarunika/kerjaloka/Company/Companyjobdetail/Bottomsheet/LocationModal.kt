package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.Bottomsheet

import android.annotation.SuppressLint
import android.app.Activity
import android.opengl.Visibility
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.Bottomsheet.Adapter.LocationAdapter
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.ManageJobPage.iUpdateJobBasicInfo
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.iBasicInfoPage
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.`interface`.iCloseModal
import com.ciptakerjaarunika.kerjaloka.api.JobAPI
import com.ciptakerjaarunika.kerjaloka.model.Data.LocationFilter
import com.ciptakerjaarunika.kerjaloka.model.Job.CompanyJobDetail.JobLocation
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter.EditCityAdapter
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.manage_profile.iEditBasic
import com.google.android.material.button.MaterialButton

class LocationModal(var data : List<JobLocation>, private val locations : List<LocationFilter>,private val updateData: iUpdateJobBasicInfo ) : SuperBottomSheetFragment(), iUpdateLocation{

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val view = View.inflate(context, R.layout.global_modal_check_box, null)
        view.findViewById<TextView>(R.id.title).text = "Pilih Lokasi"
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycleEdit)

        var searchInput = view.findViewById<SearchView>(R.id.search_filter)
        searchInput.visibility = VISIBLE

        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = LocationAdapter(data, locations, this@LocationModal)
        }

        searchInput.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val keyword = newText.toString().toLowerCase()
                if (keyword.isNullOrEmpty()) {
                    recyclerView.apply {
                        layoutManager = LinearLayoutManager(activity)
                        adapter = LocationAdapter(data, locations, this@LocationModal)
                    }
                    recyclerView.adapter?.notifyDataSetChanged()
                } else {
                    var temp = locations?.filter { data ->
                        "${data.city}, ${data.province}".toLowerCase().contains(keyword)
                    }
                    recyclerView.apply {
                        layoutManager = LinearLayoutManager(activity)
                        adapter = temp?.let { LocationAdapter(data, it, this@LocationModal) }
                    }
                    recyclerView.adapter?.notifyDataSetChanged()
                }
                return true;
            }
        })

        view.findViewById<MaterialButton>(R.id.confirm_btn).setOnClickListener {
            Log.d("Location" , data.toString())
            updateData.updateLocation(data.distinct())
            this.dismiss()
        }
        return view
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

    override fun updateLocation(value: List<JobLocation>) {
        data = value
    }
}

interface iUpdateLocation{
    fun updateLocation(value : List<JobLocation>)
}