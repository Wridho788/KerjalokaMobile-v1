package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.enum.SkillScale
import com.ciptakerjaarunika.kerjaloka.model.Data.SkillFilter
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ManageCV.iEditKemampuan
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ModalEdit.iChooseSkill
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.skill

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
        return chooseSkil(view)
    }

    override fun onBindViewHolder(holder: chooseSkil, position: Int) {
        val currentItem = skilItems[position]
        holder.item.text= currentItem.skillName

        if(value == currentItem){
            holder.container.setBackgroundColor(Color.parseColor("#FFDEDE"))
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
