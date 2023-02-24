package com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Package

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Package.Adapter.historyListAdapter
import com.google.common.reflect.TypeToken
import com.google.gson.Gson

class history_modal : SuperBottomSheetFragment() {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<historyListAdapter.History>? = null
    var history: ArrayList<pckHistory>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            val descFromBundle = arguments?.getString(EXTRA_HISTORY_PACKAGE)
            val type = object : TypeToken<List<pckHistory>>() {}.type
            history = Gson().fromJson(descFromBundle, type)
            val recyclerView = view.findViewById<RecyclerView>(R.id.recycleHistoryPack)
            layoutManager = LinearLayoutManager(activity)
            recyclerView.layoutManager = layoutManager
            adapter = history?.let { historyListAdapter(it) }
            recyclerView.adapter = adapter
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_history_modal, container, false)

        return view
    }

    companion object {
        var EXTRA_HISTORY_PACKAGE = "extra_historyPackage"
    }

    override fun isSheetAlwaysExpanded(): Boolean {
        return true
    }

    @SuppressLint("Range")
    override fun getExpandedHeight() = ViewGroup.LayoutParams.WRAP_CONTENT
}