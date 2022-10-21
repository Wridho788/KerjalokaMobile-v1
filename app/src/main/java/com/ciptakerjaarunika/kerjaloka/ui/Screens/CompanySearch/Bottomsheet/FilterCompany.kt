package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Bottomsheet

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.DataAPI
import com.ciptakerjaarunika.kerjaloka.api.FilterIndustriAPI
import com.ciptakerjaarunika.kerjaloka.api.FilterLocationAPI
import com.ciptakerjaarunika.kerjaloka.api.FilterSizeCompanyAPI
import com.ciptakerjaarunika.kerjaloka.model.Data.LocationFilter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Adapter.IndustriAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Adapter.LocationAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Adapter.SizeCompanyAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Model.industri_model
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Model.searchCompanyRequest
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Model.size_company_model
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.iSearchCompany
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import java.util.*

class FilterCompany(var request: searchCompanyRequest, val updateData: iSearchCompany) :
    SuperBottomSheetFragment() {
    var list_location: List<LocationFilter> = listOf()
    var list_industri: List<industri_model> = listOf()
    var list_size_company: List<size_company_model> = listOf()
    private var filterType = 1

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
        val search_bar = view.findViewById<SearchView>(R.id.search_filter)
        val btn_hapus = view.findViewById<TextView>(R.id.btn_hapus_check)

        view.layoutParams = RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )

        DataAPI().GetLocations(context) {
            if (it != null) {
                list_location = it
                val thisActivity = this
                listView.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = LocationAdapter(request.location, list_location, context)
                    listView.adapter = adapter
                }
            }
        }

        FilterIndustriAPI().getIndustriAsync(context) {
            if (it != null) {
                list_industri = it
                listIndustri.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = IndustriAdapter(request.industry, list_industri, context)
                }
            }
        }

        FilterSizeCompanyAPI().getSizeIndustriAsync(context) {
            if (it != null) {
                list_size_company = it
                val thisActivity = this
                listSizeCompany.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = SizeCompanyAdapter(request.size, list_size_company, context)
                }

            }
        }


        FilterLocationAPI()
        FilterIndustriAPI()
        FilterSizeCompanyAPI()
        listIndustri.visibility = GONE
        listSizeCompany.visibility = GONE

        chipLocation.setOnClickListener {
            filterType = 1
            search_bar.visibility = VISIBLE
            listView.visibility = VISIBLE
            listIndustri.visibility = GONE
            listSizeCompany.visibility = GONE
        }

        chipIndustri.setOnClickListener {
            filterType = 2
            search_bar.visibility = VISIBLE
            listView.visibility = GONE
            listSizeCompany.visibility = GONE
            listIndustri.visibility = VISIBLE
        }

        chipSizeCompany.setOnClickListener {
            search_bar.visibility = GONE
            listView.visibility = GONE
            listIndustri.visibility = GONE
            listSizeCompany.visibility = VISIBLE
        }
        search_bar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query?.isNotEmpty() == true) {
                    when (filterType) {
                        1 -> {
                            var temp = list_location.filter { data ->
                                "${data.city}, ${data.province}".lowercase(Locale.getDefault()).contains(query)
                            }
                            listView?.apply {
                                layoutManager = LinearLayoutManager(context)
                                adapter = LocationAdapter(request.location, temp!!, context)
                            }
                            listView?.adapter?.notifyDataSetChanged()
                        }
                        2 -> {
                            var temp = list_industri.filter { data ->
                                "${data.fieldName}".lowercase(Locale.getDefault()).contains(query)
                            }
                            listIndustri?.apply {
                                layoutManager = LinearLayoutManager(context)
                                adapter = IndustriAdapter(request.industry, temp!!, context)
                            }
                            listIndustri?.adapter?.notifyDataSetChanged()
                        }
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
        btnConfirm.setOnClickListener {
            request.location = list_location.filter { data -> data.checked == true }
                .map { data -> data.locationsNo }
            request.industry =
                list_industri.filter { data -> data.checked }.map { data -> data.fieldNo }
            request.size =
                list_size_company.filter { data -> data.checked }.map { data -> data.sizeNo }
            updateData.searchCompany(request)
            this.dismiss()
        }

        btn_hapus.setOnClickListener {
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
        return (displayMetrics.heightPixels * 0.8).toInt()
    }

    override fun isSheetCancelableOnTouchOutside(): Boolean {
        return true
    }
}