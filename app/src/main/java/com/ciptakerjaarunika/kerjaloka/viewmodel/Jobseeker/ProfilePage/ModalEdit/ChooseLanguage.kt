package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.ModalEdit

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.model.Data.Language
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.Adapter.ChooseLanguageAdapter
import com.ciptakerjaarunika.kerjaloka.viewmodel.ProfilePage.iEditBahasa


class ChooseLanguage(val languageNo : Int?, val languages : List<Language>,val iEditBahasa: iEditBahasa) : SuperBottomSheetFragment(),
    iChooseLanguage {

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

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycleEdit)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = ChooseLanguageAdapter(languageNo, languages, iEditBahasa, this@ChooseLanguage)
        }
        var searchInput = view.findViewById<SearchView>(R.id.search_filter)
        searchInput.visibility = View.VISIBLE

        searchInput.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val keyword = newText.toString().toLowerCase()
                if (keyword.isNullOrEmpty()) {
                    recyclerView.apply {
                        layoutManager = LinearLayoutManager(activity)
                        adapter = ChooseLanguageAdapter(languageNo, languages, iEditBahasa, this@ChooseLanguage)
                    }
                    recyclerView.adapter?.notifyDataSetChanged()
                } else {
                    var temp = languages?.filter { data ->
                        data.languageName.toLowerCase().contains(keyword)
                    }
                    recyclerView.apply {
                        layoutManager = LinearLayoutManager(activity)
                        adapter = ChooseLanguageAdapter(languageNo, temp!!, iEditBahasa, this@ChooseLanguage)
                    }
                    recyclerView.adapter?.notifyDataSetChanged()
                }
                return true;
            }
        })
    }


    override fun isSheetAlwaysExpanded(): Boolean {
        return true
    }

    @SuppressLint("Range")
    override fun getExpandedHeight(): Int {
        val displayMetrics = DisplayMetrics()
        (context as Activity?)!!.windowManager
            .defaultDisplay
            .getMetrics(displayMetrics)
        return (displayMetrics.heightPixels * 0.8).toInt();
    }

    override fun close() {
        this.dismiss()
    }
}
interface iChooseLanguage{
    fun close()
}