package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.Bottomsheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.Bottomsheet.Adapter.LocationAdapter
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.iLocationPage
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.companyAddJob.Locations
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Model.locationModel

class BottomSheetEditJob( iLocationPage: iLocationPage) : SuperBottomSheetFragment(), iChooseLocation{
    private var list: List<locationModel>? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.layout_bottomsheet_edit_job, container, false)
        view.layoutParams = RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )

        val title = view.findViewById<TextView>(R.id.title_location)
        val rv_location = view.findViewById<RecyclerView>(R.id.list_location_view)

        title.text = "Lokasi"
        Locations().GetLocations(context){
            res -> list
            rv_location.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = LocationAdapter(res, this@BottomSheetEditJob )
            }
        }
        return view
    }
    override fun close(locationName: String) {
        Log.d(locationName, "location")
        this.dismiss()

    }
}

interface iChooseLocation{
    fun close(locationName: String)
}
