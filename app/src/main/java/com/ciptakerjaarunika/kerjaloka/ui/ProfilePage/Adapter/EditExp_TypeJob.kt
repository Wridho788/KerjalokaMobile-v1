package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.model.Data.JobTypeFilter
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ManageCV.iManageExp
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ModalEdit.iCloseModal
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.typeJob

class EditExp_TypeJob(val value: Int?, private val typeList: List<JobTypeFilter>, val iManageExp: iManageExp, val iCloseModal: iCloseModal):
    RecyclerView.Adapter<EditExp_TypeJob.ChooseType>()
{

    inner class ChooseType(view: View) : RecyclerView.ViewHolder(view) {

        var item: TextView
        var container : LinearLayout

        init {
            item = view.findViewById(R.id.item_modal)
            container = view.findViewById(R.id.container)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChooseType {
        val view = View.inflate(parent.context, R.layout.modal_list, null)
        view.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        return ChooseType(view)
    }

    override fun onBindViewHolder(holder: ChooseType, position: Int) {
        val currentItem = typeList[position]
        holder.item.text= currentItem.jobTypeName

        if(currentItem.jobTypeNo == value){
            holder.container.setBackgroundColor(Color.parseColor("#FFDEDE"))
        }
        holder.container.setOnClickListener{
            iManageExp.updateJobType( currentItem.jobTypeNo)
            iCloseModal.close()
        }
    }

    override fun getItemCount(): Int {
        return typeList.size
    }

}