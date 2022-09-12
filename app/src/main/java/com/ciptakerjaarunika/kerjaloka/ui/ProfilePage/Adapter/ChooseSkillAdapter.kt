package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.MainActivity
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.gender
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.skill
import com.ciptakerjaarunika.kerjaloka.ui.WelcomingPage.OnBoarding.OnBoardingItem
import com.google.android.material.button.MaterialButton

class ChooseSkillAdapter(private val skilItems: List<skill>):
    RecyclerView.Adapter<ChooseSkillAdapter.chooseSkil>()
{

    inner class chooseSkil(view: View) : RecyclerView.ViewHolder(view) {

        var item: TextView

        init {
            item = view.findViewById<TextView>(R.id.item_modal)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): chooseSkil {
        val view = View.inflate(parent.context, R.layout.modal_list, null)
        return chooseSkil(view)
    }

    override fun onBindViewHolder(holder: chooseSkil, position: Int) {
        val currentItem = skilItems[position]
        holder.item.text= currentItem.skillName
    }

    override fun getItemCount(): Int {
        return skilItems.size
    }

}