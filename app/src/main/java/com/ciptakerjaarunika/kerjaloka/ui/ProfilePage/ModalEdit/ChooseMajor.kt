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
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter.*
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.*


class ChooseMajor : SuperBottomSheetFragment() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<ChooseMajorAdapter.chooseMajor>? = null
    private lateinit var chooseMajorAdapter: ChooseMajorAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = View.inflate(context, R.layout.global_modal_edit, null)
        val title = view.findViewById<TextView>(R.id.judul_bottom_sheet)
        title.text = "Pilih Bidang"

        return view
    }

//    override fun getCornerRadius() = requireContext().resources.getDimension(R.dimen.demo_sheet_rounded_corner)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = ArrayList<majors>()
        val major1 = majors(
            majorName = "Agriculture, General",
            majorNo = 159
        )
        val major2 = majors(
            majorName = "Agribusiness Operations",
            majorNo = 160
        )
        val major3 = majors(
            majorName = "Agricultural Business & Management",
            majorNo = 161
        )
        list.add(major1)
        list.add(major2)
        list.add(major3)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycleEdit)
        layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        adapter = ChooseMajorAdapter(list)
        recyclerView.adapter = adapter
    }


    override fun isSheetAlwaysExpanded(): Boolean {
        return true
    }

    @SuppressLint("Range")
    override fun getExpandedHeight() = ViewGroup.LayoutParams.WRAP_CONTENT
}