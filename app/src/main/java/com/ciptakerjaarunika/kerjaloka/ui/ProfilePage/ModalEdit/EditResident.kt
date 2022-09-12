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
import com.ciptakerjaarunika.kerjaloka.ui.JobPage.Adapter.BookmarkedJobAdapter
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter.EditGenderAdapter
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter.EditResidentAdapter
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.gender
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.residentList


class EditResident: SuperBottomSheetFragment() {

    private var layoutManager: RecyclerView.LayoutManager? =null
    private var adapter: RecyclerView.Adapter<EditResidentAdapter.EditResident>? = null
    private lateinit var editGenderAdapter: EditResidentAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = View.inflate(context, R.layout.global_modal_edit, null)
        val title = view.findViewById<TextView>(R.id.judul_bottom_sheet)
        title.text = "Pilih Kewarganegaraan"

        return view
    }

//    override fun getCornerRadius() = requireContext().resources.getDimension(R.dimen.demo_sheet_rounded_corner)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = ArrayList<residentList>()
        val residen1 = residentList(
            residentNo = 1,
            residentName = "Warga Negara Asli"
        )
        val residen2 = residentList(
            residentNo = 2,
            residentName = "Visa Belajar"
        )
        val residen3 = residentList(
            residentNo = 3,
            residentName = "Visa Kerja"
        )
        val residen4 = residentList(
            residentNo = 4,
            residentName = "Visa Kunjungan"
        )
        list.add(residen1)
        list.add(residen2)
        list.add(residen3)
        list.add(residen4)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycleEdit)
        layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        adapter = EditResidentAdapter(list)
        recyclerView.adapter = adapter
    }


    override fun isSheetAlwaysExpanded(): Boolean {
        return true
    }

    @SuppressLint("Range")
    override fun getExpandedHeight() = ViewGroup.LayoutParams.WRAP_CONTENT
}