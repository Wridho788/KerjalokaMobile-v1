package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Bottomsheet

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.FilterLocationAPI
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Adapter.LocationAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Model.location_model
import com.google.android.material.chip.Chip

class FilterCompany : SuperBottomSheetFragment() {
    // Declaring the DataModel Array
    private var dataModel: List<location_model>? = null
    // Declaring the elements from the main layout file
    private lateinit var adapter: LocationAdapter
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
        val listView = view.findViewById<ListView>(R.id.list_location_view)

        chipLocation.setOnClickListener{
            Toast.makeText(activity,"location list", Toast.LENGTH_LONG).show()
        }

        chipIndustri.setOnClickListener{
            Toast.makeText(activity, "industri list", Toast.LENGTH_SHORT).show()
        }

        chipSizeCompany.setOnClickListener {
            Toast.makeText(activity, "Size Company list", Toast.LENGTH_SHORT).show()
        }

        val Context = this
        FilterLocationAPI().getLocationAsync(context) {
            Log.d("response location", it.toString())
            if(it!=null) {
                listView.apply {
                    adapter = LocationAdapter(it, context)
                    listView.adapter = adapter
                }
            }
        }
        return view
    }

    override fun isSheetAlwaysExpanded(): Boolean {
        return true
    }

    @SuppressLint("Range")
    override fun getExpandedHeight(): Int {
        return 1800
    }

    override fun isSheetCancelableOnTouchOutside(): Boolean {
        return true
    }

}