package com.ciptakerjaarunika.kerjaloka.viewmodel.Company.CompanyApplicant.ApplicantDetail.SectionKomentar.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.CompanyApplicant.ApplicantDetail.SectionKomentar.Model.CommentModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class KomentarAdapter(private val context: Context?, private val commentModel: List<CommentModel>) :
    RecyclerView.Adapter<KomentarAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card_comment, null)
        val lp = RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        view.layoutParams = lp
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = commentModel[position]
        holder.companyName.text = SessionManager(context).user?.company?.companyName.toString()
        holder.comment.text = currentItem.comment
        holder.createdOn.text = LocalDateTime.parse(currentItem.commentAt)
            .format(DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm"))
    }

    override fun getItemCount(): Int {
        return commentModel.size
    }
}