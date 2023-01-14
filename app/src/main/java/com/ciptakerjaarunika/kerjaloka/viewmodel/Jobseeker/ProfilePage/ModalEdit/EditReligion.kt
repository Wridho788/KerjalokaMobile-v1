package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.ModalEdit

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
import com.ciptakerjaarunika.kerjaloka.model.Data.Religion
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.Adapter.EditReligionAdapter
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.manage_profile.iUpdateAdditional


class EditReligion(private val religionNo : Int?, val religionList : List<Religion>, val iUpdateAdditional: iUpdateAdditional): SuperBottomSheetFragment(),
    iReligion {
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
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycleEdit)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = religionList?.let { it1 -> EditReligionAdapter(religionNo, it1, iUpdateAdditional, this@EditReligion) }
        }
    }


    override fun isSheetAlwaysExpanded(): Boolean {
        return true
    }

    @SuppressLint("Range")
    override fun getExpandedHeight() = ViewGroup.LayoutParams.WRAP_CONTENT

    override fun close() {
        this.dismiss()
    }
}
interface iReligion{
    fun close()
}