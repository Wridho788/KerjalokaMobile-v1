package com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail.SectionKomentar.Adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail.SectionKomentar.Model.CommentModel

class KomentarAdapter(private val commentModel: List<CommentModel>) : RecyclerView.Adapter<KomentarAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var companyName: TextView
        var comment: TextView
        var createdOn: TextView
        init {
            companyName = itemView.findViewById(R.id.company_name_text)
            comment = itemView.findViewById(R.id.comment_text)
            createdOn = itemView.findViewById(R.id.comment_date_text)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.item_card_comment, null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = commentModel[position]
//        holder.companyName.text = SessionManager(context).user?.company.toString()
        holder.comment.text = currentItem.comment
//        holder.createdOn.text = currentItem.commentAt.toString()
    }

    override fun getItemCount(): Int {
        return commentModel.size
    }
}