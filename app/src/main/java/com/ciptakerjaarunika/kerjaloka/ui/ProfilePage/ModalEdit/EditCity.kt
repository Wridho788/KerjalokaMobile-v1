package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ModalEdit

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
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.JobAPI
import com.ciptakerjaarunika.kerjaloka.model.Data.LocationFilter
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter.EditCityAdapter
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.manage_profile.iEditBasic

class EditCity(private val cityNo : Int?,private val locations : List<LocationFilter>,private val iEditBasic: iEditBasic ) : SuperBottomSheetFragment(), iCity {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val view = View.inflate(context, R.layout.global_modal_edit, null)
        val title = view.findViewById<TextView>(R.id.judul_bottom_sheet)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycleEdit)


        var searchInput = view.findViewById<SearchView>(R.id.search_filter)
        searchInput.visibility = VISIBLE

        searchInput.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val keyword = newText.toString().toLowerCase()
                if (keyword.isNullOrEmpty()) {
                    recyclerView.apply {
                        layoutManager = LinearLayoutManager(activity)
                        adapter = EditCityAdapter(cityNo, locations, iEditBasic, this@EditCity)
                    }
                    recyclerView.adapter?.notifyDataSetChanged()
                } else {
                    var temp = locations?.filter { data ->
                        "${data.city}, ${data.province}".toLowerCase().contains(keyword)
                    }
                    recyclerView.apply {
                        layoutManager = LinearLayoutManager(activity)
                        adapter = temp?.let {
                            EditCityAdapter(cityNo,
                                it, iEditBasic, this@EditCity)
                        }
                    }
                    recyclerView.adapter?.notifyDataSetChanged()
                }
                return true;
            }
        })
        title.text = "Pilih Kota Domisili"

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycleEdit)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = EditCityAdapter(cityNo, locations, iEditBasic, this@EditCity)
        }
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

    override fun close() {
        this.dismiss()
    }
}

interface iCity{
    fun close()
}