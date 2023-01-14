package com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Profile.Adapter
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Profile.Listener.ShowModal
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Profile.ReviewSaya.Model.DataX
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.config.config
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class CompReviewHistoryAdapter(private val context: Context, private val ratingData: List<DataX>, private val listener: ShowModal):
    RecyclerView.Adapter<CompReviewHistoryAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var creator: TextView
        //        var reviewTime: TextView
        var Desc: TextView
        var proChip : ChipGroup
        var conChip : ChipGroup
        var ratBar : RatingBar
        var edit : MaterialButton
        var delete : MaterialButton
        var logo : ImageView

        init {
            creator = itemView.findViewById(R.id.nama_jobseeker)
//            reviewTime = itemView.findViewById(R.id.record_page_date)
            Desc = itemView.findViewById(R.id.reviewDesc)
            proChip = itemView.findViewById(R.id.chipGroup_kelebihan)
            conChip = itemView.findViewById(R.id.chipGroup_kekurangan)
            ratBar = itemView.findViewById(R.id.ratingbar)
            edit = itemView.findViewById(R.id.btn_Edit)
            delete = itemView.findViewById(R.id.btn_delete)
            logo = itemView.findViewById(R.id.logo)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.review_history_card, null)
        view.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = ratingData[position]
        holder.creator.text = currentItem.userFullName
        holder.Desc.text = currentItem.comment
        holder.ratBar.rating = currentItem.rating.toFloat()

        Glide.with(context)
            .load(config().portAddress + "/photo/Profile/" + currentItem.raterPhoto).fitCenter()
            .into(holder.logo)

        if(currentItem.approved) {
            holder.edit.setOnClickListener {
                listener.showDetail(currentItem)
            }
        }
        else{
            holder.edit.setStrokeColorResource(R.color.light_500)
            holder.edit.setTextColor(com.giphy.sdk.ui.R.color.material_on_background_disabled)
            holder.edit.isEnabled = false
        }

        holder.delete.setOnClickListener{
            listener.showDelete(currentItem)
        }

        if (currentItem.conRating.isNotEmpty()){
            currentItem.conRating.forEach {
                val chip = Chip(holder.conChip.context)
                chip.setChipBackgroundColorResource(R.color.danger_100)
                chip.apply {
                    textSize = 12f
                    text = it
                    isChipIconVisible = false
                    isCloseIconVisible = false
                    isClickable = false
                    isCheckable = false
                    holder.apply {
                        conChip.addView(chip as View)
                    }
                }
            }
        }
        if (currentItem.proRating.isNotEmpty()){
            currentItem.proRating.forEach {
                val chip = Chip(holder.proChip.context)
                chip.setChipBackgroundColorResource(R.color.danger_100)
                chip.apply {
                    textSize = 12f
                    text = it
                    isChipIconVisible = false
                    isCloseIconVisible = false
                    isClickable = false
                    isCheckable = false
                    holder.apply {
                        proChip.addView(chip as View)
                    }
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return ratingData.size
    }
}
