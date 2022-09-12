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


class ChooseTitle : SuperBottomSheetFragment() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<ChooseTitleAdapter.chooseTitle>? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = View.inflate(context, R.layout.global_modal_edit, null)
        val title = view.findViewById<TextView>(R.id.judul_bottom_sheet)
        title.text = "Pilih Gelar"

        return view
    }

//    override fun getCornerRadius() = requireContext().resources.getDimension(R.dimen.demo_sheet_rounded_corner)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = ArrayList<title>()
        val t1 = title(
            id = 1,
            Title = "SMA/SMK"
        )
        val t2 = title(
            id=2,
            Title = "Diploma1"
        )
        val t3 = title(
            id=3,
            Title = "Diploma2"
        )
        val t4 = title(
            id=4,
            Title = "Diploma3"
        )
        val t5 = title(
            id=5,
            Title = "Sarjana"
        )
        val t6 = title(
            id=6,
            Title = "Master"
        )
        val t7 = title(
            id=7,
            Title = "Professor"
        )

        list.add(t1)
        list.add(t2)
        list.add(t3)
        list.add(t4)
        list.add(t5)
        list.add(t6)
        list.add(t7)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycleEdit)
        layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        adapter = ChooseTitleAdapter(list)
        recyclerView.adapter = adapter
    }


    override fun isSheetAlwaysExpanded(): Boolean {
        return true
    }

    @SuppressLint("Range")
    override fun getExpandedHeight() = ViewGroup.LayoutParams.WRAP_CONTENT
}