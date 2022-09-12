package com.ciptakerjaarunika.kerjaloka.ui.MyTestList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.LamaranPage.CellClickListener
import com.ciptakerjaarunika.kerjaloka.ui.MyTestList.Model
import com.ciptakerjaarunika.kerjaloka.ui.LamaranPage.LamaranPage
import java.util.*

class mytest_list_adapter():RecyclerView.Adapter<mytest_list_adapter.ViewHolder>() {

        private var data = listOf<Model>(
            Model("Papikostik","Kerjaloka", "Evertime You Can Collect Flexibel", "120", 12),
            Model("Papikostik","Kerjaloka", "Evertime You Can Collect Flexibel", "95", 60),
            Model("Papikostik","Kerjaloka", "Evertime You Can Collect Flexibel", "300", 45),
            Model("Papikostik","Kerjaloka", "Evertime You Can Collect Flexibel", "80",30),
            Model("Papikostik","Kerjaloka", "Evertime You Can Collect Flexibel", "60", 25),
            Model("Papikostik","Kerjaloka", "Evertime You Can Collect Flexibel", "60", 50),

        );

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val Testname: TextView
            val CompName: TextView
            val Testperiod: TextView
            val Testcreator: TextView
            val jlhPertanyaan: TextView

            init {
                // Define click listener for the ViewHolder's View.
                Testname = view.findViewById(R.id.testName)
                CompName = view.findViewById(R.id.compName)
                Testperiod = view.findViewById(R.id.period)
                Testcreator = view.findViewById(R.id.durasi_Test)
                jlhPertanyaan = view.findViewById(R.id.jlh_Pertanyaan)
            }
        }

        // Create new views (invoked by the layout manager)
        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
            // Create a new view, which defines the UI of the list item
            val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.card_test, viewGroup, false)

            return ViewHolder(view)
        }



        // Replace the contents of a view (invoked by the layout manager)
        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

            // Get element from your dataset at this position and replace the
            // contents of the view with that element
            viewHolder.Testname.text = data[position].testname
            viewHolder.CompName.text = "Oleh: " + data[position].testduration
            viewHolder.Testperiod.text = "Dapat dikerjakan sebelum: " + data[position].testperiod
            viewHolder.Testcreator.text = data[position].testcreator + " min"
            viewHolder.jlhPertanyaan.text= data[position].jlhPertanyaan.toString() + " Pertanyaan"

//            viewHolder.itemView.setOnClickListener {
//                cellClickListener.onCellClickListener()
//            }
        }

        // Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount() = data.size

    }