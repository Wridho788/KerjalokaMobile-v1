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
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter.ChooseMonthAdapter
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter.ChooseScaleAdapter
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter.ChooseSkillAdapter
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter.EditGenderAdapter
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.gender
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.month
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.scale
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.skill


class ChooseMonth: SuperBottomSheetFragment() {

    private var layoutManager: RecyclerView.LayoutManager? =null
    private var adapter: RecyclerView.Adapter<ChooseMonthAdapter.chooseMonth>? = null
    private lateinit var chooseScaleAdapter: ChooseScaleAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = View.inflate(context, R.layout.global_modal_edit, null)
        val title = view.findViewById<TextView>(R.id.judul_bottom_sheet)
        title.text = "Pilih Level Skill"

        return view
    }

//    override fun getCornerRadius() = requireContext().resources.getDimension(R.dimen.demo_sheet_rounded_corner)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = ArrayList<month>()
        val month1 = month(
            id = 1,
            month = "Januari"
        )
        val month2 = month(
            id = 2,
            month = "Februari"
        )
        val month3 = month(
            id = 3,
            month = "Maret"
        )
        val month4 = month(
            id = 4,
            month = "April"
        )
        val month5 = month(
            id = 5,
            month = "Mei"
        )
        val month6 = month(
            id = 6,
            month = "Juni"
        )
        val month7 = month(
            id = 7,
            month = "Juli"
        )
        val month8 = month(
            id = 8,
            month = "Agustus"
        )
        val month9 = month(
            id = 9,
            month = "September"
        )
        val month10 = month(
            id = 10,
            month = "Oktober"
        )
        val month11 = month(
            id = 11,
            month = "November"
        )
        val month12 = month(
            id = 12,
            month = "Desember"
        )
        list.add(month1)
        list.add(month2)
        list.add(month3)
        list.add(month4)
        list.add(month5)
        list.add(month6)
        list.add(month7)
        list.add(month8)
        list.add(month9)
        list.add(month10)
        list.add(month11)
        list.add(month12)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycleEdit)
        layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        adapter = ChooseMonthAdapter(list)
        recyclerView.adapter = adapter
    }


    override fun isSheetAlwaysExpanded(): Boolean {
        return true
    }

    @SuppressLint("Range")
    override fun getExpandedHeight() = ViewGroup.LayoutParams.MATCH_PARENT

}