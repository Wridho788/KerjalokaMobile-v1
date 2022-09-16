package com.ciptakerjaarunika.kerjaloka.Company.Test

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.LamaranPage.CellClickListener
import com.ciptakerjaarunika.kerjaloka.Company.Test.Test
import com.ciptakerjaarunika.kerjaloka.ui.LamaranPage.LamaranPage
import java.util.*
import kotlin.collections.ArrayList

class mytest_adapter(private val testList: List<Test>) :
    RecyclerView.Adapter<mytest_adapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val Testname: TextView
        val Testcreator: TextView

        init {
            Testname = view.findViewById(R.id.test_name)
            Testcreator = view.findViewById(R.id.test_creator)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.mytestlist_card, viewGroup, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val data = testList[position]
        viewHolder.Testname.text = data.testName
        viewHolder.Testcreator.text = "Dibuat Oleh: " + data.createdBy
    }

    override fun getItemCount() = testList.size

}