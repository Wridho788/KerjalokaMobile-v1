package com.ciptakerjaarunika.kerjaloka.Company.Package

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.ciptakerjaarunika.kerjaloka.Company.Package.Adapter.historyListAdapter
import com.ciptakerjaarunika.kerjaloka.Company.Package.Adapter.myPackageAdapter
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.HomePage.Adapter.RecommendationJobAdapter

class history_modal : SuperBottomSheetFragment() {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<historyListAdapter.History>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_history_modal, container, false)

        val history = ArrayList<pckHistory>()
        val hst1 = pckHistory(
            actionOn = "2022-05-21T10:07:34",
            creditValue = 1,
            packageName = "Small Test Package",
            userPackageLogDescription = "Add Test Test Tist (-1 Credit value)"
        )
        val hst2 = pckHistory(
            actionOn = "2022-05-21T10:07:34",
            creditValue = 1,
            packageName = "Small Test Package",
            userPackageLogDescription = "Add Test Test Jawaban Ganda (-1 Credit value)"
        )
        val hst3 = pckHistory(
            actionOn = "2022-07-02T15:16:31",
            creditValue = 1,
            packageName = "Small Test Package",
            userPackageLogDescription = "Add Test testing lampiran (-1 Credit value)"
        )
        history.add(hst1)
        history.add(hst2)
        history.add(hst3)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycleHistoryPack)
        layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        adapter = historyListAdapter(history)
        recyclerView.adapter = adapter

        return view
    }

    companion object {
    }

    override fun isSheetAlwaysExpanded(): Boolean {
        return true
    }

    @SuppressLint("Range")
    override fun getExpandedHeight() = ViewGroup.LayoutParams.WRAP_CONTENT
}