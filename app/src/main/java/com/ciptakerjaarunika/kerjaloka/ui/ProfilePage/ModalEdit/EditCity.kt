package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ModalEdit

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter.EditCityAdapter
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter.EditGenderAdapter
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.gender
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.listCity

class EditCity : SuperBottomSheetFragment() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<EditCityAdapter.EditCity>? = null
    private lateinit var editCityAdapter: EditCityAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = View.inflate(context, R.layout.global_modal_edit, null)
        val title = view.findViewById<TextView>(R.id.judul_bottom_sheet)
        title.text = "Pilih Kota Domisili"

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val City = ArrayList<listCity>()
        val city1 = listCity(
            city = "Kabupaten Simeulue",
            country = "Indonesia",
            locationsNo = 1,
            province = "Aceh"
        )
        val city2 = listCity(
            city = "Kota Sabang",
            country = "Indonesia",
            locationsNo = 3,
            province = "Aceh"
        )
        val city3 = listCity(
            city = "Kota Banda Aceh",
            country = "Indonesia",
            locationsNo = 4,
            province = "Aceh"
        )
        val city4 = listCity(
            city = "Kabupaten Pidie Jaya",
            country = "Indonesia",
            locationsNo = 5,
            province = "Aceh"
        )
        City.add(city1)
        City.add(city2)
        City.add(city3)
        City.add(city4)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycleEdit)
        layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        adapter = EditCityAdapter(City)
        recyclerView.adapter = adapter
    }


    override fun isSheetAlwaysExpanded(): Boolean {
        return true
    }

    @SuppressLint("Range")
    override fun getExpandedHeight() = ViewGroup.LayoutParams.WRAP_CONTENT
}