package com.ciptakerjaarunika.kerjaloka.Company.Test

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R

class mytest_adapter(private val cellClickListener: view_mytest_list):RecyclerView.Adapter<mytest_adapter.ViewHolder>() {

        private var data = listOf<Model>(
            Model("Papikostik","16 Hours", "Evertime You Can Collect Flexibel", "Kerjaloka."),
            Model("Papikostik","16 Hours", "Evertime You Can Collect Flexibel", "Kerjaloka."),
            Model("Papikostik","16 Hours", "Evertime You Can Collect Flexibel", "Kerjaloka."),
            Model("Papikostik","16 Hours", "Evertime You Can Collect Flexibel", "Kerjaloka."),
            Model("Papikostik","16 Hours", "Evertime You Can Collect Flexibel", "Kerjaloka."),
            Model("Papikostik","16 Hours", "Evertime You Can Collect Flexibel", "Kerjaloka."),

        );

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val Testname: TextView
            val Testduration: TextView
            val Testperiod: TextView
            val Testcreator: TextView
//            val Requirment: TextView

            init {
                // Define click listener for the ViewHolder's View.
                Testname = view.findViewById(R.id.test_name)
                Testduration = view.findViewById(R.id.test_duration)
                Testperiod = view.findViewById(R.id.test_period)
                Testcreator = view.findViewById(R.id.test_creator)
//                Requirment = view.findViewById(R.id.ca)
            }
        }

        // Create new views (invoked by the layout manager)
        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
            // Create a new view, which defines the UI of the list item
            val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.mytestlist_card, viewGroup, false)

            return ViewHolder(view)
        }



        // Replace the contents of a view (invoked by the layout manager)
        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

            // Get element from your dataset at this position and replace the
            // contents of the view with that element
            viewHolder.Testname.text = data[position].testname
            viewHolder.Testduration.text = data[position].testduration
            viewHolder.Testperiod.text = data[position].testperiod
            viewHolder.Testcreator.text = data[position].testcreator
//            viewHolder.Requirment.text= data[position].requirentment

            viewHolder.itemView.setOnClickListener {
                cellClickListener.onCellClickListener()
            }
        }

        // Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount() = data.size

    }