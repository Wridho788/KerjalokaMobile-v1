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
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter.EditExp_TypeJob
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter.EditGenderAdapter
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.gender
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.typeJob


class EditExpTypeJob: SuperBottomSheetFragment() {

    private var layoutManager: RecyclerView.LayoutManager? =null
    private var adapter: RecyclerView.Adapter<EditExp_TypeJob.ChooseType>? = null
    private lateinit var editGenderAdapter: EditExp_TypeJob
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = View.inflate(context, R.layout.global_modal_edit, null)
        val title = view.findViewById<TextView>(R.id.judul_bottom_sheet)
        title.text = "Pilih Type Job"

        return view
    }

//    override fun getCornerRadius() = requireContext().resources.getDimension(R.dimen.demo_sheet_rounded_corner)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = ArrayList<typeJob>()
        val type1 = typeJob(
            typeJob = "Full-time",
            typeNo = 1
        )
        val type2 = typeJob(
            typeJob = "Part-time",
            typeNo = 2
        )
        val type3 = typeJob(
            typeJob = "Contract-time",
            typeNo = 3
        )
        val type4 = typeJob(
            typeJob = "Internship",
            typeNo = 4
        )
        val type5 = typeJob(
            typeJob = "Volunteer",
            typeNo = 5
        )
        val type6 = typeJob(
            typeJob = "Remote",
            typeNo = 6
        )
        list.add(type1)
        list.add(type2)
        list.add(type3)
        list.add(type4)
        list.add(type5)
        list.add(type6)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycleEdit)
        layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        adapter = EditExp_TypeJob(list)
        recyclerView.adapter = adapter
    }


    override fun isSheetAlwaysExpanded(): Boolean {
        return true
    }

    @SuppressLint("Range")
    override fun getExpandedHeight() = ViewGroup.LayoutParams.WRAP_CONTENT
}