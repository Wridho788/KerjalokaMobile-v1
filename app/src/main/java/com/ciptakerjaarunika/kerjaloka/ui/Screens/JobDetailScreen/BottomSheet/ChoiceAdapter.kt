package com.ciptakerjaarunika.kerjaloka.ui.Screens.JobDetailScreen.BottomSheet

import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.enum.QuestinoType
import com.ciptakerjaarunika.kerjaloka.model.Test.ShortQuestionChoices
import com.google.android.material.card.MaterialCardView

class ChoiceAdapter(private var choice : List<ShortQuestionChoices>, private val questionType : Int) : RecyclerView.Adapter<ChoiceAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var filterTxt: TextView
        var choiceContainer: LinearLayout
        var checkBox: CheckBox

        init {
            filterTxt = itemView.findViewById(R.id.filter_txt)
            choiceContainer = itemView.findViewById(R.id.choice_container)
            checkBox = itemView.findViewById(R.id.checked)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.item_choices, null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = choice[position]
        holder.filterTxt.text = currentItem.choice
        holder.checkBox.setOnClickListener{
            if(questionType == QuestinoType.MultipleChoice.value){
                choice[position].isSelected = true
                choice.forEach {
                    if(it.shortQuestionChoiceNo != currentItem.shortQuestionChoiceNo){
                        it.isSelected = false
                    }
                }
            }
            else{
                choice[position].isSelected = !currentItem.isSelected
            }
        }
        holder.checkBox.isChecked = currentItem.isSelected

//        holder.cardrelatedJob.setOnClickListener {
//            onFragmentClickListener.onFragmentClick()
//        }
    }

    override fun getItemCount(): Int {
        return choice.size
    }
}