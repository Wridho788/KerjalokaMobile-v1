package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Bottomsheet

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.FilterIndustriAPI
import com.ciptakerjaarunika.kerjaloka.api.FilterLocationAPI
import com.ciptakerjaarunika.kerjaloka.api.FilterSizeCompanyAPI
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Adapter.IndustriAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Adapter.LocationAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Adapter.SizeCompanyAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Model.industri_model
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Model.location_model
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Model.size_company_model
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip

class FilterCompany : SuperBottomSheetFragment(), iUpdate {

    private var list_location: List<location_model>? = listOf()
    private var list_industri: List<industri_model>? = listOf()
    private var list_size_company: List<size_company_model>? = listOf()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.layout_filter_company, container, false)
        val chipLocation = view.findViewById<Chip>(R.id.lokasi)
        val chipIndustri = view.findViewById<Chip>(R.id.industri)
        val chipSizeCompany = view.findViewById<Chip>(R.id.size_company)
        val listView = view.findViewById<RecyclerView>(R.id.list_location_view)
        val listIndustri = view.findViewById<RecyclerView>(R.id.list_industri_view)
        val listSizeCompany = view.findViewById<RecyclerView>(R.id.list_size_company_view)
        val btnConfirm = view.findViewById<MaterialButton>(R.id.btn_konfirmasi)
        val search_bar = view.findViewById<androidx.appcompat.widget.SearchView>(R.id.search_filter)
        val btn_hapus = view.findViewById<TextView>(R.id.btn_hapus_check)

        view.setLayoutParams(
            RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT
            )
        )

        val Context = this
        FilterLocationAPI().getLocationAsync(context) {
            if (it != null) {
                list_location = it
                val thisActivity = this
                listView.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = LocationAdapter(list_location!!, context, thisActivity)
                    listView.adapter = adapter
                }
            }
        }

        FilterIndustriAPI().getIndustriAsync(context){
            if (it != null) {
                list_industri = it
                val thisActivity = this
                listIndustri.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = IndustriAdapter(list_industri!!, context, thisActivity)
                }
            }
        }

        FilterSizeCompanyAPI().getSizeIndustriAsync(context){
            if (it != null) {
                list_size_company = it
                val thisActivity = this
                listSizeCompany.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = SizeCompanyAdapter(list_size_company!!, context, thisActivity)
                }

            }
        }


        FilterLocationAPI()
        FilterIndustriAPI()
        FilterSizeCompanyAPI()
        listIndustri.visibility = View.GONE
        listSizeCompany.visibility = View.GONE
        search_bar.visibility = View.GONE

        chipLocation.setOnClickListener {
            search_bar.visibility = View.GONE
            listView.visibility = View.VISIBLE
            listIndustri.visibility = View.GONE
            listSizeCompany.visibility = View.GONE
        }

        chipIndustri.setOnClickListener {
            listView.visibility = View.GONE
            listSizeCompany.visibility = View.GONE
            listIndustri.visibility = View.VISIBLE
            search_bar.visibility = View.GONE
        }

        chipSizeCompany.setOnClickListener {
            listView.visibility = View.GONE
            listIndustri.visibility = View.GONE
            listSizeCompany.visibility = View.VISIBLE
            search_bar.visibility = View.GONE
        }
//        search_bar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            val context = this
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                if(query?.isNotEmpty() == true) {
////                    FilterLocationAPI().getLocationAsync()
//                }
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                TODO("Not yet implemented")
//            }
//        })
        btnConfirm.setOnClickListener{
            Log.d("check ${list_location}, ${list_industri}, ${list_size_company}", list_location.toString())
        }

        btn_hapus.setOnClickListener{
            list_location = listOf()
            list_industri = listOf()
            list_size_company = listOf()

            listView.adapter?.notifyDataSetChanged()
            listIndustri.adapter?.notifyDataSetChanged()
            listSizeCompany.adapter?.notifyDataSetChanged()
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

    override fun isSheetCancelableOnTouchOutside(): Boolean {
        return true
    }

    override fun updateLocation(locations: List<location_model>) {
        list_location = locations
    }

    override fun updateField(fields: List<industri_model>) {
        list_industri = fields
    }

    override fun updateSizeCompany(sizes: List<size_company_model>) {
        list_size_company = sizes
    }

}

interface iUpdate {
    fun updateLocation(locations: List<location_model>)
    fun updateField(fields: List<industri_model>)
    fun updateSizeCompany(sizes: List<size_company_model>)
}