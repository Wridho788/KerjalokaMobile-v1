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
import com.ciptakerjaarunika.kerjaloka.ui.WelcomingPage.OnBoarding.OnBoardingItem
import com.google.android.material.button.MaterialButton

class EditGenderAdapter(private val genderItems: List<gender>):
    RecyclerView.Adapter<EditGenderAdapter.EditGender>()
{

    inner class EditGender(view: View) : RecyclerView.ViewHolder(view) {

        var item: TextView

        init {
            item = view.findViewById<TextView>(R.id.item_modal)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditGender {
        val view = View.inflate(parent.context, R.layout.modal_list, null)
        return EditGender(view)
    }

    override fun onBindViewHolder(holder: EditGender, position: Int) {
        val currentItem = genderItems[position]
        holder.item.text= currentItem.gender
    }

    override fun getItemCount(): Int {
        return genderItems.size
    }

}