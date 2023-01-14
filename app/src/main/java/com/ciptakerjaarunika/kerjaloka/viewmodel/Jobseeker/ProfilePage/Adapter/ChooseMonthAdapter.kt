package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.Adapter

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.enum.Month
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.ManageCV.iManageExp
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.ModalEdit.iCloseModal

class ChooseMonthAdapter(val type: String, val value : Int?, val iManageExp: iManageExp, val iCloseModal: iCloseModal) :
    RecyclerView.Adapter<ChooseMonthAdapter.chooseMonth>() {

    inner class chooseMonth(view: View) : RecyclerView.ViewHolder(view) {

        var item: TextView
        var container : LinearLayout

        init {
            item = view.findViewById(R.id.item_modal)
            container = view.findViewById(R.id.container)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): chooseMonth {
        val view = View.inflate(parent.context, R.layout.modal_list, null)
        view.layoutParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return chooseMonth(view)
    }

    override fun onBindViewHolder(holder: chooseMonth, position: Int) {
        val currentItem = Month.values().find { month-> month.value == position +1}
        holder.item.text = currentItem?.description
        if(position+1 == value){
            holder.container.setBackgroundColor(Color.parseColor("#FFDEDE"))
            holder.container.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
        holder.container.setOnClickListener{
            iManageExp.updateMonth(position+1, type)
            iCloseModal.close()
        }
    }

    override fun getItemCount(): Int {
        return Month.values().size
    }

}
