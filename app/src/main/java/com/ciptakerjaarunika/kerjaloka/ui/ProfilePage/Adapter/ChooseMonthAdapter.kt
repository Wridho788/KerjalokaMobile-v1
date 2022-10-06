package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.enum.Month
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ManageCV.iManageExp
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ModalEdit.iCloseModal
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.month

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
        return chooseMonth(view)
    }

    override fun onBindViewHolder(holder: chooseMonth, position: Int) {
        val currentItem = Month.values().find { month-> month.value == position +1}
        holder.item.text = currentItem?.description
        if(position+1 == value){
            holder.container.setBackgroundColor(Color.parseColor("#FFDEDE"))
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
