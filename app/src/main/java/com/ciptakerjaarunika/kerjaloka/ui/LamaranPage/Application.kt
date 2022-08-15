package com.ciptakerjaarunika.kerjaloka.ui.LamaranPage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import java.util.*

class Application


    (private val dataSet: Array<String>) :
    RecyclerView.Adapter<Application.ViewHolder>() {

        /**
         * Provide a reference to the type of views that you are using
         * (custom ViewHolder).
         */
        private var applicationData = listOf(
            "Testing"
        );

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val Jobposition: TextView

            init {
                // Define click listener for the ViewHolder's View.
                Jobposition = view.findViewById(R.id.card_title)
            }
        }

        // Create new views (invoked by the layout manager)
        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
            // Create a new view, which defines the UI of the list item
            val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.fragment_lamaran, viewGroup, false)

            return ViewHolder(view)
        }

        // Replace the contents of a view (invoked by the layout manager)
        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

            // Get element from your dataset at this position and replace the
            // contents of the view with that element
            viewHolder.Jobposition.text = applicationData[position]
        }

        // Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount() = dataSet.size

    }