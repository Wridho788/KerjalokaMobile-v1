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
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter.ChooseScaleAdapter
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter.ChooseSkillAdapter
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter.EditGenderAdapter
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.gender
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.scale
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.skill


class ChooseScale: SuperBottomSheetFragment() {

    private var layoutManager: RecyclerView.LayoutManager? =null
    private var adapter: RecyclerView.Adapter<ChooseScaleAdapter.chooseScale>? = null
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
        val list = ArrayList<scale>()
        val skill1 = scale(
            scaleName = "Amateur",
            scaleNo = 1
        )
        val skill2 = scale(
            scaleName = "Beginner",
            scaleNo = 2
        )
        val skill3 = scale(
            scaleName = "Intermediate",
            scaleNo = 3
        )
        val skill4 = scale(
            scaleName = "Advance",
            scaleNo = 4
        )
        val skill5 = scale(
            scaleName = "Professional",
            scaleNo = 5
        )
        list.add(skill1)
        list.add(skill2)
        list.add(skill3)
        list.add(skill4)
        list.add(skill5)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycleEdit)
        layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        adapter = ChooseScaleAdapter(list)
        recyclerView.adapter = adapter
    }


    override fun isSheetAlwaysExpanded(): Boolean {
        return true
    }

    @SuppressLint("Range")
    override fun getExpandedHeight() = ViewGroup.LayoutParams.WRAP_CONTENT
}