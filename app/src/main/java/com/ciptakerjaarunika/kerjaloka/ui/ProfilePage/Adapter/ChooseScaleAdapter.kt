package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.enum.SkillScale
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ManageCV.iEditKemampuan
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ModalEdit.iChooseScale

class ChooseScaleAdapter(val value : Int?, val iChooseSkill: iChooseScale, val iEditKemampuan: iEditKemampuan):
    RecyclerView.Adapter<ChooseScaleAdapter.chooseScale>()
{
    inner class chooseScale(view: View) : RecyclerView.ViewHolder(view) {

        var item: TextView
        var container : LinearLayout

        init {
            item = view.findViewById(R.id.item_modal)
            container = view.findViewById(R.id.container)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): chooseScale {
        val view = View.inflate(parent.context, R.layout.modal_list, null)
        view.layoutParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return chooseScale(view)
    }

    override fun onBindViewHolder(holder: chooseScale, position: Int) {
        holder.item.text = SkillScale.values().find { scale-> scale.value == position+1 }?.description

        if(value == position+1){
            holder.container.setBackgroundColor(Color.parseColor("#FFDEDE"))
            holder.container.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
        holder.item.setOnClickListener{
            iEditKemampuan.updateScale(position +1)
            iChooseSkill.close()
        }
    }

    override fun getItemCount(): Int {
        return SkillScale.values().size
    }
}
