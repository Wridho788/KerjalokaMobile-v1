package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.Adapter

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.ModalEdit.iChooseScore
import com.ciptakerjaarunika.kerjaloka.viewmodel.ProfilePage.iEditBahasa

class ChooseScoreAdapter(
    val type: String,
    val value: Int?,
    val iEditBahasa: iEditBahasa,
    val iChooseScore: iChooseScore
) : RecyclerView.Adapter<ChooseScoreAdapter.chooseScore>() {

    inner class chooseScore(view: View) : RecyclerView.ViewHolder(view) {
        var item: TextView
        var container: LinearLayout

        init {
            item = view.findViewById(R.id.item_modal)
            container = view.findViewById(R.id.container)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): chooseScore {
        val view = View.inflate(parent.context, R.layout.modal_list, null)
        view.layoutParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return chooseScore(view)
    }

    override fun onBindViewHolder(holder: chooseScore, position: Int) {
        var currentValue = position + 1
        holder.item.text = currentValue.toString()

        if (currentValue == value) {
            holder.container.setBackgroundColor(Color.parseColor("#FFDEDE"))
        }
        holder.container.setOnClickListener {
            if (type == "written") {
                iEditBahasa.updateScoreTulisan(currentValue)
                iChooseScore.close()
            } else {
                iEditBahasa.updateScoreLisan(currentValue)
                iChooseScore.close()
            }
        }
    }

    override fun getItemCount(): Int {
        return 10
    }

}