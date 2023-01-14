package com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Test

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Test.Listener.TestDetailListener
import com.ciptakerjaarunika.kerjaloka.R
//import com.ciptakerjaarunika.kerjaloka.ui.LamaranPage.CellClickListener

class mytest_adapter(private val testList: List<Test>, private val listener: TestDetailListener) :
    RecyclerView.Adapter<mytest_adapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val Testname: TextView
        val Testcreator: TextView
        val card: RelativeLayout

        init {
            Testname = view.findViewById(R.id.test_name)
            Testcreator = view.findViewById(R.id.test_creator)
            card = view.findViewById(R.id.test_card)
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
        viewHolder.card.setOnClickListener{
            listener.detail(data)
        }
    }

    override fun getItemCount() = testList.size

}