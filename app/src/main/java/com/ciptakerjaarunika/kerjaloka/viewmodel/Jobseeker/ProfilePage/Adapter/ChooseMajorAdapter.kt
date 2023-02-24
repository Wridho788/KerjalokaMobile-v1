package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.Adapter

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.`interface`.iUpdateMajor
import com.ciptakerjaarunika.kerjaloka.model.Data.Major
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Companyjobdetail.Bottomsheet.iChooseMajor

class ChooseMajorAdapter(
    val value: Int?,
    private var dataset: List<Major>,
    val iChooseMajor: iChooseMajor,
    val iUpdateMajor: iUpdateMajor
) :
    RecyclerView.Adapter<ChooseMajorAdapter.chooseMajor>() {

    inner class chooseMajor(view: View) : RecyclerView.ViewHolder(view) {

        var item: TextView
        var container: LinearLayout

        init {
            item = view.findViewById(R.id.item_modal)
            container = view.findViewById(R.id.container)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): chooseMajor {
        val view = View.inflate(parent.context, R.layout.modal_list, null)
        view.layoutParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return chooseMajor(view)
    }

    override fun onBindViewHolder(holder: chooseMajor, position: Int) {
        val currentItem = dataset[position]
        holder.item.text = currentItem.majorName

        if (currentItem.majorNo == value) {
            holder.container.setBackgroundColor(Color.parseColor("#FFDEDE"))
            holder.container.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
        holder.container.setOnClickListener {
            iChooseMajor.close()
            iUpdateMajor.updateMajor(currentItem.majorNo)
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

}