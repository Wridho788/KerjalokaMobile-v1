package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.model.Data.SkillFilter
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ManageCV.iEditKemampuan
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ModalEdit.iChooseSkill

class ChooseSkillAdapter(val value : SkillFilter?, private val skilItems: List<SkillFilter>, val iEditKemampuan: iEditKemampuan, val iChooseSkill: iChooseSkill):
    RecyclerView.Adapter<ChooseSkillAdapter.chooseSkil>()
{

    inner class chooseSkil(view: View) : RecyclerView.ViewHolder(view) {

        var item: TextView
        var container : LinearLayout

        init {
            item = view.findViewById(R.id.item_modal)
            container = view.findViewById(R.id.container)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): chooseSkil {
        val view = View.inflate(parent.context, R.layout.modal_list, null)
        view.layoutParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return chooseSkil(view)
    }

    override fun onBindViewHolder(holder: chooseSkil, position: Int) {
        val currentItem = skilItems[position]
        holder.item.text= currentItem.skillName

        if(value == currentItem){
            holder.container.setBackgroundColor(Color.parseColor("#FFDEDE"))
            holder.container.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
        holder.item.setOnClickListener{
            iEditKemampuan.updateSkill(currentItem)
            iChooseSkill.close()
        }
    }

    override fun getItemCount(): Int {
        return skilItems.size
    }

}
