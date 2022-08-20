package com.ciptakerjaarunika.kerjaloka.ui.LamaranPage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.LamaranPage.Model.ApplicantModel
import java.util.*

class Application(private val cellClickListener: CellClickListener):RecyclerView.Adapter<Application.ViewHolder>() {

        /**
         * Provide a reference to the type of views that you are using
         * (custom ViewHolder).
         */
//        private var applicationData = listOf(
//            "Software Engineer", "PT. Cipta Kerja Indonesia", "Palembang, Sumatera Selatan",
//        );
        private var data = listOf<ApplicantModel>(
            ApplicantModel("Software Engineer","PT. Maju Bersama Aman Inc", "Medan, Indonesia", 3, 2, "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."),
            ApplicantModel("Egi","PT Dunia Terlarang", "Jalan Kesesatan", 3, 2, "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."),
            ApplicantModel("Sisanya","PT Dunia Terlarang", "Jalan Kesesatan", 3, 2, "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."),
            ApplicantModel("Bagusin Tampilan","PT Dunia Terlarang", "Jalan Kesesatan", 3, 2, "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."),
            ApplicantModel("Bagusin Tampilan","PT Dunia Terlarang", "Jalan Kesesatan", 3, 2,"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."),
            ApplicantModel("Bagusin Tampilan","PT Dunia Terlarang", "Jalan Kesesatan", 3, 2, "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."),
            ApplicantModel("Bagusin Tampilan","PT Dunia Terlarang", "Jalan Kesesatan", 3, 2,"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."),
            ApplicantModel("Bagusin Tampilan","PT Dunia Terlarang", "Jalan Kesesatan", 3, 2, "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")

        );

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val Jobposition: TextView
            val CompanyName: TextView
            val CompanyAddress: TextView
            val TotalTest: TextView
            val TestTaken: TextView
//            val Requirment: TextView

            init {
                // Define click listener for the ViewHolder's View.
                Jobposition = view.findViewById(R.id.card_title)
                CompanyName = view.findViewById(R.id.card_companyname)
                CompanyAddress = view.findViewById(R.id.card_location)
                TotalTest = view.findViewById(R.id.card_totalTest)
                TestTaken = view.findViewById(R.id.card_testHasTake)
//                Requirment = view.findViewById(R.id.ca)
            }
        }

        // Create new views (invoked by the layout manager)
        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
            // Create a new view, which defines the UI of the list item
            val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.myapplicant_card, viewGroup, false)

            return ViewHolder(view)
        }

        // Replace the contents of a view (invoked by the layout manager)
        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

            // Get element from your dataset at this position and replace the
            // contents of the view with that element
            viewHolder.Jobposition.text = data[position].jobposition
            viewHolder.CompanyName.text = data[position].companyname
            viewHolder.CompanyAddress.text = data[position].companyaddress
            viewHolder.TotalTest.text = data[position].totaltest.toString()
            viewHolder.TestTaken.text = data[position].taketest.toString()
//            viewHolder.Requirment.text= data[position].requirentment

            viewHolder.itemView.setOnClickListener {
                cellClickListener.onCellClickListener()
            }
        }

        // Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount() = data.size

    }