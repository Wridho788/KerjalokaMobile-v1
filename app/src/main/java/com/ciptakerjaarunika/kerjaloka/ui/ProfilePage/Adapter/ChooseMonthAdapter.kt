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
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.month
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.scale
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.skill
import com.ciptakerjaarunika.kerjaloka.ui.WelcomingPage.OnBoarding.OnBoardingItem
import com.google.android.material.button.MaterialButton

class ChooseMonthAdapter(private val monthList: List<month>):
    RecyclerView.Adapter<ChooseMonthAdapter.chooseMonth>()
{

    inner class chooseMonth(view: View) : RecyclerView.ViewHolder(view) {

        var item: TextView

        init {
            item = view.findViewById<TextView>(R.id.item_modal)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): chooseMonth {
        val view = View.inflate(parent.context, R.layout.modal_list, null)
        return chooseMonth(view)
    }

    override fun onBindViewHolder(holder: chooseMonth, position: Int) {
        val currentItem = monthList[position]
        holder.item.text= currentItem.month
    }

    override fun getItemCount(): Int {
        return monthList.size
    }

}