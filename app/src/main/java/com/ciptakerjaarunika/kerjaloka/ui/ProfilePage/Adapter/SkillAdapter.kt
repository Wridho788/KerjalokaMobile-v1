package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter

import android.content.Context
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.enum.SkillScale
import com.ciptakerjaarunika.kerjaloka.model.Profile.JobseekerSkills
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ManageCV.iEditKemampuan
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.skill
import com.giphy.sdk.analytics.GiphyPingbacks.context
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class SkillAdapter(private val skilItems: List<JobseekerSkills>, val context: Context,val iEditKemampuan: iEditKemampuan):
    RecyclerView.Adapter<SkillAdapter.Skill>()
{
    inner class Skill(view: View) : RecyclerView.ViewHolder(view) {

        var container: LinearLayout
        var skillName : TextView
        var chipGroup : ChipGroup

        init {
            container = view.findViewById(R.id.container)
            skillName = view.findViewById(R.id.skillName)
            chipGroup = view.findViewById(R.id.chipGroup)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Skill {
        val view = View.inflate(parent.context, R.layout.item_skills, null)
        return Skill(view)
    }

    override fun onBindViewHolder(holder: Skill, position: Int) {

        val currentItems = skilItems.filter { skill -> skill.scale == position + 1 }
        if(currentItems.size == 0){
            holder.container.visibility = GONE
        }
        else{
            holder.skillName.setText(SkillScale.values().find { scale -> scale.value == position+1 }?.description)

            currentItems.forEach { skill ->
                val skil1Chip = Chip(context)
                skil1Chip.setChipBackgroundColorResource(R.color.danger_100)
                skil1Chip.apply {
                    textSize = 12f
                    text = skill.skillName
                    isChipIconVisible = false
                    isCloseIconVisible = true
                    setCloseIconResource(R.drawable.ic_close)
                    setOnCloseIconClickListener {
                        iEditKemampuan.removeSkill(skill)
                    }
                    isCheckable = false
                    holder.apply {
                        chipGroup?.addView(skil1Chip as View)
                    }
                }
            }

        }
//        holder.item.text= currentItem.skillName
    }

    override fun getItemCount(): Int {
        return SkillScale.values().size
    }

}
