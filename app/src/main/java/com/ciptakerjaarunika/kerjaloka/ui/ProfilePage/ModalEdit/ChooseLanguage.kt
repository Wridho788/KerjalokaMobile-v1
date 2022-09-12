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


class ChooseLanguage : SuperBottomSheetFragment() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<ChooseLanguageAdapter.chooseLang>? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = View.inflate(context, R.layout.global_modal_edit, null)
        val title = view.findViewById<TextView>(R.id.judul_bottom_sheet)
        title.text = "Pilih Bahasa"

        return view
    }

//    override fun getCornerRadius() = requireContext().resources.getDimension(R.dimen.demo_sheet_rounded_corner)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = ArrayList<language>()
        val lang1 = language(
            languageNo = 1,
            languageName = "Bahasa Indonesia"
        )
        val lang2 = language(
            languageNo = 2,
            languageName = "Chinese"
        )
        val lang3 = language(
            languageNo = 3,
            languageName = "English"
        )
        list.add(lang1)
        list.add(lang2)
        list.add(lang3)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycleEdit)
        layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        adapter = ChooseLanguageAdapter(list)
        recyclerView.adapter = adapter
    }


    override fun isSheetAlwaysExpanded(): Boolean {
        return true
    }

    @SuppressLint("Range")
    override fun getExpandedHeight() = ViewGroup.LayoutParams.WRAP_CONTENT
}