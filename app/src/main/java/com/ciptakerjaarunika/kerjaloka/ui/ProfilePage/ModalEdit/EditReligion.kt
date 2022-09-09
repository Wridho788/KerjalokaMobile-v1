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
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter.EditGenderAdapter
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter.EditReligionAdapter
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.gender
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.religionList


class EditReligion: SuperBottomSheetFragment() {

    private var layoutManager: RecyclerView.LayoutManager? =null
    private var adapter: RecyclerView.Adapter<EditReligionAdapter.EditReligion>? = null
    private lateinit var editreligiAdapter: EditReligionAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = View.inflate(context, R.layout.global_modal_edit, null)
        val title = view.findViewById<TextView>(R.id.judul_bottom_sheet)
        title.text = "Pilih Agama"

        return view
    }

//    override fun getCornerRadius() = requireContext().resources.getDimension(R.dimen.demo_sheet_rounded_corner)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = ArrayList<religionList>()
        val religi1 = religionList(
            religionNo = 1,
            religionName = "Islam"
        )
        val religi2 = religionList(
            religionNo = 2,
            religionName = "Protestan"
        )
        val religi3 = religionList(
            religionNo = 3,
            religionName = "Khatolik"
        )
        val religi4 = religionList(
            religionNo = 4,
            religionName = "Hindu"
        )
        val religi5 = religionList(
            religionNo = 5,
            religionName = "Budha"
        )
        val religi6 = religionList(
            religionNo = 6,
            religionName = "Konghuchu"
        )

        list.add(religi1)
        list.add(religi2)
        list.add(religi3)
        list.add(religi4)
        list.add(religi5)
        list.add(religi6)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycleEdit)
        layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        adapter = EditReligionAdapter(list)
        recyclerView.adapter = adapter
    }


    override fun isSheetAlwaysExpanded(): Boolean {
        return true
    }

    @SuppressLint("Range")
    override fun getExpandedHeight() = ViewGroup.LayoutParams.WRAP_CONTENT
}