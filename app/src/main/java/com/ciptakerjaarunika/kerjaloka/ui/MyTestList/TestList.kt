package com.ciptakerjaarunika.kerjaloka.ui.MyTestList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.google.android.material.appbar.MaterialToolbar

class TestList : AppCompatActivity() {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<mytest_list_adapter.ViewHolder>? = null

    var list = ArrayList<Model>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_list2)

        val recyclerView = findViewById<RecyclerView>(R.id.myTestList)
        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        adapter = mytest_list_adapter()
        recyclerView.adapter = adapter
    }
}