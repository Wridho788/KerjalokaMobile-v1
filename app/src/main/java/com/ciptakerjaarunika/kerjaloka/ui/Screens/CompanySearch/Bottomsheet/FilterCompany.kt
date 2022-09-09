package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Bottomsheet

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.FilterLocationAPI
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Adapter.LocationAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Model.location_model
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip

class FilterCompany : SuperBottomSheetFragment(), iUpdate {
    private var list_location: List<location_model>? = listOf()
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
        val listIndustri = view.findViewById<ListView>(R.id.list_industri_view)
        val listSizeCompany = view.findViewById<ListView>(R.id.list_size_company_view)
        val btnConfirm = view.findViewById<MaterialButton>(R.id.btn_konfirmasi)
        val search_bar = view.findViewById<androidx.appcompat.widget.SearchView>(R.id.search_filter)
        val btn_hapus_check = view.findViewById<TextView>(R.id.btn_hapus_check)


        chipLocation.setOnClickListener {

            listView.visibility = View.VISIBLE
            listIndustri.visibility = View.GONE
            listSizeCompany.visibility = View.GONE
        }

        chipIndustri.setOnClickListener {
            listView.visibility = View.GONE
            listSizeCompany.visibility = View.GONE
            listIndustri.visibility = View.VISIBLE

        }

        chipSizeCompany.setOnClickListener {
            listView.visibility = View.GONE
            listIndustri.visibility = View.GONE
            listSizeCompany.visibility = View.VISIBLE
            search_bar.visibility = View.GONE
        }


        val Context = this
        FilterLocationAPI().getLocationAsync(context) {
//            Log.d("response location", it.toString())
            if (it != null) {
                list_location = it
                Log.d("response location", list_location.toString())
                val thisActivity = this
                listView.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = LocationAdapter(list_location!!, context, thisActivity)
                    listView.adapter = adapter
                }
            }
        }


//            if (it != null) {
//                listView.apply {
//                    adapter = LocationAdapter(, context)
//                    listView.adapter = adapter
//                    listView.smoothScrollToPosition(adapter.count)
//                }
//            }

//        FilterIndustriAPI().getIndustriAsync(context) {
//            Log.d("response industri", it.toString())
//            if (it != null) {
//                listIndustri.apply {
//                    adapter = IndustriAdapter(it, context)
//                    listIndustri.adapter = adapter
//                }
//            }
//        }

//        FilterSizeCompanyAPI().getSizeIndustriAsync(context) {
//            Log.d("response size", it.toString())
//            if(it != null) {
//                listSizeCompany.apply {
//                    adapter = SizeCompanyAdapter(it, context)
//                    listSizeCompany.adapter = adapter
//                }
//            }
//        }

        btnConfirm.setOnClickListener{
            Toast.makeText(activity,"confirm", Toast.LENGTH_SHORT).show()
        }
        return view
    }

//    override fun isSheetAlwaysExpanded(): Boolean {
//        return true
//    }

    @SuppressLint("Range")
    override fun getExpandedHeight(): Int {
        return 1800
    }

    override fun isSheetCancelableOnTouchOutside(): Boolean {
        return true
    }

    override fun updateLocation(locations: List<location_model>) {
        list_location = locations;
        Log.d("Data sended", locations.toString())
    }

}
interface iUpdate{
    fun updateLocation(locations :List<location_model>)
}