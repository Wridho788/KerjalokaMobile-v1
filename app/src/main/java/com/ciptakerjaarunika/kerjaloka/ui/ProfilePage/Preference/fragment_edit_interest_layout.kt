package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Preference

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter.MinatAdapter
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.minat_model

class fragment_edit_interest_layout : Fragment() {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<MinatAdapter.ViewHolder>? = null

    var list = ArrayList<minat_model>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_interest_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = ArrayList<minat_model>()
        val pref1 = minat_model(
            1,
            "Accounting",
        )
        val pref2 = minat_model(
            2,
            "Music"
        )
        val pref3 = minat_model(
            3,
            "Art"
        )
        val pref4 = minat_model(
            4,
            "Computer"
        )
        val pref5 = minat_model(
            5,
            "Sleeping"
        )

        list.add(pref1)
        list.add(pref2)
        list.add(pref3)
        list.add(pref4)
        list.add(pref5)
        val recyclerView = view.findViewById<RecyclerView>(R.id.listMinat)
        layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        adapter = MinatAdapter(list)
        recyclerView.adapter = adapter
    }

    companion object {

    }
}