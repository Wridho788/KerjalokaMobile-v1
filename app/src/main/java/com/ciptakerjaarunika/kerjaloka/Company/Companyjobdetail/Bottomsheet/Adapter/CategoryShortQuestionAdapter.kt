package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.Bottomsheet.Adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.Bottomsheet.iChooseCategory
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.ManageJobPage.iUpdatePage5
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.model.Data.ShortQuestionCategory

class CategoryShortQuestionAdapter(private var dataset: List<ShortQuestionCategory>?, val iChooseCategory: iChooseCategory, val iUpdatePage5: iUpdatePage5) : RecyclerView.Adapter<CategoryShortQuestionAdapter.ViewHolder?>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtCategory: TextView

        init {
            txtCategory = view.findViewById(R.id.txt_location)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.item_location_job, null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  dataset!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataset!![position]
        holder.txtCategory.text = item.categoryName
        holder.txtCategory.setOnClickListener {
            iChooseCategory.close()
            iUpdatePage5.updateCategory(item)
        }
    }
}